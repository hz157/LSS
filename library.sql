/*
 Navicat Premium Data Transfer

 Source Server         : db
 Source Server Type    : MySQL
 Source Server Version : 50651 (5.6.51-log)
 Source Host           : 150.158.146.225:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 50651 (5.6.51-log)
 File Encoding         : 65001

 Date: 10/01/2023 02:11:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lss_activity
-- ----------------------------
DROP TABLE IF EXISTS `lss_activity`;
CREATE TABLE `lss_activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `poster` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动海报',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动地址',
  `peopleLimit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '人数限制',
  `registrationDate` datetime NOT NULL COMMENT '报名开始时间',
  `deadline` datetime NULL DEFAULT NULL COMMENT '截至时间',
  `startDate` datetime NOT NULL COMMENT '活动开始时间',
  `user` bigint(20) NOT NULL COMMENT '归属用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  CONSTRAINT `lss_activity_ibfk_1` FOREIGN KEY (`user`) REFERENCES `lss_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 795 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_activityrecord
-- ----------------------------
DROP TABLE IF EXISTS `lss_activityrecord`;
CREATE TABLE `lss_activityrecord`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录编码',
  `activity` bigint(20) NULL DEFAULT NULL COMMENT '活动编码',
  `user` bigint(20) NULL DEFAULT NULL COMMENT '参加用户',
  `registrationDate` datetime NULL DEFAULT NULL COMMENT '报名日期',
  `attend` int(11) NULL DEFAULT NULL COMMENT '参加状态',
  `SignDate` datetime NULL DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity`(`activity`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  CONSTRAINT `lss_activityrecord_ibfk_1` FOREIGN KEY (`activity`) REFERENCES `lss_activity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lss_activityrecord_ibfk_2` FOREIGN KEY (`user`) REFERENCES `lss_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_activitysigncode
-- ----------------------------
DROP TABLE IF EXISTS `lss_activitysigncode`;
CREATE TABLE `lss_activitysigncode`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到码ID',
  `activity` bigint(20) NOT NULL COMMENT '关联活动',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '签到码',
  `deadline` datetime NULL DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity`(`activity`) USING BTREE,
  CONSTRAINT `lss_activitysigncode_ibfk_1` FOREIGN KEY (`activity`) REFERENCES `lss_activity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_book
-- ----------------------------
DROP TABLE IF EXISTS `lss_book`;
CREATE TABLE `lss_book`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'isbn号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `subname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '副书名',
  `photoUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '介绍',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `authorIntro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '作者介绍',
  `translator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '译者',
  `publishing` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `published` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出版年',
  `pages` int(11) NULL DEFAULT NULL COMMENT '页数',
  `doubanScore` int(11) NULL DEFAULT NULL COMMENT '豆瓣分数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1600374391502610435 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_borrow
-- ----------------------------
DROP TABLE IF EXISTS `lss_borrow`;
CREATE TABLE `lss_borrow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借阅记录编号',
  `book` bigint(20) NOT NULL COMMENT '图书ID',
  `user` bigint(20) NOT NULL COMMENT '借阅人ID',
  `borrowingDate` datetime NOT NULL COMMENT '借阅日期',
  `returnDate` datetime NULL DEFAULT NULL COMMENT '归还日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_history
-- ----------------------------
DROP TABLE IF EXISTS `lss_history`;
CREATE TABLE `lss_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isbn` bigint(20) NOT NULL,
  `user` bigint(20) NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `isbn`(`isbn`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  CONSTRAINT `lss_history_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `lss_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lss_history_ibfk_2` FOREIGN KEY (`user`) REFERENCES `lss_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_stock
-- ----------------------------
DROP TABLE IF EXISTS `lss_stock`;
CREATE TABLE `lss_stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` bigint(20) NOT NULL COMMENT '图书编号',
  `user` bigint(20) NOT NULL COMMENT '用户编号',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user`(`user`) USING BTREE,
  INDEX `isbn`(`isbn`) USING BTREE,
  CONSTRAINT `lss_stock_ibfk_1` FOREIGN KEY (`user`) REFERENCES `lss_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lss_stock_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `lss_book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_user
-- ----------------------------
DROP TABLE IF EXISTS `lss_user`;
CREATE TABLE `lss_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `role` int(11) NOT NULL DEFAULT 1 COMMENT '用户权限',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tel`(`tel`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100410 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for lss_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `lss_userinfo`;
CREATE TABLE `lss_userinfo`  (
  `id` bigint(20) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `actual` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `sex` int(11) NOT NULL COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `mail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `idcard` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证',
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `integral` double NOT NULL DEFAULT 0,
  UNIQUE INDEX `idcard`(`idcard`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `lss_userinfo_ibfk_1` FOREIGN KEY (`id`) REFERENCES `lss_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
