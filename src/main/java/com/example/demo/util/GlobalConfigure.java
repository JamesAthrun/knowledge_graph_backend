package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.data.KG.TripleMapper;
import com.example.demo.po.GraphPo;
import com.example.demo.po.ItemPo;
import com.example.demo.po.TriplePo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Properties;

import static com.example.demo.util.GlobalTrans.getJsonString;

@Configuration
public class GlobalConfigure {
    // 4.11
    // 用户权限功能
    // 编辑知识图谱 持久化
    // 从json文件新建知识图谱
    // 知识图谱问答

    private final String dataPath = "src/main/resources/data.json";
    private final String propertyPath = "application.yml";
    private final String sqlPath = "sql/init.sql";
    private final boolean autoInitDB = true;
    private final String[] origins = new String[]{
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
    @Autowired
    Timer timer;

    @Bean
    public void init() {
        if(autoInitDB)
            runSqlScript(sqlPath,propertyPath);
        if (graphMapper.getSize() != 0) {
            logger.log("data existed");
        }
        else {
            recorder.init();
            logger.log("data load begin");
            String jsonString = getJsonString(dataPath);
            createGraphByJsonStr(jsonString);
            logger.log("data load end");
        }
    }

    public void createGraphByJsonStr(String jsonString) {
        timer.set();

        String tableId = recorder.getTableId();
        JSONObject jojo = JSONObject.parseObject(jsonString);
        graphMapper.insert(new GraphPo(tableId, jojo.getString("name"), jojo.getString("description")));

        JSONArray entity_list = jojo.getJSONArray("item");
        HashMap<String, String> map = new HashMap<>();

        for (Object o : entity_list) {
            JSONObject jo = (JSONObject) o;
            String tmp = recorder.getRecordId();
            map.put(jo.getString("id"), tmp);
            itemMapper.insert(new ItemPo(tmp, tableId, jo.getString("title"), jo.getString("name"), jo.getString("division"), jo.getString("comment")));
        }

        JSONArray triple_list = jojo.getJSONArray("triple");
        for (Object o : triple_list) {
            JSONObject jo = (JSONObject) o;
            String real_head = map.get(jo.getString("head"));
            String real_relation = map.get(jo.getString("relation"));
            String real_tail = map.get(jo.getString("tail"));
            tripleMapper.insert(new TriplePo(tableId, real_head, real_relation, real_tail));
        }
        logger.log("建图用时 " + timer.get());

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

    private void runSqlScript(String sqlFilePath,String propertyPath){
        try {
            // 获取数据库相关配置信息
            Properties props = Resources.getResourceAsProperties(propertyPath);
            // jdbc 连接信息
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            // 建立连接
            Connection conn = DriverManager.getConnection(url, username, password);
            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            // 执行SQL脚本
            runner.runScript(Resources.getResourceAsReader(sqlFilePath));
            // 关闭连接
            conn.close();
            // 若成功，打印提示信息
            logger.log("exec sql script " + sqlFilePath);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
