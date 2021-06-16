package com.example.demo.util;

import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.data.KG.TripleMapper;
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
import java.util.Properties;

import static com.example.demo.util.Trans.getJsonStringFromPath;

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
    private final String qPath = "src/main/resources/question.json";
    private final boolean autoInitDB = true;
    private final String[] origins = new String[]{
            //在这里设置允许跨域的路由
            "http://localhost:8080", "http://localhost:8081", "http://localhost:8082",
            "http://0.0.0.0:8080", "http://0.0.0.0:8081", "http://0.0.0.0:8082",
            "http://119.3.222.50:8080", "http://119.3.222.50:8081", "http://119.3.222.50:8082",
            "http://123.57.200.185:8099",
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
    public void init(){
        if (autoInitDB)
            doInit();
    }

    public void doInit() {
        runSqlScript(sqlPath, propertyPath);
        recorder.init();
        logger.log("data load begin");
        kgService.createGraphByJsonStr(getJsonStringFromPath(dataPath), "obama");
        kgService.createQuestionByJsonStr(getJsonStringFromPath(qPath));
        logger.log("data load end");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        addAllowedOrigins(corsConfiguration); // 1
//        corsConfiguration.addAllowedOrigin("*");
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

    private void runSqlScript(String sqlFilePath, String propertyPath) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
