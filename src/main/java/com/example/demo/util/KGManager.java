package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class KGManager {
    private ArrayList<KGNode> nodeList;
    //编号对应
    private ArrayList<String> numList;

    private List<String[]> tri_set_id,tri_set_num;


    public KGManager(){
        this.nodeList = new ArrayList<>();
    }

    public KGManager(String filepath){
        this.nodeList = new ArrayList<>();

        String jsonStr = getJsonString(filepath);
        JSONArray list = JSONObject.parseObject(jsonStr).getJSONArray("data");

        this.add(new KGNode("_id"));
        this.add(new KGNode("_type"));

        for(int i=0;i<list.size();i++){
            JSONObject jo =  list.getJSONObject(i);

            //"_id"字段是必有的
            //处理id
            String headId = jo.getString("_id");
            //添加头实体
            KGNode head = this.searchByIDWithBuild(headId);
            this.add(head);

            //"_type"字段有一个例外
            if(jo.containsKey("_type")) {
                //处理type
                JSONArray type_arr = jo.getJSONArray("_type");
                for (Object value : type_arr) {
                    String typeId = value.toString();
                    KGNode type = this.searchByIDWithBuild(typeId);
                    head.addCouple(this.searchByID("_type"), type);
                }
            }

            //添加关系实体
            List<String> key_arr = getKeySArr(jo);

            for (Object value : key_arr) {
                String relationId = value.toString();
                KGNode relation = this.searchByIDWithBuild(relationId);

                //添加尾实体
                //尾实体是一个JSONArray
                JSONArray tail_arr = jo.getJSONArray(relationId);
                for (Object o : tail_arr) {
                    //尾实体内部是一个JsonObject，该JsonObject内部有3种东西："_list"，"_id"，"_value"和"_language"

                    JSONObject tail_jo = (JSONObject) (o);

                    if (tail_jo.containsKey("_list")) {
                        //尾实体是一个JSONArray
                        JSONArray tail_list = tail_jo.getJSONArray("_list");
                        for (int m = 0; m < tail_list.size(); m++) {
                            String tailId = tail_list.getJSONObject(m).getString("_id");
                            KGNode tail = this.searchByIDWithBuild(tailId);
                            head.addCouple(relation,tail);
                        }

                    } else if (tail_jo.containsKey("_id")) {
                        //尾实体是其他节点的引用
                        String tailId = tail_jo.get("_id").toString();
                        KGNode tail = this.searchByIDWithBuild(tailId);
                        head.addCouple(relation,tail);

                    } else if (tail_jo.containsKey("_value") || tail_jo.containsKey("_language")) {
                        //尾实体是一个值（定义为一个JSON字符串）
                        KGNode tail = this.searchByIDWithBuild(tail_jo.toJSONString());
                        head.addCouple(relation,tail);
                    }

                }
            }
        }
    }

    private KGNode searchByID(String id){
        KGNode res;
        for (KGNode kgNode : this.nodeList) {
            res = kgNode;
            if (res.id.equals(id)) return res;
        }
        return null;
    }

    private KGNode searchByIDWithBuild(String id){
        KGNode res = this.searchByID(id);
        if(res==null) {
            res = new KGNode(id);
            this.add(res);
        }
        return res;
    }

    private void add(KGNode kgn){
        if(searchByID(kgn.id)!=null) return;
        this.nodeList.add(kgn);
    }

    private List<String> getKeySArr(JSONObject jo) {
        Object[] tmp = jo.keySet().toArray();
        List<String> res = new ArrayList<>();
        for (Object o : tmp) {
            String key = o.toString();
            if (key.equals("_id") || key.equals("_type")) continue;
            res.add(key);
        }
        return res;
    }

    public static String getJsonString(String filepath){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)), StandardCharsets.UTF_8));
            String line;
            StringBuilder res = new StringBuilder();
            while((line = br.readLine()) != null){
                res.append(line);
            }
            return res.toString();
        }catch (Exception e){
            return null;
        }
    }

    private void initNumList(){
        numList = new ArrayList<>();
        for(int i=0;i<nodeList.size();i++){
            numList.add(mapToNum(i).toString());
        }
    }

    private Integer mapToNum(int i){
        return 86400+i*101+i%17;
    }

    private String getNum(KGNode n){
        return numList.get(nodeList.indexOf(n));
    }

    private void initTripleSet(){
        tri_set_id  = new ArrayList<>();
        tri_set_num  = new ArrayList<>();
        if(numList==null) initNumList();
        for(KGNode n: nodeList){
            for(int i=0;i<n.relations.size();i++){
                String[] triple_id = {n.id,n.relations.get(i).id,n.tails.get(i).id};
                String[] triple_num = {getNum(n),getNum(n.relations.get(i)),getNum(n.tails.get(i))};
                tri_set_id.add(triple_id);
                tri_set_num.add(triple_num);
            }
        }
    }

    public List<String[]> getTripleSetById(){
        if(tri_set_id==null) initTripleSet();
        return  tri_set_id;
    }

    public List<String[]> getTripleSetByNum(){
        if(tri_set_num==null) initTripleSet();
        return  tri_set_num;
    }

    public List<String[]> getNumIdMap(){
        ArrayList<String[]> res = new ArrayList<>();
        for(int i=0;i<nodeList.size();i++){
            String[] item = {numList.get(i),nodeList.get(i).id};
            res.add(item);
        }
        return res;
    }
}
