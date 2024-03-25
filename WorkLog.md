# OIDC_BingYan

>2024冰岩作坊后端实习题OIDC。

**注：每天的记录都是按照实现顺序一步步来的，也就是说有可能出现对于一个任务的框架建立和具体实现在两个地方的情况。**

## 代码结构框架

### 项目结构框架

参考并细化了MVC（Model-View-Controller）模型。我采用了以下层次结构以确保每个组件的职责都被清晰定义且互相独立：

1. **POJO（Plain Old Java Object）**
   - 定义了与数据库表对应的实体类，用于映射表中的记录。这些类仅包含属性和其getter和setter方法，还有对应的有参无参构造函数，以及hashcode和equals函数。
   - 由于不让用第三方库，所以我本想用lombok来自动注解的愿望破灭了，遂手敲了这些方法，对标@Data,@NoArgsConstructor,@AllArgsConstructor这些注解。
   - 无任何业务逻辑，确保了数据的简单传递。
2. **Model**
   - 在我的架构中，Model为数据传输对象（DTO），用于封装客户端请求的数据。
   - 这使得传统的MVC更加细化，将业务逻辑与数据访问解耦，确保了代码的清晰性和可维护性。
   - 同时，DTO可以用于在服务之间传递数据，确保了数据的一致性和安全性。
3. **Mapper**
   - 这是数据访问层，Mapper接口定义了与数据库交互的方法，如查询、插入、更新和删除。这些方法由MyBatis-Plus动态实现，确保了数据库操作的一致性和高效性。
4. **Service**
   - 业务逻辑层封装了具体的业务操作，依赖于Mapper层进行数据访问。Service层的设计使得业务逻辑与数据访问代码解耦，便于维护和测试。
   - 在这一层中，我把每个业务逻辑都对应一个接口及其实现，这让我的代码十分灵活，可以针对同一个接口做出不同的实现，根据不同的情况进行选择，进而实现更复杂的业务逻辑。
5. **Controller**
   - 作为应用程序的表现层，Controller负责处理外部请求，调用Service层的业务方法，并返回相应的视图或数据。这确保了前端请求的处理逻辑与后端业务逻辑的分离，增强了应用程序的灵活性和可扩展性。

这样的架构遵循了MVC的基本原则，还通过更细致的分层增加了代码的可读性和可维护性。每层的定义清晰，职责单一。

#### 参考了MVC的方面

1. **Controller层**
   - Controller层在扮演着和MVC中相同的角色，即处理用户请求、调用后端服务并返回响应。这一层作为用户交互和后端服务之间的桥梁。
2. **Model的使用**
   - 本项目的Model层用于封装复杂请求体的数据，这在某种程度上对应于MVC中的Model概念，即作为数据和业务规则的承载体。但是在我的项目中，Model侧重于数据传输对象（DTO），这仍然体现了Model在数据处理中的核心作用。

#### 细化了MVC的方面

1. **POJO和Model的区分**
   - 我的架构中明确区分了POJO（用于数据库映射）和Model（用于封装请求数据）。
   - 在传统MVC中，Model通常既包含业务数据也包含业务逻辑，而在我的项目中，POJO专注于与数据库表的映射，而Model则专门用于处理请求数据，这使得数据层更加清晰。
2. **引入Mapper层**
   - Mapper层作为数据访问层处理数据库交互，这一层在标准的MVC模型中通常不会单独区分出来。
   - 通过引入Mapper层，项目能够更加灵活地处理数据库操作，同时降低了Service层与数据库之间的耦合度。
3. **Service层的设立**
   - 在传统的MVC模型中，业务逻辑可能直接放在Controller或Model中。
   - 我的架构中，Service层专门负责业务逻辑的处理，这不仅使得业务逻辑更加模块化，也进一步减轻了Controller层的负担，让Controller层更加专注于处理请求和返回响应。

通过这些参考和细化，我保留了MVC模型的核心优点，还通过更加精细化的层次划分，提高了项目的可维护性和可扩展性。

### 阶段一框架

这一阶段的任务是写所有有关用户的代码，包括用户注册，用户登录，用户信息修改，用户头像修改，用户头像上传。

