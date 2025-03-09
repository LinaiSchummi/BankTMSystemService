# 场景描述
设计一个用户资金交易管理系统,业务主要涉及以下几个接口：<br>
createTransaction 创建用户的交易记录，若用户不存在，则创建成功，否则创建失败<br>
deleteTransaction/{userId} 根据userId删除用户的交易记录，目前适配功能为全部删除<br>
modifyTransaction 修改用户的交易记录<br>
queryTransactions/{userId} 查询用户的交易记录，目前适配功能为查询用户所有记录<br>
涉及参数说明：<br>
userId用户id: 具有唯一性，一个用户可以存在多条交易记录,值为long型，且大于0<br>
userAmount用户交易金额：值为高精度数值型，有效值为[100,50000]<br>
userTransactionTime用户交易时间：值为long型，单位为毫秒<br>
isSaveMoney是否存款：值为yes 或者no<br>

# 模块划分
bean：用户交易信息<br>
client：对外接口暴漏<br>
exception：异常封装<br>
mapper：交易记录缓存操作<br>
service：service层，项目对外接口抽象实现<br>
utils: 工具类<br>

# 额外依赖
springboot web 相关功能，基础功能框架<br>
lombok ：简化代码，减少 get set 等格式代码<br>
fastjson：进行 json 相关处理<br>
spring-boot-starter-thymeleaf：使用thymeleaf 模版编写页面<br>
springdoc-openapi-starter-webmvc-ui：生成文档<br>

# 用户手册
## 项目构建与运行
### 环境准备
- 安装 **JDK 21** 和 **Maven** 和 **Docker**。
- 推荐使用 **IntelliJ IDEA** 进行开发。
### maven构建
找到项目根目录，打开终端，执行以下步骤：
```sh
mvn clean package
```
运行项目：
```sh
java -jar target/banktmsystemservice-1.0-SNAPSHOT.jar
```
应用将启动在：<br>
`http://localhost:8080`<br>
运行系统后API说明文档可通过以下链接获取：<br>
`http://localhost:8080/v3/api-docs` <br>
### docker构建
在终端调整到 `Dockerfile` 所在目录，执行以下命令：
```sh
docker build -t %application_name% .
docker run -p 8080:8080 %application_name%
```
## 页面功能
访问：http://localhost:8080/transactions

## 接口功能
### 创建接口
```sh
curl --location --request POST 'http://localhost:8080/transactions/createTransaction' \\

--header 'Content-Type: application/json' \\

--data-raw '{

"userId":"123",

"userAmount": 123.45,

"userTransactionTime": 123456789000,

"isSaveMoney": "YES"
}'
```
### 删除接口
```sh
http://localhost:8080/transactions/deleteTransaction/123
```
### 修改接口
```sh
curl --location --request PUT 'http://localhost:8080/transactions/modifyTransaction' \\

--header 'Content-Type: application/json' \\

--data-raw '{

"userId":"123",

"userAmount": 123.45,

"userTransactionTime": 123456789000,

"isSaveMoney": "YES"
}'
```
### 查询接口
```sh
http://localhost:8080/transactions/queryTransactions/123
```
# 测试设计
测试主要由以下几个方面考虑：功能场景（正常场景、异常场景）、接口安全性（防DOS攻击）、性能测试
## 功能测试
### 创建用户资金交易
#### 正常场景：
```sh
curl --location --request POST 'http://localhost:8080/transactions/createTransaction' \\

--header 'Content-Type: application/json' \\

--data-raw '{

"userId":"123",

"userAmount": 123.45,

"userTransactionTime": 123456789000,

"isSaveMoney": "YES"
}'
```
接口返回OK，200
#### 异常场景
参数异常：400, 服务异常返回500
### 删除接口
#### 正常场景
```sh
http://localhost:8080/transactions/deleteTransaction/123
```
#### 异常场景
参数异常返回400，找不到返回404，服务内部错误返回500
### 修改接口
#### 正常场景
```sh
curl --location --request PUT 'http://localhost:8080/transactions/modifyTransaction' \\

--header 'Content-Type: application/json' \\

--data-raw '{

"userId":"123",

"userAmount": 123.45,

"userTransactionTime": 123456789000,

"isSaveMoney": "YES"
}'
```
返回200
#### 异常场景
参数异常：400, 服务异常返回500
### 查询接口
#### 正常场景
```sh
http://localhost:8080/transactions/queryTransactions/123
```
返回查询列表
#### 异常场景
参数异常：400, 找不到返回404，服务内部异常返回500
## 安全测试
使用Jmeter不断请求接口，查看高并发下服务是否异常退出<br>
本业务场景简单，请求body体简单，并发量很大下，也并未出现网络安全安全问题。<br>
## 性能测试
本业务主要涉及接口请求，使用Jmeter进行压测<br>
添加接口；吞吐量1000tps并未出现错误<br>
删除接口：几十万条数据情况下，吞吐量1000tps并未出现错误<br>
更新接口；几十万条数据情况下，吞吐量1000tps并未出现错误<br>
查询接口：几十万条数据情况下，吞吐量300tps，返回body体会有点大，服务并未出现OOM<br>

