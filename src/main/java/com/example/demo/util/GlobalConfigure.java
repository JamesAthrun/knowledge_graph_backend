package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.data.KG.PropertyMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPO;
import com.example.demo.po.TriplePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GlobalConfigure {
    //final String filepath = "src/main/resources/covid-19-prevention-2020-03-11.json";
    final String data_path = "src/main/resources/data.json";
    private final String[] origins = new String[]{
        //在这里设置允许跨域的路由
        "http://localhost:8080", "http://localhost:8081", "http://localhost:8082",
        "http://localhost:63342", "https://jsonplaceholder.typicode.com/posts"
    };

    @Autowired
    Recorder recorder;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    TripleMapper tripleMapper;

    @Autowired
    public void init(){
        if(tripleMapper.getListSize()>0) {
            System.out.println("data existed");
            return;
        }
        System.out.println("data load begin");
        String jsonString = KGManager.getJsonString(data_path);
        initFromJSONStr(jsonString);
        System.out.println("data load end");
    }

    private void initFromJSONStr(String jsonString){
        JSONArray entity_list = JSONObject.parseObject(jsonString).getJSONArray("entity");
        List<String> before = new ArrayList<>();
        List<String> after = new ArrayList<>();
        String tableId = recorder.getTableId();
        for(Object o:entity_list){
            JSONObject jo = (JSONObject) o;
            before.add(jo.getString("recordId"));
            String tmp = recorder.getRecordId();
            after.add(tmp);
            entityMapper.insert(new EntityPo(tmp,jo.getString("id"),jo.getString("nameEn"),jo.getString("nameCn"),jo.getString("division"),jo.getString("from"),jo.getString("comment")));
        }
        JSONArray property_list = JSONObject.parseObject(jsonString).getJSONArray("property");
        for(Object o:property_list){
            JSONObject jo = (JSONObject) o;
            before.add(jo.getString("recordId"));
            String tmp = recorder.getRecordId();
            after.add(tmp);
            propertyMapper.insert(new PropertyPO(tmp,jo.getString("id"),jo.getString("nameEn"),jo.getString("nameCn"),jo.getString("domain"),jo.getString("range"),jo.getString("from"),jo.getString("comment")));
        }
        JSONArray triple_list = JSONObject.parseObject(jsonString).getJSONArray("triple");
        for(Object o:triple_list){
            JSONObject jo = (JSONObject) o;
            String head = jo.getString("head");
            String real_head = after.get(before.indexOf(head));
            String relation = jo.getString("relation");
            String real_relation = after.get(before.indexOf(relation));
            String tail = jo.getString("tail");
            String real_tail = after.get(before.indexOf(tail));
            tripleMapper.insert(new TriplePo(recorder.getRecordId(),tableId,real_head,real_relation,real_tail));
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        addAllowedOrigins(corsConfiguration); // 1
        //corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        corsConfiguration.setAllowCredentials(true); // 跨域session共享
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }

    private void addAllowedOrigins(CorsConfiguration corsConfiguration) {
        for (String origin : origins) {
            corsConfiguration.addAllowedOrigin(origin);
        }
    }
}
