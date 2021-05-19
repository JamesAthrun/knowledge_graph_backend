package com.example.demo.blImpl.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.data.KG.QuestionMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.GraphPo;
import com.example.demo.po.ItemPo;
import com.example.demo.po.QuestionPo;
import com.example.demo.po.TriplePo;
import com.example.demo.util.Timer;
import com.example.demo.util.*;
import com.example.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KGServiceImpl implements KGService {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    TripleMapper tripleMapper;
    @Autowired
    GlobalLogger logger;
    @Autowired
    GlobalConfigure globalConfigure;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    Recorder recorder;
    @Autowired
    Timer timer;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    GraphMapper graphMapper;

    //去重
    private static <T> void MySet(List<T> in) {
        HashSet<T> out = new HashSet<>(in);
        for (T item : in) {
            if (out.contains(item)) continue;
            out.add(item);
        }
        in.clear();
        in.addAll(out);
    }

    private static <T> List<T> getRandomList(List<T> paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random random = new Random(0);
        List<Integer> tempList = new ArrayList<>();
        List<T> newList = new ArrayList<T>();
        for (int i = 0, temp; i < count; ) {
            temp = random.nextInt(paramList.size());
            if (!tempList.contains(temp)) {
                tempList.add(temp);
                newList.add(paramList.get(temp));
                i += 1;
            }
        }
        return newList;
    }

    @Override
    public ResultBean searchEntity(String keywords, String ver) {
        timer.set();
        List<ItemPo> items = itemMapper.searchByKeywords(keywords, ver);
        ItemListVo itemListVo = new ItemListVo();
        for (ItemPo e : items) {
            itemListVo.addItem(e);
        }

        logger.log("节点数 " + items.size() + " 搜索用时 " + timer.get());

        return ResultBean.success(itemListVo);
    }

    @Override
    public ResultBean getGraphData(String id, String ver) {
        timer.set();

        List<TriplePo> related_link = new ArrayList<>();
        searchTriples(id, 3, 5, related_link, ver);
        //depth是递归查找上限，neighbors是每层头和尾的连接上限

        GraphInfoVo go = new GraphInfoVo(itemMapper, ver);
        for (TriplePo item : related_link) {
            go.addLink(item);
        }

        logger.log("相关节点数 " + related_link.size() + " 搜索用时 " + timer.get());

        return ResultBean.success(go);
    }

    @Override
    public ResultBean getTreeData(String id, String ver) {
        timer.set();

        List<TriplePo> related_link = new ArrayList<>();
        searchTriples(id, 3, 5, related_link, ver);

        TreeInfoVo to = new TreeInfoVo(id, itemMapper, ver);

        Queue<String> q = new LinkedList<>();
        q.offer(id);
        HashMap<String, ArrayList<TriplePo>> visited = new HashMap<>();

        while (q.size() > 0) {
            String pId = q.poll();
            for (TriplePo triplePo : related_link) {
                if (visited.get(triplePo.relation) != null && visited.get(triplePo.relation).contains(triplePo))
                    continue;
                if (triplePo.head.equals(pId)) {
                    to.itemAdd(
                            to.addItem(triplePo.head, triplePo.relation),
                            triplePo.tail);
                    q.offer(triplePo.tail);
                    //p->relation->tail
                    if (!visited.containsKey(triplePo.relation)) visited.put(triplePo.relation, new ArrayList<>());
                    visited.get(triplePo.relation).add(triplePo);//不走原路
                }
                if (triplePo.tail.equals(pId)) {
                    if (!(visited.get(triplePo.tail) != null && visited.get(triplePo.tail).contains(triplePo))) {
                        to.itemAdd(
                                to.addItem(triplePo.tail, triplePo.relation),
                                triplePo.head);
                        q.offer(triplePo.head);
                        //p->relation->head
                        if (!visited.containsKey(triplePo.relation)) visited.put(triplePo.relation, new ArrayList<>());
                        visited.get(triplePo.relation).add(triplePo);
                    }
                }
            }
        }

        logger.log("相关节点数 " + related_link.size() + " 搜索用时 " + timer.get());

        return ResultBean.success(to.getRoot());
    }

    @Override
    public ResultBean createGraphByJsonStr(String jsonString) {
        globalConfigure.createGraphByJsonStr(jsonString);
        return ResultBean.success();
    }

    @Override
    public ResultBean commitChange(KGEditFormVo f) {
        Integer res = redisUtil.OpCommitItemChange(f);
        return res == 1 ? ResultBean.success() : ResultBean.error(701, "commit fail");
    }

    @Override
    public ResultBean cancelChange(KGEditFormVo f) {
        Integer res = redisUtil.OpCancelItemChange(f);
        return res == 1 ? ResultBean.success() : ResultBean.error(702, "cancel fail");
    }

    @Override
    public ResultBean confirmChange(String userName) {
        Set<String> ops = redisUtil.getOpsOfUser(userName);
        List<ResultBean> resList = new ArrayList<>();
        String tableId = GlobalTrans.jsonStrToJavaObject(ops.iterator().next(), KGEditFormVo.class).tableId;
        String ver = graphMapper.getPresentVer(tableId);

        for (String op : ops) {
            KGEditFormVo f = GlobalTrans.jsonStrToJavaObject(op, KGEditFormVo.class);
            if (!f.tableId.equals(tableId)) return ResultBean.error(801, "cannot op more than 1 graph in 1 time");
            switch (f.op) {
                case "createItem":
                    resList.add(createItem(
                            f.headId,
                            f.relationId,
                            f.tailId,
                            f.tableId,
                            f.title,
                            f.name,
                            f.division,
                            f.comment,
                            ver));
                    break;
                case "createLink":
                    resList.add(createLink(
                            f.tableId,
                            f.headId,
                            f.relationId,
                            f.tailId,
                            ver));
                    break;
                case "updateItem":
                    resList.add(updateItem(
                            f.id,
                            f.tableId,
                            f.title,
                            f.name,
                            f.division,
                            f.comment,
                            ver));

                    break;
                case "replaceItem":
                    resList.add(replaceItem(
                            f.headId,
                            f.relationId,
                            f.tailId,
                            f.id,
                            f.tableId,
                            f.title,
                            f.name,
                            f.division,
                            f.comment,
                            ver));
                    break;
                case "deleteItem":
                    resList.add(deleteItem(
                            f.id,
                            ver));
                    break;
                case "deleteLink":
                    resList.add(deleteLink(
                            f.headId,
                            f.relationId,
                            f.tailId,
                            ver));
                    break;
                default:
                    resList.add(ResultBean.error(703, "op fail"));
            }
        }
        for (ResultBean res : resList) {
            if (res.code != 1) return res;
        }

        graphMapper.confirmChange(incr(ver), tableId);
        redisUtil.OpConfirmChange(userName);
        return ResultBean.success();
    }

    @Override
    public ResultBean rollBackChange(String ver, String tableId) {
        graphMapper.rollBack(ver, tableId);
        return ResultBean.success();
    }

    @Override
    public ResultBean getGraphInfo(String tableId) {
        GraphPo go = graphMapper.get(tableId);
        return ResultBean.success(go);
    }

    @Override
    public ResultBean getAllGraphInfo() {
        List<GraphPo> goList = graphMapper.getAll();
        return ResultBean.success(goList);
    }

    @Override
    public ResultBean ask(String questionStr) {
        List<QuestionPo> qs = new ArrayList<>(questionMapper.getAll());
        HashMap<QuestionPo, Integer> votes = new HashMap<>();

        for (QuestionPo q : qs) {
            List<String> tmp = q.getKeyWords();
            for (String kw : tmp) {
                if (questionStr.contains(kw)) votes.merge(q, 0, (oldValue, newValue) -> oldValue + 1);
            }
        }
        votes.put(null, 0);
        QuestionPo max = null;
        for (QuestionPo q : votes.keySet())
            if (votes.get(q) > votes.get(max)) max = q;
        if (max == null) return ResultBean.error(0, "no match question");

        String ver = max.ver;
        AnswerVo ao = new AnswerVo(itemMapper, max.help, ver);
        List<String> relatedIds = max.getRelatedIds();
        for (String id : relatedIds) {
            ao.addTableItem(id);
        }
        return ResultBean.success(ao);
    }

    private ResultBean createItem(String headId, String relationId, String tailId, String tableId, String title, String name, String division, String comment, String ver) {
        //010 新建property
        if (headId.equals("") && !relationId.equals("") && tailId.equals("")) {
            String tmp = recorder.getRecordId();
            //同id，版本号自动阻塞
            itemMapper.insert(new ItemPo(tmp, tableId, title, name, division, comment, incr(ver), "0"));
            return ResultBean.success(tmp);
        }

        //011 新建头item
        if (headId.equals("") && !relationId.equals("")) {
            String tmp = recorder.getRecordId();
            //同id，版本号自动阻塞
            itemMapper.insert(new ItemPo(tmp, tableId, title, name, division, comment, incr(ver), "0"));
            //同三元组，版本号自动阻塞
            createLink(tableId, headId, relationId, tmp, ver);
            return ResultBean.success(tmp);
        }

        //110 新建尾item
        if (!headId.equals("") && !relationId.equals("") && tailId.equals("")) {
            String tmp = recorder.getRecordId();
            //同id，版本号自动阻塞
            itemMapper.insert(new ItemPo(tmp, tableId, title, name, division, comment, incr(ver), "0"));
            //同三元组，版本号自动阻塞
            createLink(tableId, tmp, relationId, tailId, ver);
            return ResultBean.success(tmp);
        }

        return ResultBean.error(105, "h-r-t must be 010 or 011 or 110");
    }

    private ResultBean createLink(String tableId, String headId, String relationId, String tailId, String ver) {
        //同三元组，版本号自动阻塞
        tripleMapper.insert(new TriplePo(tableId, headId, relationId, tailId, incr(ver), "0"));
        return ResultBean.success();
    }

    private ResultBean updateItem(String id, String tableId, String title, String name, String division, String comment, String ver) {
        //同id，版本号自动阻塞，不删除原有
        itemMapper.insert(new ItemPo(id, tableId, title, name, division, comment, incr(ver), "0"));
        return ResultBean.success();
    }

    private ResultBean replaceItem(String headId, String relationId, String tailId, String id, String tableId, String title, String name, String division, String comment, String ver) {
        if (id.equals(headId)) {
            //阻塞掉原有
            deleteLink(headId, relationId, tailId, ver);
            return createItem("", relationId, tailId, tableId, title, name, division, comment, ver);
        } else if (id.equals(tailId)) {
            //阻塞掉原有
            deleteLink(headId, relationId, tailId, ver);
            return createItem(headId, relationId, "", tableId, title, name, division, comment, ver);
        } else if (id.equals(relationId)) {
            //阻塞掉原有
            deleteLink(headId, relationId, tailId, ver);
            String newRelationId = createItem(headId, relationId, tailId, tableId, title, name, division, comment, ver).data;
            createLink(newRelationId, headId, newRelationId, tailId, ver);
            return ResultBean.success();
        }
        return ResultBean.error(205, "Replace item failed");
    }

    private ResultBean deleteItem(String id, String ver) {
        ItemPo tmpItem = itemMapper.getById(id, ver);
        tmpItem.ver = incr(ver);
        //标记位置1，表示阻塞
        tmpItem.drop = "1";
        itemMapper.insert(tmpItem);
        return ResultBean.success();
    }

    private ResultBean deleteLink(String headId, String relationId, String tailId, String ver) {
        TriplePo tmpTri = new TriplePo(tailId, headId, relationId, tailId, incr(ver), "1");
        tripleMapper.insert(tmpTri);
        return ResultBean.success();
    }

    private void searchTriples(String id, int depth, int neighbors, List<TriplePo> res, String ver) {
        if (depth == 0) return;
        MySet(res);
        List<TriplePo> cases = tripleMapper.getRelatedTriples(id, ver);
        List<TriplePo> tmp_h = new ArrayList<>();
        List<TriplePo> tmp_r = new ArrayList<>();
        List<TriplePo> tmp_t = new ArrayList<>();
        for (TriplePo tri : cases) {
            //注意避免re_hit的情况
            if (!triple_existed(res, tri)) {
                if (tri.head.equals(id)) tmp_h.add(tri);
                if (tri.relation.equals(id)) tmp_r.add(tri);
                if (tri.tail.equals(id)) tmp_t.add(tri);
            }
        }
        tmp_h = getRandomList(tmp_h, neighbors);
        tmp_r = getRandomList(tmp_r, neighbors);
        tmp_t = getRandomList(tmp_t, neighbors);
        res.addAll(tmp_h);
        res.addAll(tmp_r);
        res.addAll(tmp_t);
        //作为头节点时，对尾节点进行相关搜索
        //作为关系节点时，不进行相关搜索
        //作为尾节点时，对头节点进行相关搜索
        for (TriplePo tri : tmp_h) {
            if (!tri.tail.equals(id)) searchTriples(tri.tail, depth - 1, neighbors, res, ver);
        }
        for (TriplePo tri : tmp_t) {
            if (!tri.head.equals(id)) searchTriples(tri.head, depth - 1, neighbors, res, ver);
        }
    }

    private boolean triple_existed(List<TriplePo> list, TriplePo item) {
        for (TriplePo tmp : list) {
            if (tmp.head.equals(item.head) && tmp.relation.equals(item.relation) && tmp.tail.equals(item.tail))
                return true;
        }
        return false;
    }

    private String incr(String s) {
        return String.valueOf(Integer.parseInt(s) + 1);
    }
}