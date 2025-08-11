 --通用分类表
 -- 如果各模块分类较少，可用此统一分类表替代模块专属分类表
 CREATE TABLE `system_category` (
   `id` int NOT NULL AUTO_INCREMENT,
   `module` varchar(20) NOT NULL COMMENT '所属模块（schedule/goal/health等）',
   `name` varchar(50) NOT NULL COMMENT '分类名称',
   `parent_id` int DEFAULT NULL COMMENT '父分类ID',
   `level` tinyint DEFAULT '1' COMMENT '层级',
   `sort_order` int DEFAULT '0' COMMENT '排序',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_module_name` (`module`,`name`),
   KEY `idx_module` (`module`)
 ) ENGINE=InnoDB COMMENT='系统通用分类表';

 --系统配置表
 CREATE TABLE `system_config` (
   `id` int NOT NULL AUTO_INCREMENT,
   `config_key` varchar(50) NOT NULL COMMENT '配置键',
   `config_value` text COMMENT '配置值',
   `description` varchar(255) DEFAULT NULL COMMENT '配置说明',
   `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_key` (`config_key`)
 ) ENGINE=InnoDB COMMENT='系统配置表';

--资产表
CREATE TABLE `asset` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资产编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资产名称',
  `owner_id` bigint DEFAULT NULL COMMENT '所有者编号',
  `category_id` int DEFAULT NULL COMMENT '资产分类ID（关联asset_category表）',
  `amount` bigint NOT NULL DEFAULT '1' COMMENT '资产数量',
  `balance` bigint DEFAULT NULL COMMENT '资产余量',
  `purchase_price` bigint DEFAULT NULL COMMENT '采购价格（单位：分）',
  `purchase_time` datetime DEFAULT NULL COMMENT '采购时间',
  `expiration_time` datetime DEFAULT NULL COMMENT '过期时间',
  `purchase_channel` tinyint NOT NULL DEFAULT '0' COMMENT '采购渠道：0未分类 1线下 2淘宝 3京东 4拼多多',
  `purchase_channel_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购渠道具体信息',
  `image_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片路径（多图用JSON数组存储）',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '资产状态：0删除 1正常 2维修中 3已丢失',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_owner_id` (`owner_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_purchase_time` (`purchase_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产记录表';

--资产分类表
CREATE TABLE `abstart`.`asset_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `parent_id` int DEFAULT NULL COMMENT '父分类ID（顶级分类为NULL）',
  `level` tinyint NOT NULL COMMENT '分类层级（1:一级分类 2:二级分类...）',
  `sort_order` int DEFAULT '0' COMMENT '排序字段',
  `status` tinyint DEFAULT '1' COMMENT '状态（0:禁用 1:启用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产分类表（树状结构）';

-- 插入示例数据（对应原AssetDO中的分类说明）
INSERT INTO `abstart`.`asset_category` (`id`, `name`, `parent_id`, `level`, `sort_order`) VALUES
(1, '衣物', NULL, 1, 1),
(2, '数码', NULL, 1, 2),
(3, '食物', NULL, 1, 3),
(4, '学习', NULL, 1, 4),
(5, '上衣', 1, 2, 1),
(6, '裤子', 1, 2, 2),
(7, '手机', 2, 2, 1),
(8, '电脑', 2, 2, 2);

--资产标签表
CREATE TABLE `abstart`.`asset_tag` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签颜色（如#FF0000）',
  `sort_order` int DEFAULT '0' COMMENT '排序字段',
  `status` tinyint DEFAULT '1' COMMENT '状态（0:禁用 1:启用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产标签表';

-- 插入示例数据（对应原AssetDO中的标签说明）
INSERT INTO `abstart`.`asset_tag` (`id`, `name`, `color`, `sort_order`) VALUES
(1, '必需品', '#FF0000', 1),
(2, '双十一', '#00FF00', 2),
(3, '生日', '#0000FF', 3);

-- 资产标签关联表
CREATE TABLE `abstart`.`asset_tag_mapping` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `asset_id` bigint NOT NULL COMMENT '资产ID',
  `tag_id` int NOT NULL COMMENT '标签ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_asset_tag` (`asset_id`,`tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资产标签关联表';

-- 查询资产及其分类和标签
SELECT
    a.*,
    c.name AS category_name,
    GROUP_CONCAT(t.name) AS tag_names
FROM
    asset a
LEFT JOIN
    asset_category c ON a.category_id = c.id
LEFT JOIN
    asset_tag_mapping tm ON a.id = tm.asset_id
LEFT JOIN
    asset_tag t ON tm.tag_id = t.id
GROUP BY
    a.id;



--【时间管理模块】
-- 日程表（支持重复事件）
CREATE TABLE `schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '日程标题',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime COMMENT '结束时间',
  `rrule` varchar(200) DEFAULT NULL COMMENT '重复规则（iCalendar格式）',
  `location` varchar(100) DEFAULT NULL COMMENT '地点',
  `priority` tinyint DEFAULT '3' COMMENT '优先级（1-5）',
  `status` tinyint DEFAULT '0' COMMENT '状态（0:待办 1:已完成 2:已取消）',
  `category_id` int DEFAULT NULL COMMENT '分类ID（关联schedule_category）',
  `description` text COMMENT '详细描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_time_range` (`start_time`, `end_time`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB COMMENT='日程表';

-- 日程分类表
CREATE TABLE `schedule_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `color` varchar(20) DEFAULT '#3498db' COMMENT '显示颜色',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标类名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB COMMENT='日程分类';

-- 时间块表（专注管理）
CREATE TABLE `time_block` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL COMMENT '日期',
  `start_hour` tinyint NOT NULL COMMENT '开始小时（0-23）',
  `end_hour` tinyint NOT NULL COMMENT '结束小时（0-23）',
  `activity_type` tinyint NOT NULL COMMENT '活动类型（1:工作 2:学习 3:运动 4:娱乐 5:休息）',
  `productivity` tinyint DEFAULT NULL COMMENT '效率评分（1-10）',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_date` (`date`)
) ENGINE=InnoDB COMMENT='每日时间块记录';

--【目标管理模块】
-- 目标总表
CREATE TABLE `goal` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '目标标题',
  `description` text COMMENT '详细描述',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '截止日期',
  `priority` tinyint DEFAULT '3' COMMENT '优先级（1-5）',
  `status` tinyint DEFAULT '0' COMMENT '状态（0:规划中 1:进行中 2:已完成 3:已放弃）',
  `progress` int DEFAULT '0' COMMENT '进度百分比（0-100）',
  `category_id` int DEFAULT NULL COMMENT '分类ID（关联goal_category）',
  `parent_id` bigint DEFAULT NULL COMMENT '父目标ID（支持多级目标）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB COMMENT='目标管理表';

-- 目标分类表
CREATE TABLE `goal_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标类名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB COMMENT='目标分类';

-- 关键结果表（OKR模式）
CREATE TABLE `key_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `goal_id` bigint NOT NULL COMMENT '关联目标ID',
  `title` varchar(100) NOT NULL COMMENT '关键结果标题',
  `metric` varchar(100) DEFAULT NULL COMMENT '衡量指标（如：阅读10本书）',
  `target_value` varchar(50) DEFAULT NULL COMMENT '目标值',
  `current_value` varchar(50) DEFAULT NULL COMMENT '当前值',
  `status` tinyint DEFAULT '0' COMMENT '状态（0:未开始 1:进行中 2:已完成）',
  PRIMARY KEY (`id`),
  KEY `idx_goal_id` (`goal_id`)
) ENGINE=InnoDB COMMENT='关键结果表';

--【健康管理模块】
-- 身体数据表
CREATE TABLE `health_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `record_date` date NOT NULL COMMENT '记录日期',
  `weight` decimal(5,1) DEFAULT NULL COMMENT '体重（kg）',
  `height` decimal(5,2) DEFAULT NULL COMMENT '身高（cm）',
  `body_fat` decimal(5,2) DEFAULT NULL COMMENT '体脂率（%）',
  `sleep_hours` decimal(3,1) DEFAULT NULL COMMENT '睡眠时长（小时）',
  `steps` int DEFAULT NULL COMMENT '步数',
  `water_intake` int DEFAULT NULL COMMENT '饮水量（ml）',
  `mood` tinyint DEFAULT NULL COMMENT '心情评分（1-10）',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_date` (`record_date`)
) ENGINE=InnoDB COMMENT='每日健康数据';

-- 运动记录表
CREATE TABLE `exercise_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `exercise_date` date NOT NULL COMMENT '运动日期',
  `start_time` time DEFAULT NULL COMMENT '开始时间',
  `end_time` time DEFAULT NULL COMMENT '结束时间',
  `type_id` int NOT NULL COMMENT '运动类型ID（关联exercise_type）',
  `duration` int DEFAULT NULL COMMENT '持续时间（分钟）',
  `calories` int DEFAULT NULL COMMENT '消耗卡路里',
  `intensity` tinyint DEFAULT NULL COMMENT '强度（1-5）',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_date` (`exercise_date`),
  KEY `idx_type` (`type_id`)
) ENGINE=InnoDB COMMENT='运动记录表';

