# Host: localhost:3366  (Version: 8.0.12)
# Date: 2019-09-18 17:02:40
# Generator: MySQL-Front 5.3  (Build 4.13)

/*!40101 SET NAMES utf8 */;

#
# Source for table "tab_head_line"
#

DROP TABLE IF EXISTS `tab_head_line`;
CREATE TABLE `tab_head_line` (
  `line_id` int(100) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1000) DEFAULT NULL,
  `line_link` varchar(2000) NOT NULL,
  `line_img` varchar(2000) NOT NULL,
  `priority` int(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

#
# Data for table "tab_head_line"
#


#
# Source for table "tb_area"
#

DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `priority` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

#
# Data for table "tb_area"
#

INSERT INTO `tb_area` VALUES (1,'东苑',2,NULL,NULL),(2,'西苑',3,NULL,NULL);

#
# Source for table "tb_person_info"
#

DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `profile_img` varchar(1024) DEFAULT NULL,
  `emial` varchar(1024) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:是禁止使用本商城,1:是允许使用本商城',
  `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:是顾客,2:是店家,3:是超级管理员',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

#
# Data for table "tb_person_info"
#

INSERT INTO `tb_person_info` VALUES (1,'测试','test','test','1',1,2,NULL,NULL);

#
# Source for table "tb_local_auth"
#

DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  KEY `fk_local_profile` (`user_id`),
  CONSTRAINT `fk_local_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

#
# Data for table "tb_local_auth"
#


#
# Source for table "tb_product"
#

DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `point` int(10) DEFAULT NULL,
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_shop` (`shop_id`),
  KEY `fk_product_procate` (`product_category_id`),
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

#
# Data for table "tb_product"
#

INSERT INTO `tb_product` VALUES (17,'测试商品1','测试商品1描述','/shop/html','10.0','8.0',1,'2019-08-28 07:52:20',NULL,1,NULL,2,1),(18,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917024337566.jpg','10.0','8.0',1,'2019-08-29 09:02:43','2019-08-29 09:02:43',1,NULL,NULL,1),(19,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917072494180.jpg','10.0','8.0',1,'2019-08-29 09:07:24','2019-08-29 09:07:24',1,NULL,NULL,1),(20,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917230184133.jpg','10.0','8.0',1,'2019-08-29 09:23:01','2019-08-29 09:23:01',1,NULL,NULL,1),(21,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917244017547.jpg','10.0','8.0',1,'2019-08-29 09:24:40','2019-08-29 09:24:40',1,NULL,NULL,1),(22,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917273417962.jpg','10.0','8.0',1,'2019-08-29 09:27:35','2019-08-29 09:27:35',1,NULL,NULL,1),(23,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917290123889.jpg','10.0','8.0',1,'2019-08-29 09:29:02','2019-08-29 09:29:02',1,NULL,NULL,1),(24,'测试商品2','测试商品2描述','upload\\item\\shop\\1\\2019082917291924576.jpg','10.0','8.0',1,'2019-08-29 09:29:19','2019-08-29 09:29:19',1,NULL,NULL,1),(26,'测试名称',NULL,'upload\\item\\shop\\2\\2019091017044438761.jpg','100','80',111,'2019-09-10 09:04:39','2019-09-10 09:04:39',1,NULL,34,2),(27,'测试名称',NULL,'upload\\item\\shop\\2\\2019091017061328033.jpg','100','80',111,'2019-09-10 09:06:13','2019-09-10 09:06:13',1,NULL,34,2);

#
# Source for table "tb_product_img"
#

DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img` (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_proimg_product` (`product_id`),
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

#
# Data for table "tb_product_img"
#

INSERT INTO `tb_product_img` VALUES (38,'/shop/s','详情图片',1,'2019-08-28 08:22:21',17),(39,'/shop/s1','详情图片1',2,'2019-08-28 08:22:21',17),(42,'upload\\item\\shop\\1\\2019082917024319163.jpg',NULL,NULL,'2019-08-29 09:02:43',18),(43,'upload\\item\\shop\\1\\2019082917024340524.jpg',NULL,NULL,'2019-08-29 09:02:43',18),(44,'upload\\item\\shop\\1\\2019082917072417192.jpg',NULL,NULL,'2019-08-29 09:07:24',19),(45,'upload\\item\\shop\\1\\2019082917072410156.jpg',NULL,NULL,'2019-08-29 09:07:24',19),(46,'upload\\item\\shop\\1\\2019082917230166708.jpg',NULL,NULL,'2019-08-29 09:23:01',20),(47,'upload\\item\\shop\\1\\2019082917230178435.jpg',NULL,NULL,'2019-08-29 09:23:01',20),(48,'upload\\item\\shop\\1\\2019082917244081391.jpg',NULL,NULL,'2019-08-29 09:24:40',21),(49,'upload\\item\\shop\\1\\2019082917244074629.jpg',NULL,NULL,'2019-08-29 09:24:40',21),(50,'upload\\item\\shop\\1\\2019082917273523283.jpg',NULL,NULL,'2019-08-29 09:27:35',22),(51,'upload\\item\\shop\\1\\2019082917273535544.jpg',NULL,NULL,'2019-08-29 09:27:35',22),(52,'upload\\item\\shop\\1\\2019082917290230002.jpg',NULL,NULL,'2019-08-29 09:29:02',23),(53,'upload\\item\\shop\\1\\2019082917290279130.jpg',NULL,NULL,'2019-08-29 09:29:02',23),(54,'upload\\item\\shop\\1\\2019082917291912620.jpg',NULL,NULL,'2019-08-29 09:29:19',24),(55,'upload\\item\\shop\\1\\2019082917291956940.jpg',NULL,NULL,'2019-08-29 09:29:19',24),(58,'upload\\item\\shop\\2\\2019091017044620968.jpg',NULL,NULL,'2019-09-10 09:04:46',26),(59,'upload\\item\\shop\\2\\2019091017044620314.jpg',NULL,NULL,'2019-09-10 09:04:46',26),(60,'upload\\item\\shop\\2\\2019091017061340265.jpg',NULL,NULL,'2019-09-10 09:06:13',27),(61,'upload\\item\\shop\\2\\2019091017061352040.jpg',NULL,NULL,'2019-09-10 09:06:14',27);

#
# Source for table "tb_shop_category"
#

DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) DEFAULT '',
  `shop_category_img` varchar(2000) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_self` (`parent_id`),
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

#
# Data for table "tb_shop_category"
#

INSERT INTO `tb_shop_category` VALUES (1,'咖啡奶茶','咖啡奶茶','test',1,NULL,NULL,NULL),(34,'咖啡','咖啡','test1',0,NULL,NULL,1),(35,'零食小吃','零食小吃','test2',2,NULL,NULL,NULL);

#
# Source for table "tb_shop"
#

DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `area_id` int(5) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `parent_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `shop_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `longitude` double(16,12) DEFAULT NULL,
  `latitude` double(16,12) DEFAULT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_shopcate` (`shop_category_id`),
  KEY `fk_shop_parentcate` (`parent_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_parentcate` FOREIGN KEY (`parent_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#
# Data for table "tb_shop"
#

INSERT INTO `tb_shop` VALUES (1,1,2,1,NULL,'测试的店铺3','test3','test3','test3','upload\\item\\shop\\54\\2019070916372973923.jpg',NULL,NULL,NULL,'2019-07-04 09:24:00','2019-07-09 08:37:29',1,'审核通过'),(2,1,1,1,NULL,'测试1','测','地址','11','upload\\item\\shop\\60\\2019071917543124918.jpg',NULL,NULL,0,'2019-08-14 09:34:37','2019-08-14 09:35:43',1,'审核通过'),(3,1,2,34,NULL,'测试店铺2','测','address','1324444455','upload\\item\\shop\\60\\2019071917543124918.jpg',NULL,NULL,0,'2019-08-15 02:39:03','2019-08-15 02:39:05',0,'审核中'),(60,1,2,1,NULL,'咖啡店','测','地址','12345678909','upload\\item\\shop\\60\\2019071917543124918.jpg',NULL,NULL,NULL,'2019-07-18 09:48:34','2019-07-19 09:54:32',0,'审核中'),(61,1,2,35,NULL,'ceshi','ces','ceshi','12345678900','upload\\item\\shop\\61\\2019090314591775236.jpg',NULL,NULL,NULL,'2019-09-03 06:59:17','2019-09-03 06:59:17',0,NULL),(62,1,1,35,NULL,'ceshi1','ces1','ceshi1','12345678900','upload\\item\\shop\\62\\2019090314594782882.jpg',NULL,NULL,NULL,'2019-09-03 06:59:48','2019-09-03 06:59:48',0,NULL),(63,1,2,35,NULL,'ce','ce','ce','111111111','upload\\item\\shop\\63\\2019090316251241021.jpg',NULL,NULL,NULL,'2019-09-03 08:25:12','2019-09-03 08:25:12',0,NULL),(64,1,2,1,NULL,'111','4444','2222','3333','upload\\item\\shop\\64\\2019090413485172633.jpg',NULL,NULL,NULL,'2019-09-04 05:48:52','2019-09-04 05:48:52',0,NULL),(65,1,2,1,NULL,'111','4444','2222','3333','upload\\item\\shop\\65\\2019090413520275105.jpg',NULL,NULL,NULL,'2019-09-04 05:52:02','2019-09-04 05:52:02',0,NULL),(66,1,2,1,NULL,'111','4444','2222','3333','upload\\item\\shop\\66\\2019090413552230530.jpg',NULL,NULL,NULL,'2019-09-04 05:55:22','2019-09-04 05:55:22',0,NULL),(67,1,2,35,NULL,'99','99','99','99','upload\\item\\shop\\67\\2019090417072474613.jpg',NULL,NULL,NULL,'2019-09-04 09:07:25','2019-09-04 09:07:25',0,NULL),(68,1,2,35,NULL,'99','99','99','99','upload\\item\\shop\\68\\2019090417092366503.jpg',NULL,NULL,NULL,'2019-09-04 09:09:22','2019-09-04 09:09:22',0,NULL),(69,1,2,35,NULL,'33','3','3','3','upload\\item\\shop\\69\\2019090417130022207.jpg',NULL,NULL,NULL,'2019-09-04 09:13:01','2019-09-04 09:13:01',0,NULL),(70,1,2,35,NULL,'88','88','88','88','upload\\item\\shop\\70\\2019090417181596558.jpg',NULL,NULL,NULL,'2019-09-04 09:18:16','2019-09-04 09:18:16',0,NULL),(71,1,2,35,NULL,'88111','88','88','88','upload\\item\\shop\\71\\2019090417283918161.jpg',NULL,NULL,NULL,'2019-09-04 09:28:39','2019-09-04 09:28:39',0,NULL),(72,1,2,35,NULL,'6969','36','66','99','upload\\item\\shop\\72\\2019090417345964346.jpg',NULL,NULL,NULL,'2019-09-04 09:35:00','2019-09-04 09:35:00',0,NULL),(73,1,2,35,NULL,'2323','2','2','2','upload\\item\\shop\\73\\2019090417374766746.jpg',NULL,NULL,NULL,'2019-09-04 09:37:48','2019-09-04 09:37:48',0,NULL),(74,1,2,35,NULL,'678','678','678','678','upload\\item\\shop\\74\\2019090510220316914.jpg',NULL,NULL,NULL,'2019-09-05 02:22:03','2019-09-05 02:22:03',0,NULL),(75,1,2,35,NULL,'123','987','456','789','upload\\item\\shop\\75\\2019090510251292600.jpg',NULL,NULL,NULL,'2019-09-05 02:25:12','2019-09-05 02:25:12',0,NULL),(76,1,2,35,NULL,'测试店铺1','测试测试测试','测试地那普','12345678900','upload\\item\\shop\\76\\2019090915580562139.jpg',NULL,NULL,NULL,'2019-09-09 07:58:06','2019-09-09 07:58:06',0,NULL);

#
# Source for table "tb_product_category"
#

DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) NOT NULL,
  `product_category_desc` varchar(500) DEFAULT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_category_id`),
  KEY `fk_procate_shop` (`shop_id`),
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

#
# Data for table "tb_product_category"
#

INSERT INTO `tb_product_category` VALUES (1,'店铺分类测试1','店铺分类测试1',1,'2019-08-20 08:31:13','2019-08-20 08:31:16',1),(2,'店铺分类测试2','店铺分类测试2',2,'2019-08-20 08:31:35','2019-08-20 08:31:37',1),(3,'店铺分类测试3','店铺分类测试3',3,'2019-08-20 08:32:06','2019-08-20 08:32:08',1),(5,'店铺分类测试5','店铺分类测试5',5,'2019-08-20 08:33:13','2019-08-20 08:33:15',3),(29,'测试1',NULL,10,NULL,NULL,1),(32,'1123',NULL,1,'1970-01-01 00:00:00',NULL,2),(34,'测试',NULL,1,'1970-01-01 00:00:00',NULL,2);

#
# Source for table "tb_wechat_auth"
#

DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `open_id` varchar(300) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `fk_wechat_profile` (`user_id`),
  CONSTRAINT `fk_wechat_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

#
# Data for table "tb_wechat_auth"
#

