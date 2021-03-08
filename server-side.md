#后端的几点说明

##与前端的交互
###1.接受请求
GET/POST   
原则上，对简单请求，「删查」使用GET，「增改」使用POST  
POST请求的请求头需带上 'content-type': 'application/json'，RequestBody需使用json字符串格式  
###2.返回请求
response.content的数据格式为bytes，json解析后得到字典  
{'code':xxx,'message':xxx,'data':xxx}  
其中，code为状态码，message为描述，data为返回对象（json字符串格式）  
####操作成功
code为1，message为null  
对不需要返回对象的操作（如简单的「增删改」），data为null  
对需要返回对象的操作（如简单的「查」），对data再次进行json解析后可得到返回对象，如{'id': '1', 'name': 'Jack', 'score':'100'}  
####操作失败
code大于1，message为错误信息，data为null  
###*3.加密
后续视需求对response.content进行加密

##后端项目结构
controller 控制层 如JackController.java  
bl 业务逻辑层接口 如JackService.java  
blImpl 业务逻辑层实现 如JackServiceImpl.java  
data 数据层接口 如JackMapper.java  
dataImpl 数据层实现 如JackMapper.xml，在resources目录下  
po 略  
vo 略  
util 一些工具类  

###3.异常处理
原则上异常在util.GlobalExceptionHandler统一处理  
###4.返回值
业务逻辑层的返回值用util.ResultBean统一封装
###5.数据库
使用mysql数据库，resources目录下的application.yml定义了与数据库的连接

#to be continue →