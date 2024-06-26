# OIDC_BingYan

>这是晋晨曦的冰岩实习项目！

## 项目介绍

一个关于某一个任务（嘘嘘嘘，不能多讲）的后端项目。

嗯，我只能说这么多。

## 项目总结

完成了所有阶段的所有任务（包括基础和提升），并加入了一些自己的想法，写了一些自己的新功能。

详情请前往WorkLog.md中查看。那里记载了这个项目的所有功能，代码框架和我的工作流程，心理路程。

## 项目部署

### 1. 环境准备

- **Java JDK 1.8**：安装并配置环境变量。
- **Maven**：安装Maven并配置环境变量，用于项目构建。
- **MySQL数据库**：根据项目需求，安装并启动MySQL数据库，创建必要的数据库和用户权限。

### 2. 项目构建

在项目根目录下执行以下命令来构建项目。这会根据`pom.xml`文件下载所有依赖项并编译项目：

```shell
mvn clean package
```

这条命令会在`target`目录下生成一个可执行的jar文件。

### 3. 配置文件调整

根据实际部署环境调整应用的配置文件，如`application.properties`。这包括：

- 数据库连接配置（URL、用户名、密码）。
- 任何外部服务的配置。
- 应用端口和上下文路径设置。

详细请见`application.properties`文件。将其内容替换为你自己的配置。

### 4. 运行项目

在确保所有配置正确无误后，使用以下命令启动应用：

```shell
java -jar target/OIDC-0.0.1-SNAPSHOT.jar
```

根据配置，应用将在指定端口上启动。如果配置了Spring Boot的内置Tomcat，它会自动启动并在指定端口侦听HTTP请求。

### 5. 验证部署

通过浏览器或使用任何HTTP客户端工具（如Postman）对应用的公开接口进行测试，来验证应用是否按预期运行。

### 6. 利用IDEA部署

在IDEA中，从VCS导入项目后，在IDEA中进行数据库配置，配置文件调整等操作，然后运行项目即可（PS，我没试过，大概率遇到莫名其妙的bug，先新建对应Spring boot版本的项目再进行部署或许是个不错的选择）。
