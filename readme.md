# Readme
# 后端的几点说明

## 与前端的交互
### 1.接受请求
GET/POST   
原则上，对简单请求，「删查」使用GET，「增改」使用POST  
POST请求的请求头需带上 'content-type': 'application/json'，RequestBody需使用json字符串格式  
### 2.返回请求
response.content的数据格式为bytes，json解析后得到字典  
{'code':xxx,'message':xxx,'data':xxx}  
其中，code为状态码，message为描述，data为返回对象（json字符串格式）  
#### 操作成功
code为1，message为null  
对不需要返回对象的操作（如简单的「增删改」），data为null  
对需要返回对象的操作（如简单的「查」），对data再次进行json解析后可得到返回对象，如{'id': '1', 'name': 'Jack', 'score':'100'}  
#### 操作失败
code大于1，message为错误信息，data为null  
### *3.加密
后续视需求对response.content进行加密

## 后端项目结构
controller 控制层 如JackController.java  
bl 业务逻辑层接口 如JackService.java  
blImpl 业务逻辑层实现 如JackServiceImpl.java  
data 数据层接口 如JackMapper.java  
dataImpl 数据层实现 如JackMapper.xml，在resources目录下  
po 略  
vo 略  
util 一些工具类  

### 3.异常处理
原则上异常在util.GlobalExceptionHandler统一处理  
### 4.返回值
业务逻辑层的返回值用util.ResultBean统一封装
### 5.数据库
使用mysql数据库，resources目录下的application.yml定义了与数据库的连接

### 6.示例
#### 6.1 搜索页
<http://localhost:8080/KG/search?keywords=成年人>  
```json
{"code":1,"message":null,"data":"{\"data\":[{\"name\":\"http://www.openkg.cn/COVID-19/prevention#成年人\",\"id\":\"87114\"},{\"name\":\"{\\\"_language\\\":\\\"zh\\\",\\\"_value\\\":\\\"成年人\\\"}\",\"id\":\"95393\"}]}"}
```
#### 6.2 搜索列表页
<http://localhost:8080/KG/getGraphData?id=86604>  
```json
{"code":1,"message":null,"data":"{\"data\":[{\"name\":\"_:genid1\",\"id\":\"86604\"},{\"name\":\"_type\",\"id\":\"86502\"},{\"name\":\"http://www.w3.org/2002/07/owl#Class\",\"id\":\"86706\"},{\"name\":\"http://www.w3.org/2002/07/owl#oneOf\",\"id\":\"86808\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention#医护人员\",\"id\":\"86910\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention#孕产妇\",\"id\":\"87012\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention#成年人\",\"id\":\"87114\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R10102\",\"id\":\"87216\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R10103\",\"id\":\"87318\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R10105\",\"id\":\"87420\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R10106\",\"id\":\"87522\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R1020201\",\"id\":\"87624\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R102030101\",\"id\":\"87726\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R102030201\",\"id\":\"87828\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R102030301\",\"id\":\"87930\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/resource/R102030401\",\"id\":\"88032\"},{\"name\":\"http://www.openkg.cn/COVID-19/prevention/class/C1\",\"id\":\"91653\"},{\"name\":\"http://www.w3.org/2002/07/owl#equivalentClass\",\"id\":\"95189\"}],\"link\":[{\"name\":\"86502\",\"source\":\"86604\",\"target\":\"86706\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"86910\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87012\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87114\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87216\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87318\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87420\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87522\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87624\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87726\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87828\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"87930\"},{\"name\":\"86808\",\"source\":\"86604\",\"target\":\"88032\"},{\"name\":\"95189\",\"source\":\"91653\",\"target\":\"86604\"}]}"}
```
#to be continue →

