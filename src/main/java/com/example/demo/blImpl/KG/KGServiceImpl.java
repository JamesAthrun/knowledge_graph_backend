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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
