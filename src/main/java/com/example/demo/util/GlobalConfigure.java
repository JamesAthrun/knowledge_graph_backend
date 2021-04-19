package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.ItemPo;
import com.example.demo.po.GraphPo;
import com.example.demo.po.TriplePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.GlobalTrans.getJsonString;

@Configuration
public class GlobalConfigure {
    //4.11
    // 用户权限功能
    // 编辑知识图谱 持久化
    // 从json文件新建知识图谱
    // 知识图谱问答

    public final String data_path = "src/main/resources/data.json";
    public final String[] origins = new String[]{
            //在这里设置允许跨域的路由
            "http://localhost:8080", "http://localhost:8081", "http://localhost:8082",
            "http://0.0.0.0:8080", "http://0.0.0.0:8081", "http://0.0.0.0:8082",
            "http://119.3.222.50:8080", "http://119.3.222.50:8081", "http://119.3.222.50:8082",
            "http://localhost:63342", "https://jsonplaceholder.typicode.com/posts"
    };

    @Autowired
    Recorder recorder;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    TripleMapper tripleMapper;
    @Autowired
    GraphMapper graphMapper;
    @Autowired
    GlobalLogger logger;
    @Autowired
    KGService kgService;

    @Bean
    public void init() {
        if (graphMapper.getSize() != 0) {
            logger.log("data existed");
            return;
        }
        recorder.init();
        logger.log("data load begin");
        String jsonString = getJsonString(data_path);
        createGraphByJsonStr(jsonString);
        logger.log("data load end");
    }

    public void createGraphByJsonStr(String jsonString) {
        String tableId = recorder.getTableId();
        JSONObject jojo = JSONObject.parseObject(jsonString);
        graphMapper.insert(new GraphPo(tableId, jojo.getString("name"), jojo.getString("description")));

        JSONArray entity_list = jojo.getJSONArray("item");
        List<String> before = new ArrayList<>();
        List<String> after = new ArrayList<>();

        for (Object o : entity_list) {
            JSONObject jo = (JSONObject) o;
            before.add(jo.getString("id"));
            String tmp = recorder.getRecordId();
            after.add(tmp);
            itemMapper.insert(new ItemPo(tmp, tableId, jo.getString("title"), jo.getString("name"), jo.getString("division"), jo.getString("comment")));
        }

        JSONArray triple_list = jojo.getJSONArray("triple");
        for (Object o : triple_list) {
            JSONObject jo = (JSONObject) o;
            String head = jo.getString("head");
            String real_head = after.get(before.indexOf(head));
            String relation = jo.getString("relation");
            String real_relation = after.get(before.indexOf(relation));
            String tail = jo.getString("tail");
            String real_tail = after.get(before.indexOf(tail));
            tripleMapper.insert(new TriplePo(tableId, real_head, real_relation, real_tail));
        }
        recorder.save();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        addAllowedOrigins(corsConfiguration); // 1
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
//        corsConfiguration.setAllowCredentials(true); // 跨域session共享
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }

    private void addAllowedOrigins(CorsConfiguration corsConfiguration) {
        for (String origin : origins) {
            corsConfiguration.addAllowedOrigin(origin);
        }
    }

}
