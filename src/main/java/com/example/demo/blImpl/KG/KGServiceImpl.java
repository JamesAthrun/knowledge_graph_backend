package com.example.demo.blImpl.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.NumIdMapMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.NumIdMapPo;
import com.example.demo.po.TriplePo;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.KGManager;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.EntityListVo;
import com.example.demo.vo.GraphVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Service
public class KGServiceImpl implements KGService {

    @Autowired
    NumIdMapMapper numIdMapMapper;
    @Autowired
    TripleMapper tripleMapper;
    @Autowired
    GlobalConfigure gc;
    @Autowired
    void initKG(GlobalConfigure gc){
        if(!gc.needInit) return;
        KGManager gm = new KGManager("src/main/resources/covid-19-prevention-2020-03-11.json");
        List<String[]> tri_num = gm.getTripleSetByNum();
        List<String[]> tri_id = gm.getTripleSetById();
        List<String[]> num_id_map = gm.getNumIdMap();
        for(String[] item:tri_num){
            TriplePo tp = new TriplePo(item[0],item[1],item[2]);
            tripleMapper.insertTriple(tp);
        }
        for(String[] item:tri_id){
            TriplePo tp = new TriplePo(item[0],item[1],item[2]);
            tripleMapper.insertTriple_zh(tp);
        }
        for(String[] item:num_id_map){
            NumIdMapPo np = new NumIdMapPo(item[0],item[1]);
            numIdMapMapper.insertNumIdMap(np);
        }
        System.out.println("data init over");
    }

    @Override
    public ResultBean searchEntity(String keywords) {
        long t1 = System.currentTimeMillis();;

        List<EntityPo> entities = numIdMapMapper.searchEntity(keywords);
        EntityListVo entityListVo = new EntityListVo();
        for(EntityPo e:entities){
            entityListVo.addEntity(e.num,e.id);
        }

        long t2 = System.currentTimeMillis();
        System.out.println("节点数 "+entities.size()+" 搜索用时 "+(t2-t1)+"ms");

        return ResultBean.success(entityListVo);
    }

    @Override
    public ResultBean getGraphData(String id) {
        long t1 = System.currentTimeMillis();;

        List<TriplePo> related_link = new ArrayList<>();
        List<String> related_ids = new ArrayList<>();
        searchTriples(id,3,4,related_link);//depth是递归查找上限，neighbors是每层头和尾的连接上限

        for(TriplePo item:related_link){
            related_ids.add(item.head);
            related_ids.add(item.relation);
            related_ids.add(item.tail);
        }
        MySet(related_ids);
        GraphVo go = new GraphVo();
        for(TriplePo item:related_link){
            go.addLink(item.head,item.tail,item.relation);
        }
        for(String entity_id:related_ids){
            EntityPo entity = numIdMapMapper.getEntityById(entity_id);
            go.addData(entity.num,entity.id);
        }

        long t2 = System.currentTimeMillis();
        System.out.println("相关节点数 "+related_link.size()+" 搜索用时 "+(t2-t1)+"ms");

        return ResultBean.success(go);
    }

    public void searchTriples(String id, int depth,int neighbors, List<TriplePo> res){
        if(depth==0) return;
//        res = MySet(res);
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
        //作为关系节点时，对头节点和尾节点进行相关搜索
        //作为尾节点时，对头节点进行相关搜索
        for (TriplePo tri:tmp_h) {
            if(!tri.tail.equals(id)) searchTriples(tri.tail,depth-1,neighbors-1,res);
        }
        for (TriplePo tri:tmp_r) {
            if(!tri.head.equals(id)) searchTriples(tri.head,depth-1,neighbors-1,res);
            if(!tri.tail.equals(id)) searchTriples(tri.tail,depth-1,neighbors-1,res);
        }
        for (TriplePo tri:tmp_t) {
            if(!tri.head.equals(id)) searchTriples(tri.head,depth-1,neighbors-1,res);
        }
    }

    //去重
    public static <T> void MySet(List<T> in){
        HashSet<T> out = new HashSet<>(in);
        for(T item: in){
            if(out.contains(item)) continue;
            out.add(item);
        }
        in.clear();
        in.addAll(out);
    }

    public static <T> List<T> getRandomList(List<T> paramList,int count){
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

    public boolean triple_existed(List<TriplePo> list,TriplePo item){
        for(TriplePo tmp: list){
            if(tmp.head.equals(item.head) && tmp.relation.equals(item.relation) && tmp.tail.equals(item.tail)) return true;
        }
        return false;
    }
}