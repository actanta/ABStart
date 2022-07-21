# ABStart
A Simple Spring Boot Project

## Road-Map
+ 初始化Spring Boot工程
+ 模块分层
+ 简单增删改查

## TODO
1. 返回非ResponseEntity，统一包装成ResponseEntity
2. 自定义配置写成配置类，方便查找@ConfigurationProperties(prefix = "xx.xx.xx")
3. 所有二方接口，url都统一定义常量，接口统一写到一个类中
4. 异常枚举中，标识异常分类：认证失败/缺少权限/参数有误

## CHECK
1. 分页插件提示，确认是否有缓存问题：新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)