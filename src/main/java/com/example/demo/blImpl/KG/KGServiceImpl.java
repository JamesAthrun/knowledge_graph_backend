package com.example.demo.blImpl.KG;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.data.KG.PropertyMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;
import com.example.demo.po.TriplePo;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.GraphInfoVo;
import com.example.demo.vo.NodeListVo;
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

    @Override
    public ResultBean searchEntity(String keywords) {
        long t1 = System.currentTimeMillis();;

        List<EntityPo> entities = entityMapper.searchByKeywords(keywords);
        NodeListVo nodeListVo = new NodeListVo();
        for(EntityPo e:entities){
            nodeListVo.addEntity(e);
        }

        List<PropertyPo> properties = propertyMapper.searchByKeywords(keywords);
        for(PropertyPo p:properties){
            nodeListVo.addProperty(p);
        }

        long t2 = System.currentTimeMillis();
        logger.log("节点数 "+(entities.size()+properties.size())+" 搜索用时 "+(t2-t1)+"ms");

        return ResultBean.success(nodeListVo);
    }

    @Override
    public ResultBean getGraphData(String id) {
        long t1 = System.currentTimeMillis();;

        List<TriplePo> related_link = new ArrayList<>();
        List<String> related_ids = new ArrayList<>();
        searchTriples(id,3,5,related_link);
        //depth是递归查找上限，neighbors是每层头和尾的连接上限

        for(TriplePo item:related_link){
            related_ids.add(item.head);
            related_ids.add(item.relation);
            related_ids.add(item.tail);
        }
        MySet(related_ids);
        GraphInfoVo go = new GraphInfoVo();
        for(String recordId:related_ids){
            EntityPo e = entityMapper.getByRecordId(recordId);
            PropertyPo p = propertyMapper.getByRecordId(recordId);
            if(e!=null)  go.addData(e);
            if(p!=null)  go.addData(p);
        }
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

        TreeInfoVo to = new TreeInfoVo();

        Queue<String> q = new LinkedList<String>();
        q.offer(id);
        HashMap<String,Integer> visited = new HashMap<>();
        for(TriplePo triplePo: related_link){
            if(visited.containsKey(triplePo.head)) visited.put(triplePo.head,visited.get(triplePo.head) +1);
            else visited.put(triplePo.head,1);
            if(visited.containsKey(triplePo.tail)) visited.put(triplePo.tail,visited.get(triplePo.tail) +1);
            else visited.put(triplePo.tail,1);
        }

        while (q.size()>0) {
            String pId = q.poll();
            for(TriplePo triplePo: related_link){
                if(triplePo.head.equals(pId) && visited.get(triplePo.tail)!=0){
                    to.propertyAdd(
                            to.addProperty(triplePo.head,triplePo.relation,entityMapper,propertyMapper),
                            triplePo.tail,entityMapper,propertyMapper);
                    q.offer(triplePo.tail);
                    //p->relation->tail
                    visited.put(triplePo.head,visited.get(triplePo.head)-1);
                    visited.put(triplePo.tail,visited.get(triplePo.tail)-1);
                }
                if(triplePo.tail.equals(pId) && visited.get(triplePo.head)!=0){
                    to.propertyAdd(
                            to.addProperty(triplePo.tail,triplePo.relation,entityMapper,propertyMapper),
                            triplePo.head,entityMapper,propertyMapper);
                    q.offer(triplePo.head);
                    //p->relation->head
                    visited.put(triplePo.head,visited.get(triplePo.head)-1);
                    visited.put(triplePo.tail,visited.get(triplePo.tail)-1);
                }
            }
        }

        long t2 = System.currentTimeMillis();
        logger.log("相关节点数 "+related_link.size()+" 搜索用时 "+(t2-t1)+"ms");

        JSONObject root = to.getNode(id,entityMapper,propertyMapper);
        return ResultBean.success(root);
    }

    @Override
    public ResultBean createGraphByJsonStr(String jsonString){
        globalConfigure.createGraphByJsonStr(jsonString);
        return ResultBean.success();
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