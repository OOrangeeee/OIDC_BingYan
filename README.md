# OIDC_BingYan

>2024冰岩作坊后端实习题OIDC。

**注：每天的记录都是按照实现顺序一步步来的，也就是说有可能出现对于一个任务的框架建立和具体实现在两个地方的情况。**

## 24.3.23任务梳理

### 新建文件夹（）

创建一个springboot项目，名称为OIDC。

采用版本为JDK1.8，Java8，SpringBoot2.6.13。

### 引入依赖

1. **Spring Boot Starter JDBC**：
   - 用于简化 JDBC（Java Database Connectivity）操作，提供快速集成 JDBC 的依赖管理。
   - `<artifactId>spring-boot-starter-jdbc</artifactId>`：包括了 Spring JDBC 的自动配置支持。
2. **Project Lombok**：
   - 用于简化 Java 实体类的编写，提供了简洁的注解来自动生成常见的方法，如 getters, setters, toString 等。
   - `<artifactId>lombok</artifactId>`：此依赖在编译时自动处理注解，生成相应的方法。
3. **MySQL Connector/J**：
   - MySQL 的 JDBC 驱动，用于连接 MySQL 数据库。
   - `<artifactId>mysql-connector-j</artifactId>`：使 Java 应用能够通过 JDBC 访问 MySQL 数据库。
4. **MyBatis-Plus Boot Starter**：
   - 基于 MyBatis 的增强工具，提供更多便捷的操作，减少 CRUD 相关代码。
   - `<artifactId>mybatis-plus-boot-starter</artifactId>`：集成 MyBatis Plus 到 Spring Boot 项目中，简化配置。
5. **MyBatis-Plus Generator**：
   - 用于自动生成 MyBatis 的映射文件（Mapper）、接口以及对应实体类的代码生成器。
   - `<artifactId>mybatis-plus-generator</artifactId>`：帮助开发者快速生成数据访问层代码。
6. **Spring Boot Starter Security**：
   - 提供了 Spring Security 的自动配置功能，用于增强 Web 应用的安全性。
   - `<artifactId>spring-boot-starter-security</artifactId>`：集成 Spring Security，提供认证和授权等安全功能。
7. **JJWT (Java JWT)**：
   - 用于创建和解析 JSON Web Tokens (JWT) 的库，广泛用于现代 Web 应用的安全认证。
   - `<artifactId>jjwt-api</artifactId>`、`<artifactId>jjwt-impl</artifactId>` 和 `<artifactId>jjwt-jackson</artifactId>`：这三个依赖提供了 JWT 的创建、解析以及与 Jackson 库的集成。
8. **JetBrains Annotations**：
   - 提供了一套 Java 注解，用于代码分析和工具支持，有助于提高代码的可读性和减少错误。
   - `<artifactId>annotations</artifactId>`：用于标记代码，如可空性注解等，以增强 IDE 的代码检查能力。

### 配置数据库与数据源

数据库采用MySql。

用户表内容包括：

![](md_images/用户表示意图.png)

### 实现基本的用户注册登录获取信息代码框架

1. pojo
   1. User
2. Mapper
   1. UserMapper
3. Service
   1. User
      1. account
         1. Info
         2. Login
         3. Register
4. Controller
   1. User
      1. account
         1. Info
         2. Login
         3. Register

### 引入Spring Security

经过重写UserDetailsService和UserDetails，实现用户认证和授权。
