# ABStart 开发计划（基于 README Road-Map）

> 日期：2026-09-03
> 目标：把 Road-Map 未完成项组织成有依赖顺序、可验收的开发计划。
> 约定：`ROAD-xx` 编号对应 README 中 Road-Map 列表顺序。

---

## 一、Road-Map 完成度盘点

| 编号 | Road-Map 事项 | 状态 | 代码依据 / 说明 |
|---|---|---|---|
| ROAD-01 | 初始化 Spring Boot 工程 | ✅ 已完成 | `MainApplication` |
| ROAD-02 | 模块分层 | ✅ 已完成 | `abstart-api` / `abstart-model` / `abstart-suite` |
| ROAD-03 | 代码生成器、增删改查、分页、JSR303 校验 | ✅ 已完成 | `generator/CodeGenerator`(FastAutoGenerator+Freemarker 模板)、`BizUserController` CRUD/分页、`suite/.../validation` 分组校验 |
| ROAD-04 | 请求日志、执行 SQL 打印、TraceID、操作日志到日志库 | 🟡 部分 | 请求日志 `LogAspect` ✅；SQL 打印 p6spy ✅；TraceID：`LogAspect` MDC + `logback-spring.xml` 输出 ✅（2026-09-03 完成）；“操作日志到日志库” ❌ → P2 |
| ROAD-05 | ResponseAdvice 统一返回结构 | ✅ 已完成 | `api/aop/ResponseAdvice` |
| ROAD-06 | 全局异常捕获、统一处理 | ✅ 已完成 | `api/config/ControllerExceptionHandler` |
| ROAD-07 | 登录模块：盐值、JWT/Session、登录拦截、HttpOnly Cookie | 🟡 部分 | BCrypt 盐 ✅、sa-token(Session/token) ✅、登录拦截 `AuthInterceptor` ✅（2026-09-03 完成）；HttpOnly Cookie ❌ → P1 |
| ROAD-08 | 权限：用户角色权限、接口/数据权限、操作权限，防水平/垂直越权 | ❌ 未开始 | 无角色/权限表与任何鉴权代码 → P1（最大块） |
| ROAD-09 | 防重放、防爆破、限流 | ❌ 未开始 | → P1 |
| ROAD-10 | 接口加密？（带 ?，待决策） | ⏸ 待决策 | → 决策点 D2，倾向并入 P3 |
| ROAD-11 | 业务/错误/请求日志分别存储 | ❌ 未开始 | 当前统一 console+文件 → P0 |
| ROAD-12 | 文件上传、下载 | ❌ 未开始 | → P2 |
| ROAD-13 | 通用能力下层（接口、鉴权） | ❌ 未开始 | 下沉为可复用模块 → 决策点 D4 |
| ROAD-14 | smart-doc 接口文档 | ❌ 未开始 | → P0 |
| ROAD-15 | 文件上传下载；Excel 解析、json 导出 | ❌ 未开始 | 与 ROAD-12 合并 → P2 |
| ROAD-16 | 接口缓存、Gzip、接口加密、数字签名、验签解签 | ❌ 未开始 | → P3 |
| ROAD-17 | Docker 部署 | ❌ 未开始 | → P3 |
| ROAD-18 | jar 单独生成到文件夹改造 | ❌ 未开始 | → P0 |
| ROAD-19 | 系统资源监控 | ❌ 未开始 | → P0 |
| ROAD-20 | 线程池、优雅关闭 | ❌ 未开始 | → P0 |
| ROAD-21 | 操作日志优雅记录（参考美团文） | ❌ 未开始 | 与 ROAD-04 合并 → P2 |

---

## 二、里程碑与执行计划

依赖主线：`P0 工程基础 → P1 权限/安全 → P2 操作日志与文件 → P3 平台化/部署`。
P1 依赖登录能力（已具备），P2 依赖 P1 的登录用户上下文（已具备 `UserContextHolder.getUserId()`）。

### P0 工程基础完善（低风险、互不依赖，可并行）

| 计划项 | 对应 | 目标与范围 | 主要产物 | 验收标准 |
|---|---|---|---|---|
| P0-1 jar 输出到独立文件夹 | ROAD-18 | 构建产物统一收敛：可执行 fat jar + 依赖 + 配置 + 启停脚本，输出到 `dist/` | pom 构建改造（maven-assembly/copy + spring-boot repackage） | `mvn package` 后在约定目录可直接 `start.sh` 运行 |
| P0-2 smart-doc | ROAD-14 | 引入 smart-doc-maven-plugin；Controller/Javadoc 注释规范 | pom 插件 + 注释规范补充 | `mvn smart-doc:html` 生成接口文档 |
| P0-3 线程池与优雅关闭 | ROAD-20 | suite 提供统一线程池 Bean（core/max/queue/命名/拒绝策略），注册 JVM/Spring shutdown 钩子优雅关闭；异步任务可复用 | `suite/.../thread` 线程池配置 + 启动类钩子 | 并发任务执行正常；关闭时在途任务 drain 后退出 |
| P0-4 日志分文件存储 | ROAD-11 | 在 `logback-spring.xml` 增加分组 appender：请求日志、错误日志、业务日志分文件，复用滚动策略 | logback 配置扩展 | 请求/错误/业务日志分别落到独立文件 |
| P0-5 系统资源监控 | ROAD-19 | 只读采集 OS/version/arch/CPU 核数/内存/磁盘占用/网卡 IP 与速率/主机启动时间/进程与配置信息；优先 JDK 内建 API，必要时引入 OSHI | 新 controller+service（如 `/api/v1/monitor/system`） | 接口返回上述资源信息（登录后访问） |

### P1 权限与安全主线（核心，投入最大）

