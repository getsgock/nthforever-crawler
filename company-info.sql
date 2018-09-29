CREATE TABLE `company_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registration_id` varchar(20) NOT NULL COMMENT '公司注册号',
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司名称',
  `oper` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司法人',
  `phone` varchar(14) DEFAULT NULL COMMENT '电话或者手机',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `credit_num` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `registered_capital` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '注册资本',
  `company_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业类型',
  `company_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业状态',
  `create_date` date DEFAULT NULL COMMENT '成立日期',
  `range` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '经营范围',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司住所',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`,`registration_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64458 DEFAULT CHARSET=utf8;

