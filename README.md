# RestQuickFramework

基于springBoot 2的快速开发框架

## 20200818更新日志

- 优化了下代码
- 优化了下代码生成器
- 加入了 Lombok 
- 替换工具类使用hutool
- 更改文件上传 不与数据库强关联

## 0823更新说明
- 增加文件上传
- 使用zimg做图片服务器
    - `bin/zimg.sh`可以使用docker启动一个zimg的服务
- 增加权限管理
- 还有些文档没有补全 - - 


### 用的啥技术

- Spring Boot 2+
- Spring Framework 
- Spring MVC 
- Spring Data Jpa
- lombok 
- hutool
- 
- velocity 代码生成

### 为啥要整这个项目

1. 为了应付项目初期字段变更严重的现象。每次字段变更维护实体类的是个很闹心的事情(其实可以通过ddl-auto来逆向生成数据库，但是习惯了数据库先行)

2. 疲于写文档，尤其是在上一家公司Word手撸文档。文档又是个不可回避的问题。所以这次加入swagger来方便一下我的工作。

3. 这回新搭建的项目，技术栈终于可以自己做主

4. 目前没发现什么好用的生成jpa风格的注解实体类的工具，这次又加上了swagger的注解


代码生成部分还是使用以前velocity模板的修改了下

### 开发约定

- 建表基础sql
```sql
  -- 如主键为UUID 需修改id、CreateBy与updateBy的类型
  DROP TABLE IF EXISTS `project_info`;
  CREATE TABLE `project_info` (
    `id` int(20) NOT NULL AUTO_INCREMENT,
    `project_name` varchar(50) DEFAULT NULL COMMENT '项目名称',
    `project_desc` varchar(50) DEFAULT NULL COMMENT '项目描述',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` int(20)  DEFAULT NULL COMMENT '创建人',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by` int(20)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
```
- 其他的还没想好


### Rest服务快速体验

1. clone 项目
2. 执行下建库SQL
3. 启动项目
4. `http://localhost:8080/swagger-ui.html` 直接测试接口即可
    
### 代码生成器体验

1. 在数据库中按照规则建表
2. 修改`generater/utils/Contants.java`的数据库连接配置
3. 运行`generater/Generator.java`类
4. 会在`src/java/carbon`下看到生成的代码 
5. 删除多余生成的代码  后期准备优化
6. 如果用idea的同学可以直接shift+F6修改路径把/carbon删除代码就合并到core目录下了
7. 启动项目
    