#### 阶段一详细功能说明

1. 用jwt实现Spring Security的认证和授权
2. 实现用户登录
   1. 用用户名和密码登录。
   2. 登录成功返回token。
3. 实现用户注册
   1. 需要提供用户名，密码，邮箱，昵称，头像，简介，头像和简介为非必需，如果用户不提供就用默认值替代。
   2. 注册成功后，自动发送一封邮件到用户邮箱，要求用户点击邮件中的链接来激活账号。激活成功才算注册完成，可以正常登录，否则等同于完全没有注册，这符合事务的基本原则，要么全做，要么不做。
   3. 由于我们允许一个邮箱注册多个账号，所以是每个邮箱**五分钟**只能获取一次邮件。
4. 实现用户信息获取
   1. 获取用户信息，需要提供token。
   2. 获取信息不包括密码。
5. 实现用户信息修改
   1. 支持修改的信息包括：昵称，头像，简介，密码。
   2. 登录的用户才能修改自己的信息。

#### 阶段一代码框架

代码框架是基于整个项目的代码框架设计的。

1. POJO
   1. User类：用于封装用户信息，包括ID、用户名、密码、邮箱、昵称、头像、简介、是否激活、激活令牌、最后一次邮件发送时间，用于与数据库表进行映射。
2. Model
   1. UserRegistrationModel类：用于封装用户注册请求的数据，包括用户名、密码、重复密码、邮箱、昵称、头像、简介。
   2. ImageModel类：用于封装所有图片请求体的数据。
3. Mapper
   1. UserMapper类：定义了与数据库表对应的Mapper接口，用于实现与数据库表的交互操作。
4. Service
   1. interfaces
      1. tools
         1. ImageUploadService接口：用于处理图片上传的逻辑。这个接口的不同实现可以完成不同的图片上传逻辑。
      2. user
         1. account
            1. InfoService接口：用于处理和用户信息有关的各种逻辑，包括获取和更新。
            2. LoginService接口：用于处理用户登录的逻辑。
            3. RegisterService接口：用于处理用户注册的逻辑，其中还包括注册时的邮件发送业务。
   2. impl
      1. LoadByUser
         1. UserDetailsServiceImpl类：实现了UserDetailsService接口，其中loadUserByUsername函数用于根据用户名和密码进行登录验证。
      2. tools
         1. ImageUploadServiceImpl类：实现了ImageUploadService接口，其中uploadImage函数用于上传图片。
         2. JwtAuthenticationTokenFilter类：实现了Filter接口，用于对每个请求进行过滤，并验证token的有效性。
         3. JwtTool类：用于生成和解析JWT。
         4. UserDetailImpl类：实现了UserDetails接口，用于封装用户信息，并实现与数据库表的映射。
      3. User
         1. account
            1. InfoServiceImpl：实现了InfoService接口。其中包括很多信息的更新和获取。
            2. LoginServiceImpl类：实现了LoginService接口。其中包括了登录的逻辑。
            3. RegisterServiceImpl类：实现了RegisterService接口。其中包括了注册的逻辑和邮件发送业务。
5. Controller
   1. user
      1. account
         1. InfoController类：用于处理与用户信息有关的各种请求，包括获取和更新。
         2. LoginController类：用于处理用户登录的请求。
         3. RegisterController类：用于处理用户注册的请求。
6. config
   1. SecurityConfig类：用于配置Spring Security的相关设置。

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

### 引入Spring Security

经过实现UserDetailsService和UserDetails接口，实现通过用户密码认证和授权。

#### 认证流程

实际上这里是的依据是Spring提供的一套用户认证流程：

UserDetails接口定义了Spring Security用于认证和授权的用户信息的基本属性，包括用户名、密码、账户是否过期、账户是否锁定等。实现这个接口意味着提供了一种方式来封装用户信息，使其能够被Spring Security使用。

UserDetailsService接口定义了一个方法loadUserByUsername，这个方法负责根据用户名查找用户，并返回一个UserDetails对象。在用户登录时，Spring Security会调用这个方法来获取用户的详细信息。

