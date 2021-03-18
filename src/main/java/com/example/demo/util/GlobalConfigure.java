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

@Configuration
public class GlobalConfigure {
    //final String filepath = "src/main/resources/covid-19-prevention-2020-03-11.json";
    final String cr_path = "src/main/resources/cr.json";
    final String p_path = "src/main/resources/p.json";
    final String t_path = "src/main/resources/t.json";
    final boolean needInit = false;
    private final String[] origins = new String[]{
        //在这里设置允许跨域的路由
        "http://localhost:8000",
        "http://localhost:63342",
    };

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    TripleMapper tripleMapper;

    @Autowired
    public void init(){
        if(!needInit) return;
        String cr = KGManager.getJsonString(cr_path);
        String p = KGManager.getJsonString(p_path);
        String t = KGManager.getJsonString(t_path);
        initFromJSONStr(cr,p,t);
    }

    private void initFromJSONStr(String cr,String p,String t){
        JSONArray cr_list = JSONObject.parseObject(cr).getJSONArray("data");
        for(Object o:cr_list){
            JSONObject jo = (JSONObject) o;
            entityMapper.insert(new EntityPo(jo.getString("recordId"),jo.getString("id"),jo.getString("nameEn"),jo.getString("nameCn"),jo.getString("division"),jo.getString("from"),jo.getString("comment")));
        }
        JSONArray p_list = JSONObject.parseObject(p).getJSONArray("data");
        for(Object o:p_list){
            JSONObject jo = (JSONObject) o;
            propertyMapper.insert(new PropertyPO(jo.getString("recordId"),jo.getString("id"),jo.getString("nameEn"),jo.getString("nameCn"),jo.getString("domain"),jo.getString("range"),jo.getString("from"),jo.getString("comment")));
        }
        JSONArray triple = JSONObject.parseObject(t).getJSONArray("data");
        for(Object o:triple){
            JSONObject jo = (JSONObject) o;
            tripleMapper.insert(new TriplePo(jo.getString("head"),jo.getString("relation"),jo.getString("tail")));
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
