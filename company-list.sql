CREATE TABLE `company_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `quene_id` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '状态，0代表未处理，1代表已处理',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70910 DEFAULT CHARSET=utf8;