当用户尝试登录时，Spring Security的认证机制会自动调用loadUserByUsername方法来查找用户。找到用户后，框架比较用户输入的密码和数据库中存储的密码。如果密码匹配，用户被认为是合法的，认证成功，将这个用户存在上下文中，用户随后就可以访问允许的资源啦。

这一过程是通过Spring Security的AuthenticationManager来完成的，其中DaoAuthenticationProvider使用UserDetailsService来获取用户信息，并使用配置的PasswordEncoder来比较密码。

我的PasswordEncoder采用BCryptPasswordEncoder。

果咩啊。。。。。写完这部分，进行测试的时候发现不小心死锁了。。。。但是我又不想直接手动往数据库插入数据。。。有点太不优雅了。。。只能先写注册了嗯。

#### 实现从sessionId到JWT

覆盖WebSecurityConfigurerAdapter类方法来自定义spring security配置。

禁用CSRF保护，我们有token验证

设置session为无状态，jwt不需要这个

允许跨域预检请求

开放几个公开接口来注册登录

参考资料：<https://spring.io/guides/gs/securing-web>

值得一提的是，我在查资料的时候，发现从 Spring Security 5.7.0 版本开始，WebSecurityConfigurerAdapter 被标记为已弃用。推荐的做法是转向基于组件的安全配置。

这在这篇文章中详细叙述了：<https://www.javaguides.net/2022/08/spring-security-without-webSecurityconfigureradapter.html>

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

参考资料：<https://blog.csdn.net/weixin_46195957/article/details/115326648>

### 实现登录

采用AuthenticationManager处理认证。

用UsernamePasswordAuthenticationToken封装用户名和密码。

用AuthenticationManager.authenticate(authenticationToken)进行认证，认证失败自己报错。这部分逻辑我在上面的Security部分也说过了，就是SpringBoot的一个认证流程。

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

这部分需要处理的细节很多，包括但不限于是否输入，不输入是否有默认值，长度限制等等。理论上这部分还应该有敏感内容的判定，但是这部分要调用付费的外部api，所以先不考虑。

测试：终于解决了死锁问题，postman启动！测试过程很顺利，代码可以根据不同的错误返回不同的错误信息。例如没有用户名就说用户名不能为空（只写空格也不行）。但是bug也随之出现。

### 出现bug，开修

什么！为什么我的MySql不能正常的让我的id自增！？！？

明明已经设置了id自增，唯一，且为主键。

代码逻辑也没问题。。。

尝试直接在cmd里插入一个值，居然是正常的！！

可能跟spring boot的版本有关，之前写的项目就没这样的错误！

没事我直接手动自增就好了。。。。笨但是管用的办法。。。。

### 24.3.23总结

今天新建了文件夹，配置了各项依赖，调整了数据库，大概写了一下用户的登录，注册模块，修复了bug。。。嗯，明天开始正式的OIDC。。。。

## 24.3.24任务梳理

>说是24号，其实是23号晚上，今晚要熬夜了嗯。。。

### 手敲USER

把之前直接注入的User类改写为手动new的User类。

### 头像上传（调试）

使用图床API上传图片，仍在调试中

测试：本来想直接写成一个Controller的，但是发现上传头像的逻辑和注册的逻辑有重叠，所以就直接写成了一个Service的。浪费了很多时间，请大家下次动手之前先想好逻辑，引以为鉴，望周知（bushi）。

### 邮箱发送（调试）

利用两个属性来判断一个用户是否激活来实现发送邮件注册，仍在调试中。

### 邮箱发送（实现）

嗯，完成了邮箱验证功能，还需要完善限制频率的功能。

测试：新注册了一个网易163邮箱，通过Postman发送注册请求，成功向我的个人邮箱发送激活邮件。在不同情况下，用不同的数据测试，发送了30多封邮件，都成功了。

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

图床API：<https://www.imgtp.com/index/api.html>

### 实现用户头像更改

实现根据token更改用户头像的功能。

### 24.3.24总结

今天彻底完成阶段一，明天正式开始OIDC，希望不会太难。

而且今天在导师的指导下，学会了规范的分支命名和commit提交。。。

对不起我之前这部分做的太烂。。

今天做的具体的事情都在上面，我这里就不过多赘述了。

**时间：今天花了6个小时。**
