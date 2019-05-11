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

 Date: 11/05/2019 07:31:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for website_config
-- ----------------------------
DROP TABLE IF EXISTS `website_config`;
CREATE TABLE `website_config`  (
  `website_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否删除，1表示删除，0表示未删除',
  `website` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站的根目录对应的正则表达式  此表达式应设置为能识别所有为此网站页面的链接地址，且应唯一。',
  `website_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站名字',
  `website_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站首页，即爬虫开始的地方',
  `novel_detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说详情页的URL规则，若为空则表示没有详情页，如为空，下面的标题、作者、简介为从文中提取的正则表达式',
  `novel_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说标题的jsoup选择器规则',
  `novel_title_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说标题的删除内容（正则）',
  `novel_author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说作者的jsoup选择器',
  `novel_author_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说作者需要删除的内容（正则）',
  `novel_state` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说状态所在的jsoup选择器',
  `novel_state_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态文本中需要删除的内容',
  `novel_state_end` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除之后，如果是完结对应的字符串',
  `novel_state_not_end` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除之后，如果未完结时对应的字符串',
  `novel_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说简介的选择器',
  `novel_description_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说简介删除内容（正则）',
  `novel_category` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站分类对应的jsoup选择器',
  `novel_category_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类中要删除的内容',
  `novel_catalog_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说目录页的地址规则，若为空，表示没有目录页。如没有目录页，则下列所有选项无效，对应的章节名从文中解析',
  `novel_catalog_type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '小说目录页是否分页 0表示不分页，1表示分页',
  `novel_catalog_next` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当目录页是分页显示时，此项有效，表示下一页连接的选择器',
  `novel_catalog_next_text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当目录页是分页显示时，此项有效，表示下一页连接选择所在的属性值，空表示选择文本',
  `novel_catalog_list` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目录页中的章节列表所在的位置',
  `novel_catalog_list_text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目录中章节列表连接的属性值',
  `novel_chapter_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说内容页的url规则',
  `novel_chapter_title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '如果没有目录页，此项有效，表示小说的章节名在文中对应的正则式，此时需要自行截取，并将其存储为文本文件',
  `novel_chapter_title_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说章节名的删除内容',
  `novel_content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说文章内容所在的位置',
  `novel_content_replace` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说文章中需要删除的内容',
  `novel_content_type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '小说内容页是否分页 0表示不分页，1表示分页',
  `novel_content_next` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当小说内容页是分页显示时，此项有效。内容页中的下一页所在位置',
  `novel_content_next_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当小说内容页分页显示时，此项有效。小说内容页的链接规则',
  `novel_content_next_text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说分页显示时，小说下一页连接所在属性值',
  `novel_chapter_next` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说内容页中下一章所在位置',
  `novel_chapter_next_type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '当小说内容页分页显示时此项有效。表示下一页与下一章所在位置是否相同。0表示不同，1表示相同',
  `novel_chapter_next_text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说下一章连接所在的属性值，空表示选择文本',
  PRIMARY KEY (`website_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of website_config
-- ----------------------------
INSERT INTO `website_config` VALUES (9, '2019-04-21 00:00:00', '2019-04-21 00:00:00', 0, 'https://m.biquge.info/?.*', '笔趣阁：biquge.info', 'https://m.biquge.info/', 'https://m.biquge.info/\\d+_\\d+/?$', 'html > body > div.cover > div.block > div.block_txt2 > h2 > a', '', 'html > body > div.cover > div.block > div.block_txt2 > p:eq(3)', '作者：', 'html > body > div.cover > div.block > div.block_txt2 > p:eq(5)', '状态：', '完本', '连载中', 'html > body > div.cover > div.intro_info', '最新章节推荐地址：.*$', 'html > body > div.cover > div.block > div.block_txt2 > p:eq(4) > a', '', 'https://m.biquge.info/\\d+_\\d+/?(index_\\d+?.html)?$', 1, 'html > body > div.cover > div.listpage > span.right > a.onclick', 'href', 'html > body > div.cover > ul.chapter:last-of-type > li > a', 'href', 'https://m.biquge.info/\\d+_\\d+$/\\d+\\.html$', 'html > body > header > div.zhong', '', 'html > body > section.zj > articll#nr', '', 0, '', '', '', 'html > body > section.zj > div:last-of-type > a.dise:eq(2)', 0, 'href');
INSERT INTO `website_config` VALUES (10, '2019-04-21 00:00:00', '2019-04-21 00:00:00', 0, 'http://m.biqugew.com/?.*', '笔趣阁：biqugew.com', 'http://m.biqugew.com/', 'http://m.biqugew.com/book/\\d+/?$', 'html > body > div.cover > div.block > div.block_txt2 > h2 > a', '', 'html > body > div.cover > div.block > div.block_txt2 > p:eq(3) > a', '', 'html > body > div.cover > div.block > div.block_txt2 > p:eq(5)', '状态：', '已完结', '连载中', 'html > body > div.cover > div.intro_info', '', 'html > body > div.cover > div.block > div.block_txt2 > p:eq(4) > a', '', 'http://m.biqugew.com/0/\\d+(_\\d+)?/?$', 1, 'html > body > div.page > a:matchesOwn(下一页)', '', 'html > body > div.cover > ul.chapter > li > a', 'href', 'http://m.biqugew.com/book/\\d+/\\d+\\.html$', 'html > body > div > div#nr_title', '（第\\d+/\\d+页）', 'html > body > div > div#nr > div#nr1', '\n\n本章未完，请点击下一页继续阅读》》', 1, 'html > body > div > div > table > tbody > tr > td.next > a#pb_next', 'http://m.biqugew.com/book/\\d+/\\d+_\\d+\\.html$', '', 'html > body > div > div > table > tbody > tr > td.next > a#pb_next', 0, 'href');

SET FOREIGN_KEY_CHECKS = 1;
