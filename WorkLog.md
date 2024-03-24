# OIDC_BingYan

>2024冰岩作坊后端实习题OIDC。

**注：每天的记录都是按照实现顺序一步步来的，也就是说有可能出现对于一个任务的框架建立和具体实现在两个地方的情况。**

在具体的代码里我新学会的东西都进行了详细的注释。

## 24.3.23任务梳理

### 新建文件夹（）

创建一个springboot项目，名称为OIDC。

采用版本为JDK1.8，Java8，SpringBoot2.6.13。

### 引入依赖

引入各种依赖来配置环境，具体可见readme。

### 配置数据库与数据源

数据库采用MySql。

用户表内容包括：

|           名称            |                       评论                       |   数据类型    | 非null |
| :-----------------------: | :----------------------------------------------: | :-----------: | :----: |
|            id             | 用户id，手动自增，非空，主键，唯一，不允许修改。 |      int      |  true  |
|         user_name         |   用户名，非空，唯一，不允许修改，最多100字。    | varchar(100)  |  true  |
|       user_password       |        密码，非空，允许修改，最多1000字。        | varchar(1000) |  true  |
|       user_nickname       |         昵称，非空，允许修改，最多20字。         |  varchar(20)  |  true  |
|        user_email         |       邮箱，非空，不允许修改，最多100字。        | varchar(100)  |  true  |
|        user_avatar        |   头像，非空（有默认），允许修改，最多100字。    | varchar(100)  |  true  |
|     user_introduction     |   介绍，非空（有默认），允许修改，最多1000字。   | varchar(1000) |  true  |
|      user_is_active       |              用于判断用户是否激活。              |  tinyint(1)   |  true  |
|  user_confirmation_token  |                用户唯一激活令牌。                | varchar(1000) | false  |
| user_last_email_sent_time |         用户邮箱最后一次申请邮件的时间。         |   datetime    | false  |



### 实现基本的代码框架

1. pojo
2. model
3. Mapper
4. Service
5. Controller

### 引入Spring Security

经过重写UserDetailsService和UserDetails，实现用户认证和授权。

配置Security实现加密密码。PasswordEncoder采用BCryptPasswordEncoder。

#### 实现JWT

重写WebSecurityConfigurerAdapter类来自定义spring security配置。

禁用CSRF保护，我们有token验证

设置session为无状态，jwt不需要这个

允许跨域预检请求

开放几个公开接口来注册登录

##### 实现JWT工具

1. **生成密钥** (`generalKey` 方法)：
   - 将一个预定义的字符串（JWT_KEY）通过 Base64 解码为字节数组。
   - 使用这个字节数组创建一个 `SecretKey` 对象，用于 JWT 的签名和验证。
2. **生成 JWT** (`createJWT` 方法)：
   - 调用 `getUUID` 方法生成一个唯一标识符（UUID）。
   - 调用 `getJwtBuilder` 方法创建一个 `JwtBuilder` 对象，设置 JWT 的主题（subject）、发行者（issuer）、签发时间（issuedAt）、过期时间（expiration）等。
   - 使用生成的 `SecretKey` 对 JWT 进行签名。
   - 调用 `compact` 方法将 JWT 对象压缩成一个字符串，这个字符串就是最终的 JWT。
3. **解析 JWT** (`parseJWT` 方法)：
   - 调用 `generalKey` 方法生成 `SecretKey` 对象。
   - 使用 `Jwts.parser().verifyWith(secretKey)` 来验证 JWT 的签名是否有效。
   - 解析 JWT 字符串，获取 JWT 的负载（payload），这里主要是 `Claims` 对象，其中包含了 JWT 的信息，如主题、签发者等。

##### 实现JWT拦截器

提取 JWT：从 HTTP 请求的 Authorization 头部提取 JWT。如果请求头不存在或不是以 "Bearer " 开头，则直接放行（即不进行 JWT 验证）。

