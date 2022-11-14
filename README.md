# ABStart
A Simple Spring Boot Project

## Road-Map
+ 初始化Spring Boot工程
+ 模块分层
+ 简单增删改查

## TODO
1. 返回非ResponseEntity，返回默认包装成ResponseEntity<Result<Object>>. 定义统一返回体结构
2. 自定义配置写成配置类，方便查找@ConfigurationProperties(prefix = "xx.xx.xx")
3. 所有二方接口，url都统一定义常量，接口统一写到一个类中
4. 异常枚举中，标识异常分类：认证失败/缺少权限/参数有误
5. 全局异常捕获: √
   - org.springframework.web.bind.MissingServletRequestParameterException √
   - org.springframework.web.method.annotation.MethodArgumentTypeMismatchException √
6. JSR303参数校验
7. MyBatis-Plus--自定义SQLhttps://blog.51cto.com/knifeedge/5139762
8. Excel文件上传解析、导出
9. 接口缓存
10. 接口加密、数字签名。验签解签 
11. 接口安全限流，防暴力破解
12. Word、PDF生成(可选itextPDF)，电子签名到文件上
13. 自定义朋友圈分享海报生成
14. 异常日志展示时显示为红色字体
15. Docker部署
16. 代码生成工具

## CHECK
1. 分页插件提示，确认是否有缓存问题：新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)

## QUESTION
1. 请求日志摘要，result日期打印 √
2. 不计划支持全局事务，存在全局事务容易在开发中忘记事务这回事，还是作为平常必须考虑事项为宜 √
3. SQL日志默认红色字体，生产不应该打印SQL。

## 公用基本包
1. 常量包
2. 注解包