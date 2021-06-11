package com.example.demo.blImpl.KG;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.bl.KG.KGService;
import com.example.demo.data.Account.AccountMapper;
import com.example.demo.data.Account.UserGroupMapper;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.data.KG.QuestionMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.*;
import com.example.demo.util.Timer;
import com.example.demo.util.*;
import com.example.demo.vo.AnswerVo;
import com.example.demo.vo.GraphInfoVo;
import com.example.demo.vo.ItemListVo;
import com.example.demo.vo.KGEditFormVo;
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
    @Autowired
    UserGroupMapper userGroupMapper;
    @Autowired
    AccountMapper accountMapper;
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

    private List<String> getAllRoots(List<TriplePo> tris){
        List<String> res = new ArrayList<>();
        HashMap<String, Integer> tmp = new HashMap<>();
        for (TriplePo tri :
                tris) {
            tmp.merge(tri.head,0,(o,n)->o);
            tmp.merge(tri.relation,1,(o,n)->1);
            tmp.merge(tri.tail,1,(o,n)->1);
        }
        for (String id :
                tmp.keySet()) {
            if(tmp.get(id).equals(0)){
                res.add(id);
            }
        }
        return res;
    }

    @Override
    public ResultBean getTreeData(String id, String ver) {
        timer.set();

        List<TriplePo> related_link = new ArrayList<>();
        searchTriples(id, 3, 5, related_link, ver);
        //depth是递归查找上限，neighbors是每层头和尾的连接上限

        HashMap<String, Integer> counter = new HashMap<>();

        GraphInfoVo go = new GraphInfoVo(itemMapper, ver);

        List<String> roots = getAllRoots(related_link);
        final String TreeRoot = "19822994";
        final String SubRoot = "19736712";
        for (String root :
                roots) {
            related_link.add(new TriplePo(null,TreeRoot,SubRoot,root));
        }
        //找到所有的root，然后将其连接到一个统一的虚根上，以表示为一棵树

        for (TriplePo tri : related_link) {
            if (!counter.containsKey(tri.tail)) {
                go.addLink(tri);
                counter.put(tri.tail, 1);
            } else {
                go.addLinkCopy(tri, counter.get(tri.tail));
                counter.merge(tri.tail, 0, (oldValue, newValue) -> oldValue + 1);
            }
        }

        logger.log("相关节点数 " + related_link.size() + " 搜索用时 " + timer.get());

        return ResultBean.success(go);

    }

    @Override
    public ResultBean createGraphByJsonStr(String jsonString, String name) {
        AccountPo accountPo = accountMapper.selectAccountByName(name);
        globalConfigure.createGraphByJsonStr(jsonString, accountPo.userId);
        return ResultBean.success();
    }

    @Override
    public ResultBean commitChange(KGEditFormVo f) {
        Integer res = redisUtil.OpCommitItemChange(f);
        return res == 1 ? ResultBean.success() : ResultBean.error(701, "commit fail");
    }

    @Override
    public ResultBean cancelChange(String userName) {
        Integer res = redisUtil.OpCancelItemChange(userName);
        return res == 1 ? ResultBean.success() : ResultBean.error(702, "cancel fail");
    }

    private String getReplacePosition(KGEditFormVo f) {
        if (f.id.equals(f.headId))
            return "head";
        else if (f.id.equals(f.relationId))
            return "relation";
        else if (f.id.equals(f.tailId))
            return "tail";
        else return null;
    }

    private HashMap<KGEditFormVo, String> handleId(List<KGEditFormVo> fs, Recorder recorder) {
        List<String> idToMap = new ArrayList<>();
        HashMap<KGEditFormVo, String> replaceMap = new HashMap<>();
        for (KGEditFormVo f : fs) {
            String[] ids = {f.headId, f.relationId, f.tailId, f.id};
            idToMap.addAll(Arrays.asList(ids));
            if (f.op.equals("replaceItem")) {
                replaceMap.put(f, getReplacePosition(f));
            }
        }

        HashMap<String, String> idMap = new HashMap<>();
        for (String key : idToMap) {
            if (!key.equals("") && Integer.parseInt(key) <= 1000) idMap.put(key, recorder.getRecordId());
        }

        for (KGEditFormVo f : fs) {
            if (idMap.containsKey(f.headId))
                f.headId = idMap.get(f.headId);
            if (idMap.containsKey(f.relationId))
                f.relationId = idMap.get(f.relationId);
            if (idMap.containsKey(f.tailId))
                f.tailId = idMap.get(f.tailId);
            if (idMap.containsKey(f.id))
                f.id = idMap.get(f.id);
        }
        return replaceMap;
    }

    @Override
    public ResultBean confirmChange(String userName) {
        List<String> ops = redisUtil.getOpsOfUser(userName);
        List<ResultBean> resList = new ArrayList<>();
        String tableId = Trans.jsonStrToJavaObject(ops.iterator().next(), KGEditFormVo.class).tableId;
        String ver = graphMapper.getPresentVer(tableId);

        List<KGEditFormVo> fs = new ArrayList<>();
        for (String op : ops) {
            fs.add(Trans.jsonStrToJavaObject(op, KGEditFormVo.class));
        }

        HashMap<KGEditFormVo, String> replaceMap = handleId(fs, recorder);
        JSONArray detail = new JSONArray();

        for (KGEditFormVo f : fs) {
            // id映射
            switch (f.op) {
                case "createItem":
                    resList.add(createItem(
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
                            replaceMap.get(f),
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
                            f.tableId,
                            ver));
                    break;
                default:
                    resList.add(ResultBean.error(703, "op fail"));
            }
            detail.add(f.toJSONObject());
        }

        for (ResultBean res : resList) {
            if (res.code != 1) return res;
        }

        graphMapper.confirmChange(incr(ver), tableId);//图谱版本号+1
        graphMapper.createHistory(tableId, incr(ver), Timer.getFormatTime(), detail.toJSONString());//记录操作历史
        redisUtil.OpConfirmChange(userName);//清除redis中的记录

        return ResultBean.success();
    }

    @Override
    public ResultBean rollBackChange(String ver, String tableId) {
        graphMapper.rollBack(ver, tableId);
        graphMapper.createHistory(tableId, ver, Timer.getFormatTime(), "NKG: roll back to ver " + ver);
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
    public ResultBean getGraphHistory(String tableId) {
        List<HistoryPo> hisList = graphMapper.getHistory(tableId);
        return ResultBean.success(HistoryPo.toJsonArray(hisList));
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

    @Override
    public ResultBean changeTablePermission(String tableId, int authority) {
        if (authority < 0 || authority / 100 > 2 || (authority / 10) % 10 > 2 || authority % 10 > 2)
            return ResultBean.error(-1, "权限设置错误");
        graphMapper.updateAuthority(tableId, authority);
        return ResultBean.success();
    }

    private ResultBean createItem(String headId, String relationId, String tailId, String id, String tableId, String title, String name, String division, String comment, String ver) {
        itemMapper.insert(new ItemPo(id, tableId, title, name, division, comment, incr(ver), "0"));
        if (id.equals(headId) || id.equals(relationId) || id.equals(tailId)) createLink(tableId, headId, relationId, tailId, ver);
        return ResultBean.success();
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

    private ResultBean replaceItem(String headId, String relationId, String tailId, String id, String position, String tableId, String title, String name, String division, String comment, String ver) {
        switch (position) {
            case "head":
                deleteLink(headId, relationId, tailId, tableId, ver);
                createItem(id, relationId, tailId, id, tableId, title, name, division, comment, ver);
                break;
            case "tail":
                deleteLink(headId, relationId, tailId, tableId, ver);
                createItem(headId, relationId, id, id, tableId, title, name, division, comment, ver);
                break;
            case "relation":
                //阻塞掉原有
                deleteLink(headId, relationId, tailId, tableId, ver);
                createItem(headId, id, tailId, id, tableId, title, name, division, comment, ver);
                createLink(tableId, headId, id, tailId, ver);
                break;
        }
        return ResultBean.success();
    }

    private ResultBean deleteItem(String id, String ver) {
        ItemPo tmpItem = itemMapper.getById(id, ver);
        tmpItem.ver = incr(ver);
        //标记位置1，表示阻塞
        tmpItem.drop = "1";
        itemMapper.insert(tmpItem);
        return ResultBean.success();
    }

    private ResultBean deleteLink(String headId, String relationId, String tailId, String tableId, String ver) {
        TriplePo tmpTri = new TriplePo(tableId, headId, relationId, tailId, incr(ver), "1");
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
            if (!tri.existIn(res)) {
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

    private String incr(String s) {
        return String.valueOf(Integer.parseInt(s) + 1);
    }
}