JWT 解析：使用 JwtTool.parseJWT(token) 方法尝试解析 JWT。如果解析失败（比如，JWT 无效或过期），抛出异常。

用户验证：通过 JWT 中的主题查询用户信息。如果查询不到用户，表示用户不存在或未登录，抛出异常。

认证信息设置：使用查询到的用户信息创建 UsernamePasswordAuthenticationToken，并将其设置到 SecurityContextHolder 中。这样，后续的处理流程就可以认为该请求已经通过身份验证。

请求放行。

### 实现登录

采用AuthenticationManager处理认证。

用UsernamePasswordAuthenticationToken封装用户名和密码。

用AuthenticationManager.authenticate(authenticationToken)进行认证，认证失败自己报错。

认证成功后，生成token，并返回给前端。

### 实现注册

注册需要7个值：

1. 用户名
2. 密码
3. 确认密码
4. 邮箱
5. 昵称
6. 头像
7. 简介

头像为头像简介，目前用的是手动上传的图床图像，后续会编写一个上传头像的接口。

如果上传的7个值满足一定要求就注册然后插入就完了。

### 出现bug，开修！

什么！为什么我的MySql不能正常的让我的id自增！？！？

明明已经设置了id自增，唯一，且为主键。

代码逻辑也没问题。。。

尝试直接在cmd里插入一个值，居然是正常的！！

说明问题出现在mybatis-plus的配置上。

可能也跟spring boot的版本有关，之前写的项目就没这样的错误！

没事我直接手动自增就好了。。。。笨但是管用的办法。。。。

### 总结

今天新建了文件夹，配置了各项依赖，调整了数据库，大概写了一下用户的登录，注册模块，修复了bug。。。嗯，明天开始正式的OIDC。。。。

## 24.3.24任务梳理

>说是24号，其实是23号晚上，今晚要熬夜了嗯。。。

### 手敲USER

把之前直接注入的User类改写为手动new的User类。

### 头像上传（调试）

使用图床API上传图片，仍在调试中

### 邮箱发送（调试）

利用两个属性来判断一个用户是否激活来实现发送邮件注册，仍在调试中。

### 邮箱发送（实现）

嗯，完成了邮箱验证功能，还需要完善限制频率的功能。

### 完善限制频率功能，每五分钟一次（完善）

在发送邮件的接口上加上一个时间限制，限制发送邮件的频率。通过更改逻辑，完美实现了这个功能，限制注册用户只要还没激活，就可以每五分钟发送一次邮件，不会出现只能发送一次，再发送就显示用户名重复的情况。

**注：因为我们允许一个邮箱注册多个账号，所以是每个邮箱五分钟一次。**

（2024.3.24凌晨4点05分）今日份结束，睡醒继续。

**时间：今天花了8个小时。**

### 重写登录逻辑

现在必须激活才能登陆。

### 修改部分逻辑

减少字段注入，确保依赖项不会被后续更改，使得类更加不可变，从而增加了代码的稳定性和可靠性。

### 获取用户信息

实现根据token获取用户信息的功能。token写在请求头里，被过滤器拦截后，使用JWT工具解析token，获取用户信息，储存在SecurityContextHolder中，后续在Service中使用SecurityContextHolder获取用户信息。

### 实现用户信息修改

实现根据token修改用户信息的功能。可以修改的有昵称，头像（还没实现），简介，密码。

### 实现用户头像自主上传

图床选择ImgTP，借用api实现图片上传。终于！！！！！！！！！！！！！！！！！！！！！！！

### 实现用户头像更改

实现根据token更改用户头像的功能。

### 总结

今天彻底完成阶段一，明天正式开始OIDC，希望不会太难。

而且今天在导师的指导下，学会了规范的分支命名和commit提交。。。

对不起我之前这部分做的太烂。。

今天做的具体的事情都在上面，我这里就不过多赘述了。

**时间：今天花了6个小时**
