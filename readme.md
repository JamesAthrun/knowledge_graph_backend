# Readme
## 1. 请求格式
### 1.1 接受请求
GET/POST   
原则上，对简单请求，「删查」使用GET，「增改」使用POST  
POST请求的请求头需带上 'content-type': 'application/json'，RequestBody需使用json字符串格式  
### 1.2 返回请求
response.content的数据格式为bytes，json解析后得到字典  
{'code':xxx,'message':xxx,'data':xxx}  
其中，code为状态码，message为描述，data为返回对象（json字符串格式）  
#### 操作成功
code为1，message为null  
对不需要返回对象的操作（如简单的「增删改」），data为null  
对需要返回对象的操作（如简单的「查」），对data再次进行json解析后可得到返回对象，如{'id': '1', 'name': 'Jack', 'score':'100'}  
#### 操作失败
code大于1，message为错误信息，data为null  
### 1.3* 加密
后续视需求对response.content进行加密

### 2. Service接口
#### 2.1 搜索页
端口仅供参考，详见application.yml中的port字段  
<http://localhost:8082/KG/search?keywords=佩戴>  
```json
{"code":1,"message":null,"data":"{\"data\":[{\"des\":\"\",\"name\":\"#佩戴口罩\",\"id\":\"87704\"},{\"des\":\"\",\"name\":\"#尽量选择步行或自驾车外出或去医院。在路上和医院时，人与人之间尽可能保持距离，并全程佩戴口罩和手套。\",\"id\":\"88117\"},{\"des\":\"\",\"name\":\"#老人出现发热、咳嗽等可疑症状时，应自我隔离，避免与其他人员近距离接触。由医护人员对其健康状况进行评估，视病情状况送至医疗机构就诊，送医途中应佩戴口罩，尽量避免乘坐公共交通工具。\",\"id\":\"88933\"},{\"des\":\"\",\"name\":\"#不建议有慢性肺病、心脏病的老人佩戴N95/KN95口罩。\",\"id\":\"89749\"},{\"des\":\"\",\"name\":\"#有慢性肺病、心脏病的老人避免佩戴N95/KN95口罩。\",\"id\":\"89834\"},{\"des\":\"\",\"name\":\"#不要带孩子去人多的地方，外出一定要给孩子佩戴口罩，回家后要及时更换衣物并洗手。\",\"id\":\"91477\"},{\"des\":\"\",\"name\":\"#工作时建议佩戴口罩和手套。\",\"id\":\"92071\"},{\"des\":\"\",\"name\":\"#佩戴口罩和手套\",\"id\":\"92173\"},{\"des\":\"\",\"name\":\"#在工作区和休息区，减少与工友交流，采取手势或者其他形体语言示意对方；若必须交流时，则双方佩戴口罩并保持最少 1米以上的距离。\",\"id\":\"92775\"},{\"des\":\"\",\"name\":\"#上门服务时要注意佩戴口罩和手套等。\",\"id\":\"94390\"},{\"des\":\"\",\"name\":\"#佩戴口罩、手套\",\"id\":\"94492\"},{\"des\":\"\",\"name\":\"#下水道维修工人要佩戴口罩、手套、护目镜\",\"id\":\"95801\"},{\"des\":\"\",\"name\":\"#在垃圾清理过程中，对垃圾收运工具进行消毒，佩戴口罩、手套，尽量不用手触碰眼、口、鼻等处。\",\"id\":\"97515\"},{\"des\":\"\",\"name\":\"#前往医院的路上，全程佩戴口罩。如果条件容许，路上打开车窗。\",\"id\":\"97110\"},{\"des\":\"\",\"name\":\"#就医路上佩戴口罩\",\"id\":\"97212\"},{\"des\":\"\",\"name\":\"#康复训练过程中佩戴口罩，注意安全。\",\"id\":\"97720\"},{\"des\":\"\",\"name\":\"#出门时佩戴口罩，注意保暖和手卫生\",\"id\":\"97824\"},{\"des\":\"\",\"name\":\"#就医期间佩戴口罩，应尽量缩短在医院逗留的时间。\",\"id\":\"97927\"},{\"des\":\"\",\"name\":\"#去往工作场所路上应佩戴口罩，各人之间保持一定距离。\",\"id\":\"98334\"},{\"des\":\"\",\"name\":\"#工作路上佩戴口罩\",\"id\":\"98419\"},{\"des\":\"\",\"name\":\"#工作场所多人一起工作时，每人应佩戴口罩。\",\"id\":\"98521\"},{\"des\":\"\",\"name\":\"#多人工作时佩戴口罩\",\"id\":\"98723\"},{\"des\":\"\",\"name\":\"#日常出行时佩戴口罩，外出期间不乱扔垃圾，不随地吐痰，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡，尽量与他人保持一定距离，不到人群密集场所活动。外出返回后手卫生。\",\"id\":\"99133\"},{\"des\":\"\",\"name\":\"#日常出行佩戴口罩，注意个人和公共卫生\",\"id\":\"99235\"},{\"des\":\"\",\"name\":\"#在接待访客、审讯嫌疑人时，全程佩戴口罩，并要求访客、嫌疑人佩戴口罩。\",\"id\":\"100137\"},{\"des\":\"\",\"name\":\"#接待、审讯时佩戴口罩\",\"id\":\"100238\"},{\"des\":\"\",\"name\":\"#参加案情会议时，全程佩戴口罩\",\"id\":\"100850\"},{\"des\":\"\",\"name\":\"#在入户调查、设卡检查等外出执勤时应佩戴口罩。\",\"id\":\"101777\"},{\"des\":\"\",\"name\":\"#外出执勤时应佩戴口罩\",\"id\":\"101778\"},{\"des\":\"\",\"name\":\"#在教室、图书馆、食堂、宿舍等人员密集场所应佩戴口罩，尽量少参加群体性聚集性活动。\",\"id\":\"103077\"},{\"des\":\"\",\"name\":\"#佩戴口罩，减少聚集性活动\",\"id\":\"103179\"},{\"des\":\"\",\"name\":\"#外出前往公共场所、乘坐公共交通工具时，应佩戴口罩。\",\"id\":\"105797\"},{\"des\":\"\",\"name\":\"#在社区门口、楼梯口等处张贴告示，提醒居民加强通风、勤洗手、外出注意佩戴口罩等，也可通过短信、社区公众号等进行宣传。\",\"id\":\"107713\"},{\"des\":\"\",\"name\":\"#诊疗环境应通风良好，并常规进行物体表面及地面的消毒，每天2次。发现疑似或确诊患者，立即为患者佩戴口罩，及时转送发热门诊或隔离病区，及时进行终末消毒并做好记录；通知该患者就诊过的有关科室如放射科、化验室等，做好相应的消毒工作。\",\"id\":\"108823\"},{\"des\":\"\",\"name\":\"#从业人员工作过程中必须佩戴口罩，与他人交流时保持安全距离；做好每日健康监测，出现可疑症状时立即前往定点医疗机构就医。\",\"id\":\"110251\"},{\"des\":\"\",\"name\":\"#顾客乘坐厢式电梯时应佩戴口罩，尽量避免直接接触梯内设施。\",\"id\":\"110742\"},{\"des\":\"\",\"name\":\"#可通过视频滚动播放或在超市入口处、楼梯口、电梯间等显著位置处张贴告示，提醒工作人员和顾客注意佩戴口罩、回家后注意洗手等。\",\"id\":\"111458\"},{\"des\":\"\",\"name\":\"#收银员、理货员、保安等要佩戴口罩、经常洗手。\",\"id\":\"112971\"},{\"des\":\"\",\"name\":\"#在显著位置张贴告示，提醒客户办理业务时要佩戴口罩。\",\"id\":\"113481\"},{\"des\":\"\",\"name\":\"#从业人员工作过程中佩戴口罩，有条件的可以佩戴护目镜，工作服定期洗涤、消毒；与他人交流时保持安全距离。\",\"id\":\"115793\"},{\"des\":\"\",\"name\":\"#经营场所应在场所门口设置顾客体温测量点，体温正常方可进入。顾客进入商场应佩戴口罩，人与人之间保持安全距离。\",\"id\":\"115997\"},{\"des\":\"\",\"name\":\"#工作人员应体温正常，无发热咳嗽等症状，并佩戴口罩。\",\"id\":\"117813\"},{\"des\":\"\",\"name\":\"#在办公室工作时，所有人员需要佩戴口罩。\",\"id\":\"117510\"},{\"des\":\"\",\"name\":\"#办公期间佩戴口罩\",\"id\":\"117712\"},{\"des\":\"\",\"name\":\"#办公室内的工作人员谈话交流要佩戴口罩并保持至少 1 米的安全距离。\",\"id\":\"117714\"},{\"des\":\"\",\"name\":\"#公用电话接听时要佩戴口罩，接听电话前后需要对听筒擦拭消毒。\",\"id\":\"118122\"},{\"des\":\"\",\"name\":\"#传递文件或物品的前后都要洗手，传递时都要佩戴口罩。对于负责收发文件或其他用品频繁的工作人员，应佩戴口罩和手套。\",\"id\":\"118327\"},{\"des\":\"\",\"name\":\"#应为职工配备口罩，指导职工正确佩戴口罩、做好口罩的定期更换和使用后口罩的正确处理。\",\"id\":\"118734\"},{\"des\":\"\",\"name\":\"#为工作人员配备口罩，未佩戴口罩的工作人员禁止乘坐班车或进入单位。\",\"id\":\"120247\"},{\"des\":\"\",\"name\":\"#建立探访登记制度，如探访人员有新冠肺炎可疑症状，应拒绝其探访。所有外来探访人员应佩戴口罩。\",\"id\":\"122178\"},{\"des\":\"\",\"name\":\"#乘客、乘务员和驾驶员佩戴口罩，乘客保持安静、减少交流，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡等。\",\"id\":\"127707\"},{\"des\":\"\",\"name\":\"#佩戴口罩注意遮挡\",\"id\":\"127809\"},{\"des\":\"\",\"name\":\"#乘客、乘务员佩戴口罩，乘客保持安静、减少交流，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡等。\",\"id\":\"128322\"},{\"des\":\"\",\"name\":\"#乘客、船舶工作人员佩戴口罩，乘客保持安静、减少交流，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡等。\",\"id\":\"129733\"},{\"des\":\"\",\"name\":\"#客舱乘务员佩戴口罩，可携带含醇类消毒湿巾。乘客佩戴口罩，保持安静、减少交流，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡等。\",\"id\":\"131450\"},{\"des\":\"\",\"name\":\"#乘客、与乘客接触的城市轨道交通运营服务人员佩戴口罩，乘客保持安静、减少交流，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡等。\",\"id\":\"133777\"},{\"des\":\"\",\"name\":\"#司机佩戴口罩，提醒车上的乘客佩戴口罩并减少交流，打喷嚏时用纸巾遮住口鼻，或采用肘臂遮挡等。\",\"id\":\"134578\"}]}"}
```
#### 2.2 搜索列表页
<http://localhost:8082/KG/getGraphData?id=19581760>  
```json
{"code":1,"message":null,"data":"{\"entityData\":[{\"des\":\"中文名 城市公共汽电车预防措施1\\n\",\"name\":\"R3070601\",\"id\":\"19688908\"},{\"des\":\"中文名 出租汽车预防措施5\\n\",\"name\":\"R3070805\",\"id\":\"19426562\"},{\"des\":\"中文名 民航预防措施5\\n\",\"name\":\"R3070505\",\"id\":\"19814568\"},{\"des\":\"中文名 铁路客运预防措施3\\n\",\"name\":\"R3070303\",\"id\":\"19649652\"},{\"des\":\"中文名 出租汽车预防措施4\\n\",\"name\":\"R3070804\",\"id\":\"19302860\"},{\"des\":\"中文名 出租汽车预防措施3\\n\",\"name\":\"R3070803\",\"id\":\"19606068\"},{\"des\":\"中文名 城市轨道交通预防措施1\\n\",\"name\":\"R3070701\",\"id\":\"19677966\"},{\"des\":\"中文名 水路客运预防措施7\\n\",\"name\":\"R3070407\",\"id\":\"19903202\"},{\"des\":\"中文名 城市公共汽电车预防措施5\\n\",\"name\":\"R3070605\",\"id\":\"19303860\"},{\"des\":\"\",\"name\":\"R10203\",\"id\":\"19213060\"},{\"des\":\"\",\"name\":\"#根据客流情况，合理组织运力，降低车厢拥挤度。\",\"id\":\"19581760\"},{\"des\":\"\",\"name\":\"#降低车辆拥挤度\",\"id\":\"19254244\"},{\"des\":\"中文名 城市轨道交通预防措施3\\n\",\"name\":\"R3070703\",\"id\":\"19714154\"},{\"des\":\"中文名 铁路客运预防措施9\\n\",\"name\":\"R3070309\",\"id\":\"19355936\"},{\"des\":\"中文名 铁路客运预防措施2\\n\",\"name\":\"R3070302\",\"id\":\"19779464\"}],\"link\":[{\"name\":\"19689032\",\"id\":\"19552612\",\"source\":\"19649652\",\"target\":\"19213060\"},{\"name\":\"19674736\",\"id\":\"19803234\",\"source\":\"19688908\",\"target\":\"19254244\"},{\"name\":\"19689032\",\"id\":\"19698350\",\"source\":\"19302860\",\"target\":\"19213060\"},{\"name\":\"19440196\",\"id\":\"19491196\",\"source\":\"19688908\",\"target\":\"19581760\"},{\"name\":\"19440196\",\"id\":\"19347512\",\"source\":\"19677966\",\"target\":\"19581760\"},{\"name\":\"19689032\",\"id\":\"19417842\",\"source\":\"19303860\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19640368\",\"source\":\"19903202\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19265806\",\"source\":\"19714154\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19697192\",\"source\":\"19688908\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19852574\",\"source\":\"19677966\",\"target\":\"19213060\"},{\"name\":\"19674736\",\"id\":\"19515866\",\"source\":\"19677966\",\"target\":\"19254244\"},{\"name\":\"19689032\",\"id\":\"19339396\",\"source\":\"19779464\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19454932\",\"source\":\"19426562\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19903638\",\"source\":\"19814568\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19834236\",\"source\":\"19606068\",\"target\":\"19213060\"},{\"name\":\"19689032\",\"id\":\"19690570\",\"source\":\"19355936\",\"target\":\"19213060\"}],\"propertyData\":[{\"des\":\"中文名 措施描述\\n定义域 C3\\n值域 String\\n\",\"name\":\"P42\",\"id\":\"19440196\"},{\"des\":\"中文名 适用人群\\n英文名 Applicable people\\n定义域 C3\\n值域 C1\\n\",\"name\":\"P019\",\"id\":\"19689032\"},{\"des\":\"中文名 措施主题\\n定义域 C3\\n值域 String\\n\",\"name\":\"P41\",\"id\":\"19674736\"}]}"}
```

## 3. 后端项目结构
controller 控制层 如JackController.java  
bl 业务逻辑层接口 如JackService.java  
blImpl 业务逻辑层实现 如JackServiceImpl.java  
data 数据层接口 如JackMapper.java  
dataImpl 数据层实现 如JackMapper.xml，在resources目录下  
po 略  
vo 略  
util 一些工具类  

### 3.1 异常处理
原则上异常在util.GlobalExceptionHandler统一处理  
### 3.2 返回值
业务逻辑层的返回值用util.ResultBean统一封装
### 3.3 数据库
使用mysql数据库，resources目录下的application.yml定义了与数据库的连接
### 3.4 本地数据源
src/main/resources/data.json