-- 运动类型表
CREATE TABLE `exercise_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '运动名称',
  `unit` varchar(20) DEFAULT '次' COMMENT '计量单位',
  `is_default` tinyint DEFAULT '0' COMMENT '是否系统默认',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB COMMENT='运动类型表';

--【知识管理模块】
-- 笔记表（支持Markdown）
CREATE TABLE `note` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '笔记标题',
  `content` longtext COMMENT '笔记内容（Markdown格式）',
  `category_id` int DEFAULT NULL COMMENT '分类ID（关联note_category）',
  `source` varchar(100) DEFAULT NULL COMMENT '来源（书籍/文章/视频等）',
  `status` tinyint DEFAULT '1' COMMENT '状态（0:草稿 1:已发布 2:已归档）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category_id`),
  FULLTEXT KEY `ft_content` (`content`) COMMENT '全文索引（需MySQL 5.6+）'
) ENGINE=InnoDB COMMENT='笔记表';

-- 笔记分类表（支持多级）
CREATE TABLE `note_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` int DEFAULT NULL COMMENT '父分类ID',
  `level` tinyint DEFAULT '1' COMMENT '层级',
  PRIMARY KEY (`id`),
  KEY `idx_parent` (`parent_id`)
) ENGINE=InnoDB COMMENT='笔记分类表';

