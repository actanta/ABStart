-- 业务用户表
CREATE TABLE biz_user (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
  username VARCHAR(50) NOT NULL COMMENT '用户名',
  password VARCHAR(255) NOT NULL COMMENT '密码',
  slat VARCHAR(255) NOT NULL COMMENT '盐值',
  session_id VARCHAR(255) NOT NULL COMMENT '会话ID',
  nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
  mobile VARCHAR(20) UNIQUE NOT NULL COMMENT '手机号',
  email VARCHAR(100) UNIQUE NOT NULL COMMENT '邮箱',
  status TINYINT DEFAULT 1 COMMENT '状态（0:禁用 1:启用）',
  last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
  last_login_ip VARCHAR(20) DEFAULT NULL COMMENT '最后登录IP',
  created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  is_deleted TINYINT DEFAULT 0 COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `biz_user_unique_username` (`username`),
  UNIQUE KEY `biz_user_unique_mobile` (`mobile`),
  UNIQUE KEY `biz_user_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
