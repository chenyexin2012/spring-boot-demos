create database sharding_sphere_demo_0;
create database sharding_sphere_demo_1;
create database sharding_sphere_demo_2;


DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
	`id` bigint(20) NOT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
	`gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
	`address` varchar(128) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
	`status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

DROP TABLE IF EXISTS `t_user_0`;
CREATE TABLE `t_user_0`  (
	`id` bigint(20) NOT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
	`gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
	`address` varchar(128) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0`  (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
	`status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

DROP TABLE IF EXISTS `t_user_1`;
CREATE TABLE `t_user_1`  (
	`id` bigint(20) NOT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
	`gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
	`address` varchar(128) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`  (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
	`status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';


DROP TABLE IF EXISTS `t_user_2`;
CREATE TABLE `t_user_2`  (
	`id` bigint(20) NOT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
	`gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
	`address` varchar(128) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2`  (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
	`status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

DROP TABLE IF EXISTS `t_user_3`;
CREATE TABLE `t_user_3`  (
	`id` bigint(20) NOT NULL,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
	`gender` int(1) DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `phone` varchar(64) DEFAULT NULL COMMENT '手机号码',
	`address` varchar(128) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `t_order_3`;
CREATE TABLE `t_order_3`  (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
	`status` int(1) DEFAULT NULL COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';