-- 阅读记录表
CREATE TABLE `reading_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '书名',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `finish_date` date DEFAULT NULL COMMENT '完成日期',
  `rating` tinyint DEFAULT NULL COMMENT '评分（1-10）',
  `status` tinyint DEFAULT '1' COMMENT '状态（0:未开始 1:阅读中 2:已读完 3:已放弃）',
  `category_id` int DEFAULT NULL COMMENT '分类ID（关联reading_category）',
  `note` text COMMENT '读书笔记',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB COMMENT='阅读记录表';

--【人际关系模块】
-- 联系人表
CREATE TABLE `contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `company` varchar(100) DEFAULT NULL COMMENT '公司',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `relationship_type` tinyint DEFAULT NULL COMMENT '关系类型（1:家人 2:朋友 3:同事 4:其他）',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `last_contact_date` date DEFAULT NULL COMMENT '最后联系日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_relationship` (`relationship_type`)
) ENGINE=InnoDB COMMENT='联系人表';

-- 互动记录表
CREATE TABLE `interaction_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_id` bigint NOT NULL COMMENT '联系人ID',
  `interaction_date` datetime NOT NULL COMMENT '互动日期',
  `type` tinyint NOT NULL COMMENT '互动类型（1:电话 2:微信 3:见面 4:邮件）',
  `content` varchar(255) DEFAULT NULL COMMENT '互动内容摘要',
  `duration` int DEFAULT NULL COMMENT '时长（分钟，仅见面/电话适用）',
  `next_follow_up` date DEFAULT NULL COMMENT '下次跟进日期',
  PRIMARY KEY (`id`),
  KEY `idx_contact` (`contact_id`),
  KEY `idx_date` (`interaction_date`)
) ENGINE=InnoDB COMMENT='互动记录表';




-----------------------
习惯养成模块
习惯类别表 (habit_categories)
sql
CREATE TABLE habit_categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL COMMENT '习惯类别名称',
    description VARCHAR(200) COMMENT '类别描述',
    icon_url VARCHAR(255) COMMENT '类别图标URL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='习惯类别表';
