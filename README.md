# SpringBoot 项目初始模板

> 作者：[Satellite](https://github.com/Satellite2426)

基于 Java SpringBoot 的学生成绩管理系统，适合初学者快速入门 SpringBoot。
并且是一个 SpringBoot 项目的模板使用！还可以在此基础上快速开发自己的项目。



## 学生成绩管理系统

### 实现功能

#### 管理员
- 登录
- 学生管理
- 课程管理
- 选课管理
- 成绩管理 （该功能未实现，但在项目中提供了数据库表，自行实现）

#### 学生
- 登录
- 注册
- 查看课程信息、选课
- 查看、修改个人信息
- 查看成绩信息、评价管理 （该功能和管理员的成绩管理功能绑定，自行实现）

#### 功能扩展
- 成绩管理 (管理员)
- 查看成绩信息、评价管理 (用户)
- 用户修改密码
- 头像上传，即文件上传
- 显示成绩信息统计图表



## 模板特点

### 主流框架 & 特性

- Spring Boot 2.7.x（贼新）
- Spring MVC
- MyBatis + MyBatis Plus 数据访问（开启分页）
- Spring Boot 调试工具和项目处理器
- Spring AOP 切面编程
- Spring 事务注解

### 数据存储

- MySQL 数据库

### 工具类

- Hutool 工具库
- Apache Commons Lang3 工具类
- Lombok 注解

### 业务特性

- 业务代码生成器（支持自动生成 Service、Controller、数据模型代码）
- 全局请求响应拦截器（记录日志）
- 全局异常处理器
- 自定义错误码
- 封装通用响应类
- Swagger + Knife4j 接口文档
- 自定义权限注解 + 全局校验
- 全局跨域处理
- 多环境配置



### 单元测试

- JUnit5 单元测试
- 示例单元测试类

### 架构设计

- 合理分层


## 快速上手

> 所有需要修改的地方都标记了 `todo`，便于大家找到修改的位置~

### MySQL 数据库

1）修改 `application.yml` 的数据库配置为你自己的：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
```

2）执行 `sql/create_table.sql` 中的数据库语句，自动创建库表

3）启动项目，访问 `http://localhost:8101/api/doc.html` 即可打开接口文档，不需要写前端就能在线调试接口了~

![](doc/swagger.png)



### 业务代码生成器

支持自动生成 Service、Controller、数据模型代码，配合 MyBatisX 插件，可以快速开发增删改查等实用基础功能。

找到 `generate.CodeGenerator` 类，修改生成参数和生成路径，并且支持注释掉不需要的生成逻辑，然后运行即可。

```
// 指定生成参数
String packageName = "com.satellite.studentmanagement";
String dataName = "用户评论";
String dataKey = "userComment";
String upperDataKey = "UserComment";
```

生成代码后，可以移动到实际项目中，并且按照 `// todo` 注释的提示来针对自己的业务需求进行修改。
