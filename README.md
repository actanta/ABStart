# ABStart
A Simple Spring Boot Project

## Road-Map
+ 初始化Spring Boot工程
+ 模块分层
+ 简单增删改查

## TODO
0. SQL打印、smart-doc
1. 返回非ResponseEntity，返回默认包装成ResponseEntity<Result<Object>>. 定义统一返回体结构
2. 自定义配置写成配置类，方便查找@ConfigurationProperties(prefix = "xx.xx.xx")
3. 所有二方接口，url都统一定义常量，接口统一写到一个类中
4. 异常枚举中，标识异常分类：认证失败/缺少权限/参数有误
5. 全局异常捕获: √
   - org.springframework.web.bind.MissingServletRequestParameterException √
   - org.springframework.web.method.annotation.MethodArgumentTypeMismatchException √
6. JSR303参数校验
7. MyBatis-Plus--自定义SQLhttps://blog.51cto.com/knifeedge/5139762
8. Excel文件上传解析、导出;文件上传下载、连表查询
9. 接口缓存
10. 接口加密、数字签名。验签解签 
11. 接口安全限流，防暴力破解
12. Word、PDF生成(可选itextPDF)，电子签名到文件上
13. 自定义朋友圈分享海报生成
14. 异常日志展示时显示为红色字体
15. Docker部署
16. 代码生成工具
17. 动态版本号 mvn package -Drevision=1.0.0RELEASE
18. docker push,harbor:dockerfile-maven-plugin
19. mvn deploy，releases|snapshots包deploy到私服不同位置:distributionManagement
20. 线程池优雅关闭，自定义类优雅关闭（Java服务实现优雅的关闭：ShutdownHook/Signal回调https://www.jianshu.com/p/38101bff6c29）
21. 操作日志(支持写入MySQL或ES)
22. 前后端组件程序版本号展示
23. jar单独生成到文件夹
24. 支持tomcat和jar运行
25. seata分布式事务
26. RPC调用示例、心跳实现
27. Netty demo
28. xsd校验
29. Antlrv4实例 
30. 系统命令调用 
31. 多种数据源连接方式支持  MySQL/MariaDB、Oracle、PostgreSQL、MongoDB、Redis、ES 
32. Skywalking链路收集、普罗米修斯监控、系统资源监控(OS/version/osArch/CPU逻辑核数量/内存总大小/硬盘占用率/网卡IP/网卡速率/主机启动时间/进程信息/配置信息)
33. SpringBoot重试注解@Retryable(value = {RemoteAccessException.class},maxAttempts = 3,backoff = @Backoff(delay = 5000)) @Recoverpublic void recover(RemoteAccessException e){dosomething;}
34. 文字国际化、不同地区时间展示问题
35. 登录、注册、RBAC权限、按钮权限、菜单、数据字典


## CHECK
1. 分页插件提示，确认是否有缓存问题：新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)

## QUESTION
1. 请求日志摘要，result日期打印 √
2. 不计划支持全局事务，存在全局事务容易在开发中忘记事务这回事，还是作为平常必须考虑事项为宜 √
3. SQL日志默认红色字体，生产不应该打印SQL。

## 公用基本包
1. 常量包
2. 注解包

## 故障考虑
1. 时间回拨
2. 数据源故障、连接泄露、连接超时、长耗时连接占用
3. 网络断开、网络丢包、网络超时
4. 文件占用、文件(夹)权限、文件目录不存在
5. 未处理异常，三方包内部导致的NPE等记得捕获
6. 线程退出