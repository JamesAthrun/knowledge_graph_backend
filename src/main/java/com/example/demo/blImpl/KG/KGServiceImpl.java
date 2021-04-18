package com.example.demo.blImpl.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.data.KG.PropertyMapper;
import com.example.demo.data.KG.QuestionMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;
import com.example.demo.po.QuestionPo;
import com.example.demo.po.TriplePo;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.Recorder;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.AnswerVo;
import com.example.demo.vo.GraphInfoVo;
import com.example.demo.vo.ItemListVo;
import com.example.demo.vo.TreeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KGServiceImpl implements KGService {

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    PropertyMapper propertyMapper;
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

    @Override
    public ResultBean searchEntity(String keywords) {
        long t1 = System.currentTimeMillis();

        List<EntityPo> entities = entityMapper.searchByKeywords(keywords);
        ItemListVo itemListVo = new ItemListVo();
        for(EntityPo e:entities){
            itemListVo.addEntity(e);
        }

        List<PropertyPo> properties = propertyMapper.searchByKeywords(keywords);
        for(PropertyPo p:properties){
            itemListVo.addProperty(p);
        }

        long t2 = System.currentTimeMillis();
        logger.log("节点数 "+(entities.size()+properties.size())+" 搜索用时 "+(t2-t1)+"ms");

        return ResultBean.success(itemListVo);
    }

    @Override
    public ResultBean getGraphData(String id) {
        long t1 = System.currentTimeMillis();;

        List<TriplePo> related_link = new ArrayList<>();
        searchTriples(id,3,5,related_link);
        //depth是递归查找上限，neighbors是每层头和尾的连接上限

        GraphInfoVo go = new GraphInfoVo(entityMapper,propertyMapper);
        for(TriplePo item:related_link){
            go.addLink(item);
        }
        long t2 = System.currentTimeMillis();
        logger.log("相关节点数 "+related_link.size()+" 搜索用时 "+(t2-t1)+"ms");

        return ResultBean.success(go);
    }

    @Override
    public ResultBean getTreeData(String id) {
        long t1 = System.currentTimeMillis();;

        List<TriplePo> related_link = new ArrayList<>();
        searchTriples(id,3,5,related_link);

        TreeInfoVo to = new TreeInfoVo(id,entityMapper,propertyMapper);

        Queue<String> q = new LinkedList<>();
        q.offer(id);
        HashMap<String,ArrayList<TriplePo>> visited = new HashMap<>();

        while (q.size()>0) {
            String pId = q.poll();
            for(TriplePo triplePo: related_link){
                if(visited.get(triplePo.relation)!=null && visited.get(triplePo.relation).contains(triplePo)) continue;
                if(triplePo.head.equals(pId)){
                    to.propertyAdd(
                            to.addProperty(triplePo.head, triplePo.relation),
                            triplePo.tail);
                    q.offer(triplePo.tail);
                    //p->relation->tail
                    if (!visited.containsKey(triplePo.relation)) visited.put(triplePo.relation, new ArrayList<>());
                    visited.get(triplePo.relation).add(triplePo);//不走原路
                }
                if(triplePo.tail.equals(pId)) {
                    if (!(visited.get(triplePo.tail) != null && visited.get(triplePo.tail).contains(triplePo))) {
                        to.propertyAdd(
                                to.addProperty(triplePo.tail, triplePo.relation),
                                triplePo.head);
                        q.offer(triplePo.head);
                        //p->relation->head
                        if (!visited.containsKey(triplePo.relation)) visited.put(triplePo.relation, new ArrayList<>());
                        visited.get(triplePo.relation).add(triplePo);
                    }
                }
            }
        }

        long t2 = System.currentTimeMillis();
        logger.log("相关节点数 "+related_link.size()+" 搜索用时 "+(t2-t1)+"ms");

        return ResultBean.success(to.getRoot());
    }

    @Override
    public ResultBean createGraphByJsonStr(String jsonString){
        globalConfigure.createGraphByJsonStr(jsonString);
        return ResultBean.success();
    }

    @Override
    public ResultBean createEntity(String headId, String relationId, String tailId, String name, String comment, String nameEn, String nameCn, String division, String from) {
        if(relationId.equals("")) return ResultBean.error(103, "RelationId not given");
        String tmp = recorder.getRecordId();
        if(headId != null) {
            entityMapper.insert(new EntityPo(tmp, name, nameEn, nameCn, division, from, comment));
            ResultBean code = createLink(headId, relationId, tmp);
            if(code.code == 1) {
                createLink(tmp, relationId, tailId);
                return ResultBean.success(tmp);
            }

            else {
                deleteItem(tmp);
                return ResultBean.error(204, "CreateEntity error");
            }
        }
        else if(tailId != null) {
            entityMapper.insert(new EntityPo(tmp, name, nameEn, nameCn, division, from, comment));
            ResultBean code = createLink(tmp, relationId, tailId);
            if(code.code == 1) {
                createLink(headId, relationId, tmp);
                return ResultBean.success(tmp);
            }
            else {
                deleteItem(tmp);
                return ResultBean.error(204, "CreateEntity error");
            }
        }
        else {
            return ResultBean.error(105, "HeadId or relationId not given");
        }
    }

    @Override
    public ResultBean createProperty(String id, String comment, String nameEn, String nameCn, String from, String domain, String range) {
        String tmp = recorder.getRecordId();
        if(id.equals("")) return ResultBean.error(101, "Id not given");
        propertyMapper.insert(new PropertyPo(tmp, id, nameEn, nameCn, domain, range, from, comment));
        return ResultBean.success(tmp);
    }

    @Override
    public ResultBean createLink(String headId, String relationId, String tailId) {
        String tableId = recorder.getTableId();
        if(headId.equals("")) return ResultBean.error(102, "HeadId not given");
        if(relationId.equals("")) return ResultBean.error(103, "RelationId not given");
        if(tailId.equals("")) return ResultBean.error(104, "TailId not given");
        try {
            tripleMapper.insert(new TriplePo(tableId, headId, relationId, tailId));
        }catch (Exception e) {
            return ResultBean.error(201, "Create Link failed");
        }
        return ResultBean.success();
    }

    @Override
    public ResultBean updateItem(String id, String comment, String nameEn, String nameCn, String division, String from, String domain, String range) {
        EntityPo e = entityMapper.getByRecordId(id);
        PropertyPo p = propertyMapper.getByRecordId(id);
        if(e != null) {
            String thisId = e.id;
            entityMapper.deleteById(id);
            entityMapper.insert(new EntityPo(id, thisId, nameEn, nameCn, division, from, comment)); // 错误处理未做
            return ResultBean.success();
        }
        else if(p != null) {
            String thisId = p.id;
            propertyMapper.deleteById(id);
            entityMapper.insert(new EntityPo(id, thisId, nameEn, nameCn, division, from, comment)); // 错误处理未做
            return ResultBean.success();
        }
        return ResultBean.error(202, "Update item failed");
    }

    @Override
    public ResultBean replaceItem(String id, String headId, String relationId, String tailId, String name, String comment, String nameEn, String nameCn, String division, String from, String domain, String range) {
        // 这个函数没有进行合理性检查
        if(id.equals(headId)) {
            deleteLink(headId, relationId, tailId);
            return createEntity("", relationId, tailId, name, comment, nameEn, nameCn, division, from);
        }
        else if(id.equals(relationId)) {
            deleteLink(headId, relationId, tailId);
            return createEntity(headId, relationId, "", name, comment, nameEn, nameCn, division, from);
        }
        else if(id.equals(tailId)) {
            deleteLink(headId, relationId, tailId);
            createProperty(id, comment, nameEn,nameCn, from, domain, range);
            createLink(headId, relationId, tailId);
            return ResultBean.success();
        }
        else {
            return ResultBean.error(205, "Replace item failed");
        }
    }

    @Override
    public ResultBean deleteItem(String id) {
        if(id.equals("")) return ResultBean.error(101, "Id not given");
        try {
            entityMapper.deleteById(id);
        }catch (Exception e1) {
            try {
                propertyMapper.deleteById(id);
            } catch (Exception e2) {
                return ResultBean.error(203, "Delete item failed");
            }
        }
        return ResultBean.success();
    }

    @Override
    public ResultBean deleteLink(String headId, String relationId, String tailId) {
        if (headId.equals("")) return ResultBean.error(102, "HeadId not given");
        if (relationId.equals("")) return ResultBean.error(103, "RelationId not given");
        if (tailId.equals("")) return ResultBean.error(104, "TailId not given");
        try {
            tripleMapper.delete(headId, relationId, tailId);
        } catch (Exception e) {
            return ResultBean.error(201, "Delete link failed");
        }
        return ResultBean.success();
    }

    @Override
    public ResultBean ask(String questionStr) {
        List<QuestionPo> qs = new ArrayList<>(questionMapper.getAll());
        HashMap<QuestionPo,Integer> votes = new HashMap<>();

        for(QuestionPo q:qs){
            List<String> tmp = q.getKeyWords();
            for(String kw:tmp) {
                if(questionStr.contains(kw)) votes.merge(q,0,(oldValue,newValue)->oldValue+1);
            }
        }
        votes.put(null,0);
        QuestionPo max = null;
        for(QuestionPo q:votes.keySet())
            if(votes.get(q)>votes.get(max)) max = q;
        if(max==null) return ResultBean.error(0,"no match question");

        AnswerVo ao = new AnswerVo(entityMapper,max.help);
        List<String> relatedIds = max.getRelatedIds();
        for(String id:relatedIds) {
            ao.addTableItem(id);
        }
        return ResultBean.success(ao);
    }

    private void searchTriples(String id, int depth,int neighbors, List<TriplePo> res){
        if(depth==0) return;
        MySet(res);
        List<TriplePo> cases = tripleMapper.getRelatedTriples(id);
        List<TriplePo> tmp_h = new ArrayList<>();
        List<TriplePo> tmp_r = new ArrayList<>();
        List<TriplePo> tmp_t = new ArrayList<>();
        for(TriplePo tri:cases){
            //注意避免re_hit的情况
            if(!triple_existed(res,tri)) {
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
        for (TriplePo tri:tmp_h) {
            if(!tri.tail.equals(id)) searchTriples(tri.tail,depth-1,neighbors,res);
        }
        for (TriplePo tri:tmp_t) {
            if(!tri.head.equals(id)) searchTriples(tri.head,depth-1,neighbors,res);
        }
    }

    //去重
    private static <T> void MySet(List<T> in){
        HashSet<T> out = new HashSet<>(in);
        for(T item: in){
            if(out.contains(item)) continue;
            out.add(item);
        }
        in.clear();
        in.addAll(out);
    }

    private static <T> List<T> getRandomList(List<T> paramList,int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random(0);
        List<Integer> tempList=new ArrayList<>();
        List<T> newList=new ArrayList<T>();
        for(int i=0,temp;i<count;){
            temp=random.nextInt(paramList.size());
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
                i += 1;
            }
        }
        return newList;
    }

    private boolean triple_existed(List<TriplePo> list,TriplePo item){
        for(TriplePo tmp: list){
            if(tmp.head.equals(item.head) && tmp.relation.equals(item.relation) && tmp.tail.equals(item.tail)) return true;
        }
        return false;
    }
}