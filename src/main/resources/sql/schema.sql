DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `file_md5` varchar(50) DEFAULT NULL COMMENT '文件MD5',
  `type` int(11) DEFAULT NULL COMMENT '文件类型',
  `full_file_name` varchar(50) DEFAULT NULL COMMENT '存在服务器端的名称',
  `origin_file_name` varchar(50) DEFAULT NULL COMMENT '源文件名带后缀',
  `file_name` varchar(50) DEFAULT NULL COMMENT '原名称',
  `encoding` varchar(255) DEFAULT NULL,
  `suffix_name` varchar(50) DEFAULT NULL COMMENT '原后缀',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `del_tag` varchar(1) NOT NULL,
  `conversion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_md5` (`file_md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for project_info
-- ----------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) DEFAULT NULL COMMENT '项目名称',
  `project_desc` varchar(50) DEFAULT NULL COMMENT '项目描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(20) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `type` int(11) DEFAULT NULL COMMENT '用户类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `del_tag` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;