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
        List<String[]> num_id_map = gm.getNumIdMap();

        for(String[] item:tri_num){
            TriplePo tp = new TriplePo(item[0],item[1],item[2]);
            tripleMapper.insertTriple(tp);
        }
        for(String[] item:num_id_map){
            NumIdMapPo np = new NumIdMapPo(item[0],item[1]);
            numIdMapMapper.insertNumIdMap(np);
        }
        System.out.println("data init over");
    }

    @Override
    public ResultBean searchEntity(String keywords) {
        List<EntityPo> entities = numIdMapMapper.searchEntity(keywords);
        EntityListVo entityListVo = new EntityListVo();
        for(EntityPo e:entities){
            entityListVo.addEntity(e.num,e.id);
        }
        return ResultBean.success(entityListVo);
    }

    @Override
    public ResultBean getGraphData(String id) {
        ArrayList<TriplePo> res_link = new ArrayList<>();
        ArrayList<String> related_ids = new ArrayList<>();
        searchTriples(id,1,res_link);//times是递归查找次数
        for(TriplePo item:res_link){
            if(!related_ids.contains(item.head)) related_ids.add(item.head);
            if(!related_ids.contains(item.relation)) related_ids.add(item.relation);
            if(!related_ids.contains(item.tail)) related_ids.add(item.tail);
        }

        GraphVo go = new GraphVo();
        for(TriplePo item:res_link){
            go.addLink(item.head,item.tail,item.relation);
        }
        for(String entity_id:related_ids){
            EntityPo entity = numIdMapMapper.getEntityById(entity_id);
            go.addData(entity.num,entity.id);
        }
        return ResultBean.success(go);
    }

    public void searchTriples(String id, int times,List<TriplePo> res){
        if(times==0) return;
        res = MySet(res);
        List<TriplePo> cases = tripleMapper.getRelatedTriples(id);
        res.addAll(cases);
        for(TriplePo tri:cases){
            if(!tri.head.equals(id)) searchTriples(tri.head,times-1,res);
            //搜索头尾的关联节点，不搜索关系的关联节点
            //后续可以视情况改成搜索指定数量的关系的关联节点
            //todo
            //if(!tri.relation.equals(id)) searchTriples(tri.relation,times-1,res);
            if(!tri.tail.equals(id)) searchTriples(tri.tail,times-1,res);
        }
    }

    //去重
    public List<TriplePo> MySet(List<TriplePo> in){
//        ArrayList<TriplePo> out = new ArrayList<>();
//        for(TriplePo item: in){
//            if(triple_existed(out,item)) continue;
//            out.add(item);
//        }
//        in.clear();
//        in.addAll(out);
//        return in;
        HashSet<TriplePo> out = new HashSet<>(in);
        for(TriplePo item: in){
            if(out.contains(item)) continue;
            out.add(item);
        }
        in.clear();
        in.addAll(out);
        return in;
    }

//    public boolean triple_existed(List<TriplePo> list,TriplePo item){
//        for(TriplePo tmp: list){
//            if(tmp.head.equals(item.head) && tmp.relation.equals(item.relation) && tmp.tail.equals(item.tail)) return true;
//        }
//        return false;
//    }
}