| 计划项 | 对应 | 目标与范围 | 主要产物 | 验收标准 |
|---|---|---|---|---|
| P1-1 RBAC 权限体系 | ROAD-08 | ①建表：`role`、`permission`(菜单/接口权限点)、`user_role`、`role_permission`（沿用现有生成器生成 DO/Service/Controller）；②登录时加载角色/权限到 sa-token 会话（实现 `StpInterface` 或自定义加载）；③接口权限：注解 `@RequirePermission`/`@SaCheckPermission` + 全局校验（AuthInterceptor 链扩展）；④数据权限初版：先做“仅本人数据”示范（查询按 userId 过滤），组织级留扩展；⑤越权防护：水平越权=数据归属校验（owner 过滤、更新删除校验归属），垂直越权=角色层级校验 | db.sql 迁移 + 生成的 CRUD 代码 + 权限注解/切面 + 越权校验工具 | 登录后按角色/权限访问受限接口：有权限放行、无权限返回 403 统一 JSON；构造越权用例被拦截 |
| P1-2 登录补 HttpOnly Cookie | ROAD-07 补缺 | 登录成功经 sa-token 下发 `HttpOnly` Cookie（Secure/SameSite 可配），保留现有返回 tokenValue 模式（开关切换） | 登录/Cookie 配置 | 登录响应 `Set-Cookie: ...; HttpOnly`；开关可回退 header 模式 |
| P1-3 防爆破/限流/防重放 | ROAD-09 | ①登录防爆破：失败计数+临时锁定（Caffeine 本地缓存，集群需 Redis → D1）；②接口限流：注解 `@RateLimit` + 拦截器（令牌桶/滑动窗口）；③防重放：timestamp 时间窗 + nonce 幂等去重 | 新注解+拦截器/切面 + CodeMsg 新错误码 | 爆破/超限/重放请求被拒并返回明确 code；正常请求不受影响 |
| P1-4 接口加密决策 | ROAD-10 | 需求澄清（D2）：是否落地，建议与 P3 加密切面合并 | — | 决策结论 |

### P2 操作日志与文件能力

| 计划项 | 对应 | 目标与范围 | 主要产物 | 验收标准 |
|---|---|---|---|---|
| P2-1 操作日志（优雅记录） | ROAD-21 / ROAD-04 | AOP 注解 `@OperationLog(module/action)` 环绕记录：操作人（`UserContextHolder`）、请求参数、结果/异常、IP、耗时；异步（复用 P0-3 线程池）写入日志库表 `op_log`，失败不影响主流程 | 注解+切面+`op_log` 表+写入服务 | 写接口产生操作日志行；日志写失败不影响业务返回 |
| P2-2 文件上传下载 + Excel + json 导出 | ROAD-12 / ROAD-15 | 文件存储抽象（本地实现，预留 OSS 扩展）+ 上传/下载接口；Excel 解析与模板导出、json 导出 Demo（hutool/EasyExcel） | 存储抽象 + 文件接口 + Excel 工具 | 上传后可下载；Excel 导入导出、json 导出数据正确 |

### P3 平台化 / 工程化

| 计划项 | 对应 | 目标与范围 | 主要产物 | 验收标准 |
|---|---|---|---|---|
| P3-1 缓存 / Gzip / 加密切面 / 签名验签 | ROAD-16 | 接口缓存（注解+Redis 或本地缓存，依赖 D1）、Gzip 压缩、报文加解密与数字签名/验签（开放平台场景） | 过滤器/拦截器/注解 | 各能力开关化、可配置，按场景启用 |
| P3-2 通用能力下沉 | ROAD-13 | 评估把拦截器链/UserContext/异常与 Result/鉴权封装为可复用能力（suite 已含部分，未来抽 starter 供多应用） | 能力边界梳理 | 单应用下能力复用无重复实现；抽 starter 视需要（D4） |
| P3-3 Docker 部署 | ROAD-17 | Dockerfile（多阶段构建）、docker-compose（app+mysql）、健康检查、profile 切换与日志卷挂载 | Dockerfile + compose + 部署文档 | `docker compose up` 后可访问并采集日志 |

---

## 三、全局决策点（建议开工前确认）

| 决策 | 问题 | 建议 |
|---|---|---|
| D1 | 是否引入 Redis？（影响限流/防重放/缓存/分布式锁） | demo 阶段可用 Caffeine/本地实现过渡，引入 Redis 更贴近生产，视部署目标定 |
| D2 | Road-Map 带 `?` 的“接口加密”及 P3 的加密/签名/验签是否落地 | 若无对外开放平台诉求，建议只保留扩展位，不做报文加解密 |
| D3 | 数据权限粒度 | 项目暂无组织/机构模型，第一版建议“仅本人数据”示范，组织级权限后续随业务建模 |
| D4 | “通用能力下层”下沉时机 | 单应用收益有限，待出现第二个应用时再抽公共模块/starters |
| D5 | smart-doc 依赖代码注释规范 | 在生成代码模板中固化 Javadoc 注释（`templates/*.ftl` 已含基础注释） |

---

## 四、建议执行顺序

1. **P0（1 个迭代）**：5 项均为独立小改造，先做 P0-1/P0-3/P0-4（工程与运行体验），P0-2/P0-5 随时可插。
2. **P1（1–2 个迭代）**：P1-1 权限是主线、依赖最重，先行；P1-2、P1-3 紧随。
3. **P2**：依赖 P1 登录上下文完备后开始。
4. **P3**：视部署与平台化诉求决定优先级，可与 P2 穿插。

> 每个迭代结束：`./mvnw -pl abstart-api -am clean test` 回归 + 相关接口联调 + 更新本文件完成度。
