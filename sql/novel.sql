/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云-Mysql-root
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 39.96.170.153:3306
 Source Schema         : book

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 11/05/2019 07:30:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for novel
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel`  (
  `novel_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `title` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '小说标题',
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说作者',
  `category` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说分类',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '小说简介',
  `is_end` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '是否完结，1表示已完结，0表示未完结',
  `is_delete` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否删除，0表示未删除，1表示删除',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`novel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38005 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
