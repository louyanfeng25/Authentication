# 基于spring-security的微服务鉴权中心

---

## 一.简介
    此服务应用于微服务架构下，web系统对于业务接口调用需要进行token校验与权限控制
    

---

## 二.使用技术
- 注册中心：nacos【2.1.3.RELEASE】
- 配置中心：nacos【2.1.3.RELEASE】
- RPC框架：OpenFeign【Hoxton.SR8】
- 鉴权框架：security【Hoxton.SR8】
- 链路跟踪：zipkin【Hoxton.SR8】
- 缓存：guava【23.0】
- 数据库连接池：durid【1.1.22】
- ORM框架：mybytis-plus【3.3.2】
- 接口文档:swagger【2.7.0】
- 数据库：mysql【8.0.20】

---

# 三.项目启动
## 3.1.安装nacos
https://nacos.io/zh-cn/docs/quick-start.html

## 3.2.mysql
1.新建mysql数据库名为auth【可为其他名称】

2.新建用户并赋权【可为默认】

以上数据库可以任意名称新建一个数据库，修改application.yml配置即可

## 3.3.项目获取
git clone https://github.com/louyanfeng25/auth.git

## 3.4.配置修改
1.修改nacos注册中心与配置中心地址

2.修改mysql配置

> flyway报错无法启动解决方案

> 1.数据库表结构与初始化数据采用flyway进行启动，如果启动报错，则flyway无法将flyway_schema_history相关表结构打入，可手动新建该表结构。

> 2.注释flyway的maven依赖手动将classpath:/db/migration的初始化sql脚本打入数据库

## 3.5.启动
AuthApplication启动类进行启动

---

# 四.项目说明
## 4.1.项目结构
- api ：对外开放的RPC接口
- commmon ：公共组件包
- rpc ：对外开放的RPC包
- sdk ：鉴权SDK包
- service ：服务包
- start ：启动包
- web ：用户交互层

## 4.2.鉴权【能否访问系统】
能否访问系统的鉴权支持两种方式token和accessKey

### 4.2.1.token
生成使用对称加密，前端与后端分别定义相同的逻辑对用户名，密码进行加密处理
> 默认用户名：admin

> 默认密码：t9KQ7S2gBxzfGxsbOEUnXg==

> 请求头参数Times：2020-12-15 19:01:35

> 请求接口：/api/auth/web/login/auth/tokens

> 请求方式：POST

加解密逻辑具体可查看
com.baiyan.auth.service.utils.AesUtil

com.baiyan.auth.service.utils.AuthAesUtil

com.baiyan.auth.service.utils.PasswordUtil

过期时间配置为==auth.login.config.tokenTimeout==,单位为秒

### 4.2.2.accessKey
1.新增逻辑：拥有权限code的用户可进行任意用户的accessKey新建，普通用户可以新建自己的accessKey。

2.鉴权逻辑accessKey与用户token的权限一致，无过期时间。

### 4.2.3.拦截
默认配置下/api开头的所有接口请求均被拦截校验token或者accessKey
可通过配置修改：
auth.resourceAp:进行拦截的请求
auth.exclude:进行过滤的请求

### 4.2.4.鉴权方式
1.header中携带token参数

```
"Authorization":"Bearer fd6fe635e65146968820276eaf873e46
```
2.请求链接中携带token参数

```
/api/auth/rpc/test?token=fd6fe635e65146968820276eaf873e46
```
3.header中携带access参数

```
"access":"fd6fe635e65146968820276eaf873e46
```
4.请求链接中携带access参数

```
/api/auth/rpc/test?access=fd6fe635e65146968820276eaf873e46
```

## 4.3.鉴权【是否有权限访问】
在controller层接口上定义
> @PreAuthorize("hasAuthority('user_access')")

即为表示用户必须拥有user_access才能进行当前接口访问


---

# 五.业务应用服务接入
## 5.1.pom
```
<dependency>
    <groupId>com.baiyan</groupId>
    <artifactId>auth-sdk</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.baiyan</groupId>
    <artifactId>auth-rpc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
## 5.2.配置
### 5.2.1.引入openFeign与nacos
1.启动类上添加@EnableDiscoveryClient

2.如果只引用了鉴权中心的RPC,则@EnableFeignClients则不需要在启动类上添加，jar提供了自动配置类

### 5.2.2.鉴权拦截配置

```
#需要拦截的资源，多个前缀开头用英文逗号间隔  

resourceApi:/api/**

#不要拦截的资源，多个前缀开头用英文逗号间隔  

exclude:/api/**/api-docs
```

### 5.2.3.rpc发起接口请求时携带token/accessKey参数
在rpc包中重写了RequestTemplate的bean，默认情况可不添加此配置，自动加载，如果不想在rpc接口请求时传递token则增加如下配置关闭

```
auth: 
  feign:   
    enable:false
```

## 5.3.当前请求用户信息获取
方式1：使用ThreadLocalUtil.getUser()，在代码中直接操作

```
UserDTO user = ThreadLocalUtil.getUser();
```
方式2：在controller方法入口添加参数：UserDTO user

```
@GetMapping("/api/test")
publicvoidtest(HttpServletRequest request, UserDTO userDTO){

}
```

## 5.4.获取指定根据用户ID，用户token,用户accessKey获取用户信息
此方法在auth服务开启的权限拦截，因此才RPC接口请求必须携带token/accessKey参数

```
@Autowired
private UserApi userApi;

@Autowired
private AccessApi accessApi;
```

---

# 六.错误码说明
20001：用户已在线

20002：用户修改密码或者用户首次登录需要修改密码

401：无效token

403：接口访问权限不足

---


# 七.权限表结构说明

header 1 | header 2
---|---
t_role | ⻆色表
t_user_role | 用户⻆色关联表
t_menu | 权限表
t_role_menu | ⻆色权限关联表
t_user | 用户表
t_user_auth_log | 用户登录日志表
t_access | 三方授权信息表

# 八.联系我
项目如有问题欢迎加我

钉钉：louyanfeng25

微信：baiyan_lou