习惯表 (habits)
sql
CREATE TABLE habits (
    habit_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    category_id INT COMMENT '所属类别ID',
    habit_name VARCHAR(100) NOT NULL COMMENT '习惯名称',
    description VARCHAR(500) COMMENT '习惯描述',
    goal_frequency ENUM('daily', 'weekly', 'monthly') NOT NULL COMMENT '目标频率',
    target_count INT NOT NULL COMMENT '目标次数/天数',
    reminder_time TIME COMMENT '提醒时间',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES habit_categories(category_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户习惯表';
习惯打卡记录表 (habit_records)
sql
CREATE TABLE habit_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    habit_id INT NOT NULL COMMENT '习惯ID',
    user_id INT NOT NULL COMMENT '用户ID',
    record_date DATE NOT NULL COMMENT '打卡日期',
    completed_count INT DEFAULT 1 COMMENT '完成次数',
    notes VARCHAR(500) COMMENT '打卡备注',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (habit_id) REFERENCES habits(habit_id),
    INDEX idx_user_habit_date (user_id, habit_id, record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='习惯打卡记录表';

--------
财务管理模块（扩展现有资产表）
假设现有资产表为 assets，我们扩展以下表：
交易类别表 (transaction_categories)
sql
CREATE TABLE transaction_categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL COMMENT '类别名称',
    category_type ENUM('income', 'expense') NOT NULL COMMENT '类别类型',
    parent_id INT DEFAULT NULL COMMENT '父类别ID',
    icon_url VARCHAR(255) COMMENT '图标URL',
    is_system BOOLEAN DEFAULT TRUE COMMENT '是否系统内置',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES transaction_categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易类别表';
交易记录表 (transactions)
sql
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    asset_id INT COMMENT '关联资产ID',
    category_id INT NOT NULL COMMENT '交易类别ID',
    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',
    transaction_date DATETIME NOT NULL COMMENT '交易时间',
    description VARCHAR(500) COMMENT '交易描述',
    counterpart VARCHAR(100) COMMENT '交易对方',
    transaction_type ENUM('income', 'expense', 'transfer') NOT NULL COMMENT '交易类型',
    related_transaction_id INT DEFAULT NULL COMMENT '关联交易ID(转账用)',
    is_reconciled BOOLEAN DEFAULT FALSE COMMENT '是否已对账',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES transaction_categories(category_id),
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id),
    INDEX idx_user_date (user_id, transaction_date),
    INDEX idx_user_asset (user_id, asset_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易记录表';
预算表 (budgets)
sql
CREATE TABLE budgets (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    category_id INT COMMENT '预算类别ID',
    time_period ENUM('daily', 'weekly', 'monthly', 'quarterly', 'yearly') NOT NULL COMMENT '预算周期',
    start_date DATE NOT NULL COMMENT '预算开始日期',
    end_date DATE NOT NULL COMMENT '预算结束日期',
    budget_amount DECIMAL(15,2) NOT NULL COMMENT '预算金额',
    actual_amount DECIMAL(15,2) DEFAULT 0 COMMENT '实际支出金额',
    remaining_amount DECIMAL(15,2) GENERATED ALWAYS AS (budget_amount - actual_amount) STORED COMMENT '剩余金额',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES transaction_categories(category_id),
    INDEX idx_user_period (user_id, time_period, start_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预算表';


隐私保护字段（扩展联系人表为例）
假设现有联系人表为 contacts，我们添加加密字段：
sql
ALTER TABLE contacts
ADD COLUMN encrypted_phone VARCHAR(255) COMMENT '加密后的电话号码',
ADD COLUMN encrypted_email VARCHAR(255) COMMENT '加密后的邮箱',
ADD COLUMN encryption_key VARCHAR(255) COMMENT '加密密钥(存储在安全位置)',
ADD COLUMN is_sensitive BOOLEAN DEFAULT FALSE COMMENT '是否敏感信息';
或者创建单独的敏感联系人信息表（更安全的方式）：
sql
CREATE TABLE sensitive_contact_info (
    info_id INT AUTO_INCREMENT PRIMARY KEY,
    contact_id INT NOT NULL COMMENT '关联联系人ID',
    user_id INT NOT NULL COMMENT '用户ID',
    encrypted_phone VARCHAR(255) COMMENT '加密后的电话号码',
    encrypted_email VARCHAR(255) COMMENT '加密后的邮箱',
    encryption_version VARCHAR(20) DEFAULT 'AES-256' COMMENT '加密算法版本',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (contact_id) REFERENCES contacts(contact_id),
    INDEX idx_user_contact (user_id, contact_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感联系人信息表';



数据可视化看板说明
数据可视化看板主要需要应用层开发，但数据库层面需要确保有适当的索引和可能的数据聚合表。例如：

sql
-- 示例：每日交易汇总表（可用于可视化）
CREATE TABLE daily_transaction_summaries (
    summary_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    summary_date DATE NOT NULL COMMENT '汇总日期',
    total_income DECIMAL(15,2) DEFAULT 0 COMMENT '总收入',
    total_expense DECIMAL(15,2) DEFAULT 0 COMMENT '总支出',
    income_count INT DEFAULT 0 COMMENT '收入交易数',
    expense_count INT DEFAULT 0 COMMENT '支出交易数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_date (user_id, summary_date),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日交易汇总表';
实际应用中，这个表可以通过定时任务从transactions表生成，或者由应用层在查询时动态计算。
















































一、财务管理模块优化设计
1. 核心表结构优化
(1) 账户体系增强
sql
CREATE TABLE financial_accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    account_name VARCHAR(100) NOT NULL COMMENT '账户名称',
    account_type ENUM('cash', 'debit_card', 'credit_card', 'savings', 'investment', 'loan', 'virtual') NOT NULL COMMENT '账户类型',
    institution_name VARCHAR(100) COMMENT '金融机构名称',
    account_number_masked VARCHAR(50) COMMENT '掩码显示的账号',
    currency_code CHAR(3) DEFAULT 'CNY' COMMENT '货币代码',
    current_balance DECIMAL(15,2) NOT NULL COMMENT '当前余额',
    available_balance DECIMAL(15,2) COMMENT '可用余额(信用卡可用额度等)',
    interest_rate DECIMAL(10,4) COMMENT '利率(储蓄/贷款)',
    payment_due_date DATE COMMENT '还款日(信用卡/贷款)',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    is_default BOOLEAN DEFAULT FALSE COMMENT '是否默认账户',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user (user_id),
    INDEX idx_user_type (user_id, account_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务账户表';
(2) 交易记录增强版
sql
CREATE TABLE financial_transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    account_id INT NOT NULL COMMENT '关联账户ID',
    transaction_type ENUM('income', 'expense', 'transfer', 'payment', 'refund', 'interest', 'fee') NOT NULL COMMENT '交易类型',
    category_id INT NOT NULL COMMENT '交易类别ID',
    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',
    currency_code CHAR(3) DEFAULT 'CNY' COMMENT '货币代码',
    transaction_date DATETIME NOT NULL COMMENT '交易时间',
    post_date DATE COMMENT '入账日期',
    description VARCHAR(500) COMMENT '交易描述',
    counterpart VARCHAR(100) COMMENT '交易对方/商户名称',
    counterpart_account VARCHAR(50) COMMENT '对方账号(转账时)',
    transaction_status ENUM('pending', 'completed', 'failed', 'refunded') DEFAULT 'completed' COMMENT '交易状态',
    payment_method ENUM('cash', 'card', 'bank_transfer', 'third_party', 'check') COMMENT '支付方式',
    related_transaction_id BIGINT DEFAULT NULL COMMENT '关联交易ID(转账/退款用)',
    is_reconciled BOOLEAN DEFAULT FALSE COMMENT '是否已对账',
    reconciliation_note VARCHAR(255) COMMENT '对账备注',
    location_lat DECIMAL(10,6) COMMENT '交易地点纬度',
    location_lng DECIMAL(10,6) COMMENT '交易地点经度',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES financial_accounts(account_id),
    INDEX idx_user_date (user_id, transaction_date),
    INDEX idx_user_account (user_id, account_id),
    INDEX idx_user_category (user_id, category_id),
    INDEX idx_user_counterpart (user_id, counterpart(20))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务交易记录表';
(3) 交易类别体系
sql
CREATE TABLE transaction_categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT DEFAULT NULL COMMENT '父类别ID',
    category_name VARCHAR(50) NOT NULL COMMENT '类别名称',
    category_type ENUM('income', 'expense', 'transfer') NOT NULL COMMENT '类别类型',
    icon_name VARCHAR(50) COMMENT '图标名称',
    icon_color VARCHAR(20) COMMENT '图标颜色',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_system BOOLEAN DEFAULT TRUE COMMENT '是否系统内置',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES transaction_categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易类别表';

-- 示例数据
INSERT INTO transaction_categories
(parent_id, category_name, category_type, icon_name, icon_color, is_system) VALUES
(NULL, '收入', 'income', 'income', '#4CAF50', TRUE),
(1, '工资', 'income', 'salary', '#4CAF50', TRUE),
(1, '奖金', 'income', 'bonus', '#4CAF50', TRUE),
(NULL, '支出', 'expense', 'expense', '#F44336', TRUE),
(4, '餐饮', 'expense', 'food', '#FFC107', TRUE),
(4, '交通', 'expense', 'transport', '#2196F3', TRUE);
(4) 预算系统增强
sql
CREATE TABLE financial_budgets (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    category_id INT NOT NULL COMMENT '预算类别ID',
    budget_type ENUM('fixed', 'flexible') DEFAULT 'flexible' COMMENT '预算类型',
    time_period ENUM('weekly', 'biweekly', 'monthly', 'quarterly', 'yearly') NOT NULL COMMENT '预算周期',
    start_date DATE NOT NULL COMMENT '预算开始日期',
    end_date DATE NOT NULL COMMENT '预算结束日期',
    budget_amount DECIMAL(15,2) NOT NULL COMMENT '预算金额',
    actual_amount DECIMAL(15,2) DEFAULT 0 COMMENT '实际支出金额',
    remaining_amount DECIMAL(15,2) GENERATED ALWAYS AS (budget_amount - actual_amount) STORED COMMENT '剩余金额',
    warning_threshold DECIMAL(5,2) DEFAULT 0.8 COMMENT '预警阈值(0-1)',
    is_auto_renew BOOLEAN DEFAULT TRUE COMMENT '是否自动续期',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES transaction_categories(category_id),
    INDEX idx_user_period (user_id, time_period, start_date),
    INDEX idx_user_category (user_id, category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务预算表';
2. 高级功能表设计
(1) 定期交易管理
sql
CREATE TABLE recurring_transactions (
    recurring_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    template_name VARCHAR(100) NOT NULL COMMENT '模板名称',
    transaction_type ENUM('income', 'expense', 'transfer') NOT NULL COMMENT '交易类型',
    category_id INT NOT NULL COMMENT '交易类别ID',
    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',
    currency_code CHAR(3) DEFAULT 'CNY' COMMENT '货币代码',
    frequency_type ENUM('daily', 'weekly', 'biweekly', 'monthly', 'quarterly', 'yearly') NOT NULL COMMENT '频率类型',
    frequency_interval INT DEFAULT 1 COMMENT '频率间隔',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE DEFAULT NULL COMMENT '结束日期(NULL表示无限期)',
    next_occurrence_date DATE NOT NULL COMMENT '下次发生日期',
    last_processed_date DATE DEFAULT NULL COMMENT '上次处理日期',
    account_id INT NOT NULL COMMENT '关联账户ID',
    counterpart VARCHAR(100) COMMENT '交易对方',
    description VARCHAR(500) COMMENT '交易描述',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES financial_accounts(account_id),
    FOREIGN KEY (category_id) REFERENCES transaction_categories(category_id),
    INDEX idx_user_next_date (user_id, next_occurrence_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定期交易模板表';
(2) 交易标签系统
sql
CREATE TABLE transaction_tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    tag_name VARCHAR(30) NOT NULL COMMENT '标签名称',
    tag_color VARCHAR(20) DEFAULT '#9E9E9E' COMMENT '标签颜色',
    is_system BOOLEAN DEFAULT FALSE COMMENT '是否系统标签',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_tag (user_id, tag_name),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易标签表';

CREATE TABLE transaction_tag_mappings (
    mapping_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id BIGINT NOT NULL COMMENT '交易ID',
    tag_id INT NOT NULL COMMENT '标签ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES financial_transactions(transaction_id),
    FOREIGN KEY (tag_id) REFERENCES transaction_tags(tag_id),
    UNIQUE KEY uk_trans_tag (transaction_id, tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易标签映射表';
(3) 财务目标管理
sql
CREATE TABLE financial_goals (
    goal_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    goal_name VARCHAR(100) NOT NULL COMMENT '目标名称',
    goal_type ENUM('savings', 'debt_payment', 'investment', 'purchase') NOT NULL COMMENT '目标类型',
    target_amount DECIMAL(15,2) NOT NULL COMMENT '目标金额',
    current_amount DECIMAL(15,2) DEFAULT 0 COMMENT '当前金额',
    target_date DATE NOT NULL COMMENT '目标日期',
    account_id INT DEFAULT NULL COMMENT '关联账户ID',
    category_id INT DEFAULT NULL COMMENT '关联类别ID',
    description VARCHAR(500) COMMENT '目标描述',
    priority ENUM('low', 'medium', 'high') DEFAULT 'medium' COMMENT '优先级',
    progress_percentage DECIMAL(5,2) GENERATED ALWAYS AS ((current_amount / target_amount) * 100) STORED COMMENT '进度百分比',
    is_completed BOOLEAN DEFAULT FALSE COMMENT '是否完成',
    completed_at TIMESTAMP NULL COMMENT '完成时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES financial_accounts(account_id),
    FOREIGN KEY (category_id) REFERENCES transaction_categories(category_id),
    INDEX idx_user (user_id),
    INDEX idx_user_completed (user_id, is_completed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务目标表';
二、数据可视化看板优化设计
1. 核心数据汇总表设计
(1) 每日财务摘要
sql
CREATE TABLE daily_financial_summaries (
    summary_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    summary_date DATE NOT NULL COMMENT '汇总日期',
    total_income DECIMAL(15,2) DEFAULT 0 COMMENT '总收入',
    total_expense DECIMAL(15,2) DEFAULT 0 COMMENT '总支出',
    net_cash_flow DECIMAL(15,2) GENERATED ALWAYS AS (total_income - total_expense) STORED COMMENT '净现金流',
    income_count INT DEFAULT 0 COMMENT '收入交易数',
    expense_count INT DEFAULT 0 COMMENT '支出交易数',
    top_income_category VARCHAR(50) COMMENT '主要收入类别',
    top_expense_category VARCHAR(50) COMMENT '主要支出类别',
    avg_income_per_transaction DECIMAL(15,2) GENERATED ALWAYS AS (
        CASE WHEN income_count > 0 THEN total_income / income_count ELSE 0 END
    ) STORED COMMENT '平均每笔收入金额',
    avg_expense_per_transaction DECIMAL(15,2) GENERATED ALWAYS AS (
        CASE WHEN expense_count > 0 THEN total_expense / expense_count ELSE 0 END
    ) STORED COMMENT '平均每笔支出金额',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_date (user_id, summary_date),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日财务摘要表';
(2) 每月财务趋势
sql
CREATE TABLE monthly_financial_trends (
    trend_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    year_month CHAR(7) NOT NULL COMMENT '年月(YYYY-MM)',
    total_income DECIMAL(15,2) DEFAULT 0 COMMENT '月总收入',
    total_expense DECIMAL(15,2) DEFAULT 0 COMMENT '月总支出',
    net_savings DECIMAL(15,2) GENERATED ALWAYS AS (total_income - total_expense) STORED COMMENT '净储蓄',
    income_growth_rate DECIMAL(10,2) COMMENT '收入同比增长率(%)',
    expense_growth_rate DECIMAL(10,2) COMMENT '支出同比增长率(%)',
    top_income_categories VARCHAR(500) COMMENT '主要收入类别(JSON数组)',
    top_expense_categories VARCHAR(500) COMMENT '主要支出类别(JSON数组)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_month (user_id, year_month),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每月财务趋势表';
2. 高级分析表设计
(1) 支出分类分析
sql
CREATE TABLE category_spending_analysis (
    analysis_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    category_id INT NOT NULL COMMENT '类别ID',
    time_period ENUM('daily', 'weekly', 'monthly', 'quarterly', 'yearly') NOT NULL COMMENT '分析周期',
    period_start DATE NOT NULL COMMENT '周期开始日期',
    period_end DATE NOT NULL COMMENT '周期结束日期',
    total_amount DECIMAL(15,2) DEFAULT 0 COMMENT '总金额',
    transaction_count INT DEFAULT 0 COMMENT '交易次数',
    avg_amount_per_transaction DECIMAL(15,2) DEFAULT 0 COMMENT '平均每笔交易金额',
    amount_percentage DECIMAL(10,2) DEFAULT 0 COMMENT '占该周期总支出百分比',
    comparison_with_prev_period DECIMAL(10,2) COMMENT '与上周期比较变化(%)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_period (user_id, time_period, period_start),
    INDEX idx_user_category (user_id, category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支出分类分析表';
(2) 预算执行分析
sql
CREATE TABLE budget_execution_analysis (
    analysis_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    budget_id INT NOT NULL COMMENT '预算ID',
    time_period VARCHAR(20) NOT NULL COMMENT '预算周期描述',
    period_start DATE NOT NULL COMMENT '周期开始日期',
    period_end DATE NOT NULL COMMENT '周期结束日期',
    budgeted_amount DECIMAL(15,2) NOT NULL COMMENT '预算金额',
    actual_amount DECIMAL(15,2) DEFAULT 0 COMMENT '实际金额',
    remaining_amount DECIMAL(15,2) GENERATED ALWAYS AS (budgeted_amount - actual_amount) STORED COMMENT '剩余金额',
    execution_rate DECIMAL(10,2) GENERATED ALWAYS AS (
        CASE WHEN budgeted_amount > 0 THEN (actual_amount / budgeted_amount) * 100 ELSE 0 END
    ) STORED COMMENT '执行率(%)',
    days_remaining INT COMMENT '剩余天数',
    daily_average_needed DECIMAL(15,2) GENERATED ALWAYS AS (
        CASE WHEN days_remaining > 0 THEN remaining_amount / days_remaining ELSE 0 END
    ) STORED COMMENT '每日平均需花费金额',
    status ENUM('under_budget', 'on_budget', 'over_budget', 'at_risk')
        GENERATED ALWAYS AS (
            CASE
                WHEN execution_rate < 80 THEN 'under_budget'
                WHEN execution_rate BETWEEN 80 AND 100 THEN 'on_budget'
                WHEN execution_rate > 100 AND remaining_amount >= 0 THEN 'over_budget'
                ELSE 'at_risk'
            END
        ) STORED COMMENT '预算状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_budget (user_id, budget_id),
    INDEX idx_user_period (user_id, period_start)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预算执行分析表';
(3) 现金流预测
sql
CREATE TABLE cash_flow_projections (
    projection_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    projection_date DATE NOT NULL COMMENT '预测日期',
    projection_type ENUM('daily', 'weekly', 'monthly') NOT NULL COMMENT '预测类型',
    days_ahead INT NOT NULL COMMENT '预测天数',
    projected_balance DECIMAL(15,2) DEFAULT 0 COMMENT '预测余额',
    projected_income DECIMAL(15,2) DEFAULT 0 COMMENT '预测收入',
    projected_expense DECIMAL(15,2) DEFAULT 0 COMMENT '预测支出',
    confidence_level DECIMAL(5,2) DEFAULT 80.00 COMMENT '置信水平(%)',
    data_source VARCHAR(50) COMMENT '数据来源',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_date (user_id, projection_date),
    INDEX idx_user_type (user_id, projection_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='现金流预测表';
3. 看板配置表设计
(1) 用户看板配置
sql
CREATE TABLE dashboard_configurations (
    config_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT '用户ID',
    dashboard_name VARCHAR(50) NOT NULL COMMENT '看板名称',
    layout_type ENUM('grid', 'freeform', 'list') DEFAULT 'grid' COMMENT '布局类型',
    theme_color VARCHAR(20) DEFAULT '#2196F3' COMMENT '主题颜色',
    is_default BOOLEAN DEFAULT FALSE COMMENT '是否默认看板',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_default (user_id, is_default),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='看板配置表';
(2) 看板组件配置
sql
CREATE TABLE dashboard_widgets (
    widget_id INT AUTO_INCREMENT PRIMARY KEY,
    config_id INT NOT NULL COMMENT '看板配置ID',
    widget_type ENUM(
        'income_expense_chart', 'category_pie_chart',
        'budget_progress', 'account_balance',
        'net_worth', 'spending_trend',
        'goal_progress', 'cash_flow_forecast',
        'recent_transactions', 'top_categories'
    ) NOT NULL COMMENT '组件类型',
    widget_title VARCHAR(100) COMMENT '组件标题',
    position_row INT NOT NULL COMMENT '位置行',
    position_col INT NOT NULL COMMENT '位置列',
    width INT NOT NULL COMMENT '宽度(单位:格)',
    height INT NOT NULL COMMENT '高度(单位:格)',
    time_range ENUM('7d', '30d', '90d', '1y', 'all') DEFAULT '30d' COMMENT '时间范围',
    chart_type ENUM('line', 'bar', 'pie', 'donut', 'number', 'table') COMMENT '图表类型',
    display_currency BOOLEAN DEFAULT TRUE COMMENT '是否显示货币',
    data_filter JSON COMMENT '数据过滤条件(JSON)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (config_id) REFERENCES dashboard_configurations(config_id),
    INDEX idx_config (config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='看板组件配置表';









个人复盘笔记模块（每日三省我身）建表语句
以下是为个人复盘笔记模块设计的数据库表结构，包含主表和可能的关联表：

1. 主表：复盘记录表 (daily_reflection)
sql
CREATE TABLE `daily_reflection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `reflection_date` date NOT NULL COMMENT '复盘日期',
  `today_achievements` text COMMENT '今日成就/完成事项',
  `today_mistakes` text COMMENT '今日不足/错误反思',
  `improvement_plan` text COMMENT '改进计划/明日重点',
  `mood_score` tinyint(4) DEFAULT NULL COMMENT '心情评分(1-10)',
  `energy_level` tinyint(4) DEFAULT NULL COMMENT '精力水平(1-10)',
  `insights` text COMMENT '今日感悟',
  `gratitude` text COMMENT '感恩事项',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_date` (`user_id`,`reflection_date`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_reflection_date` (`reflection_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日复盘记录表';
2. 复盘标签表 (reflection_tag)
sql
CREATE TABLE `reflection_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_tag` (`user_id`,`tag_name`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='复盘标签表';
3. 复盘记录标签关联表 (reflection_tag_relation)
sql
CREATE TABLE `reflection_tag_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `reflection_id` bigint(20) NOT NULL COMMENT '复盘记录ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_reflection_tag` (`reflection_id`,`tag_id`),
  KEY `idx_reflection_id` (`reflection_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='复盘记录标签关联表';
4. 复盘附件表 (reflection_attachment)
sql
CREATE TABLE `reflection_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `reflection_id` bigint(20) NOT NULL COMMENT '复盘记录ID',
  `file_url` varchar(500) NOT NULL COMMENT '文件URL',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小(字节)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`),
  KEY `idx_reflection_id` (`reflection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='复盘附件表';





