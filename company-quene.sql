CREATE TABLE `company_quene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鍏徃鍚嶅瓧',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '状态。1表示已完成，0表示未完成',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22161 DEFAULT CHARSET=utf8;
