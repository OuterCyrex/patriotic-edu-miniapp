/*
 Navicat Premium Data Transfer

 Source Server         : windows
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : localhost:3306
 Source Schema         : edu

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 31/05/2025 16:23:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for defense_voice
-- ----------------------------
DROP TABLE IF EXISTS `defense_voice`;
CREATE TABLE `defense_voice`  (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '心声内容',
                                `region` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地区',
                                `identity` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份',
                                `author_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者姓名',
                                `theme` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题分类',
                                `likes_count` int NULL DEFAULT 0 COMMENT '点赞数',
                                `comments_count` int NULL DEFAULT 0 COMMENT '评论数',
                                `is_featured` tinyint NULL DEFAULT 0 COMMENT '是否精选：1是 0否',
                                `status` tinyint NULL DEFAULT 0 COMMENT '审核状态：0待审核 1已通过 2已拒绝',
                                `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                `gmt_create` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '国防心声表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of defense_voice
-- ----------------------------

-- ----------------------------
-- Table structure for hero
-- ----------------------------
DROP TABLE IF EXISTS `hero`;
CREATE TABLE `hero`  (
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '英雄姓名',
                       `period` tinyint NOT NULL COMMENT '历史时期：1新民主主义革命先驱 2建设年代守护者 3改革浪潮弄潮儿 4强国先锋时代篇',
                       `period_years` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '时期年份',
                       `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '称号/职务',
                       `famous_quote` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '名言',
                       `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '英雄事迹摘要',
                       `story` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '英雄事迹',
                       `sacrifice_year` year NULL DEFAULT NULL COMMENT '牺牲年份',
                       `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片链接',
                       `sort_order` int NULL DEFAULT 0 COMMENT '排序',
                       `status` tinyint NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
                       `gmt_create` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                       `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
                       `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
                       `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除(0：未删除 1：已删除)',
                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '英雄人物表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hero
-- ----------------------------

-- ----------------------------
-- Table structure for knowledge_question
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_question`;
CREATE TABLE `knowledge_question`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目',
                                     `option_a` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项A',
                                     `option_b` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项B',
                                     `option_c` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项C',
                                     `option_d` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项D',
                                     `correct_answer` tinyint NOT NULL COMMENT '正确答案：1-A 2-B 3-C 4-D',
                                     `explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '答案解析',
                                     `difficulty` tinyint NULL DEFAULT 1 COMMENT '难度等级：1简单 2中等 3困难',
                                     `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '基础知识' COMMENT '题目分类',
                                     `status` tinyint NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
                                     `gmt_create` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                                     `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
                                     `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
                                     `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除(0：未删除 1：已删除)',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '国防知识题库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of knowledge_question
-- ----------------------------

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `user_id` bigint NOT NULL,
                        `target_type` tinyint NOT NULL COMMENT '点赞目标类型：1心声 2评论',
                        `target_id` bigint NOT NULL COMMENT '目标ID',
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE INDEX `unique_like`(`user_id` ASC, `target_type` ASC, `target_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点赞记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes
-- ----------------------------

-- ----------------------------
-- Table structure for scenario_question
-- ----------------------------
DROP TABLE IF EXISTS `scenario_question`;
CREATE TABLE `scenario_question`  (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `scenario` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '情景描述',
                                    `question` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题',
                                    `option_a` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项A',
                                    `option_b` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项B',
                                    `option_c` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '选项C',
                                    `correct_answer` tinyint NOT NULL COMMENT '正确答案：1-A 2-B 3-C',
                                    `legal_basis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '法律依据',
                                    `solution` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '应对方法',
                                    `status` tinyint NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
                                    `gmt_create` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                                    `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
                                    `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
                                    `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除(0：未删除 1：已删除)',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '情景问答表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scenario_question
-- ----------------------------

-- ----------------------------
-- Table structure for service_booking
-- ----------------------------
DROP TABLE IF EXISTS `service_booking`;
CREATE TABLE `service_booking`  (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `organization_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位/学校名称',
                                  `contact_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人',
                                  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系电话',
                                  `contact_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系邮箱',
                                  `service_type` tinyint NOT NULL COMMENT '需求类型',
                                  `preferred_time` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '期望时间',
                                  `participant_count` int NULL DEFAULT NULL COMMENT '参与人数',
                                  `venue_address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '场地地址',
                                  `special_requirements` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '特殊需求',
                                  `matched_heroes` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '匹配的英雄人物',
                                  `status` tinyint NULL DEFAULT 0 COMMENT '状态：0待处理 1已确认 2已完成 3已取消',
                                  `admin_notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '管理员备注',
                                  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `idx_status_created`(`status` ASC, `create_by` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_booking
-- ----------------------------

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`  (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `daily_quiz_count` int NULL DEFAULT 10 COMMENT '每日答题数量',
                                `stars_per_correct` int NULL DEFAULT 1 COMMENT '答对一题获得星星数',
                                `hotline_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '400-1234-5678' COMMENT '服务热线电话',
                                `voice_max_length` int NULL DEFAULT 300 COMMENT '心声内容最大字符数',
                                `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表（单行）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES (1, 10, 1, '400-1234-5678', 300, '2025-05-30 21:49:43');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
                       `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
                       `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
                       `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                       `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像链接',
                       `region` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区',
                       `type` tinyint NOT NULL COMMENT '用户类型(0：学生 1：管理员)',
                       `total_stars` int NULL DEFAULT 0 COMMENT '总星星勋章数',
                       `status` tinyint NULL DEFAULT 1 COMMENT '状态：1正常 0禁用',
                       `gmt_create` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                       `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       PRIMARY KEY (`id`) USING BTREE,
                       UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_record
-- ----------------------------
DROP TABLE IF EXISTS `user_record`;
CREATE TABLE `user_record`  (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `user_id` bigint NOT NULL,
                              `question_id` bigint NOT NULL,
                              `question_type` tinyint NOT NULL COMMENT '题目类型：1知识题 2情景题',
                              `user_answer` tinyint NOT NULL COMMENT '用户答案：1-A 2-B 3-C 4-D',
                              `is_correct` tinyint NOT NULL COMMENT '是否正确：1正确 0错误',
                              `stars_earned` int NULL DEFAULT 0 COMMENT '获得星星数',
                              `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户答题记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_record
-- ----------------------------

-- ----------------------------
-- Table structure for voice_comment
-- ----------------------------
DROP TABLE IF EXISTS `voice_comment`;
CREATE TABLE `voice_comment`  (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `voice_id` bigint NOT NULL,
                                `user_id` bigint NOT NULL,
                                `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
                                `parent_id` bigint NULL DEFAULT 0 COMMENT '父评论ID，0为顶级评论',
                                `likes_count` int NULL DEFAULT 0 COMMENT '点赞数',
                                `status` tinyint NULL DEFAULT 1 COMMENT '状态：1显示 0隐藏',
                                `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '心声评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of voice_comment
-- ----------------------------

-- ----------------------------
-- Table structure for word
-- ----------------------------
DROP TABLE IF EXISTS `word`;
CREATE TABLE `word`  (
                       `id` bigint NOT NULL AUTO_INCREMENT,
                       `word` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '词汇',
                       `count` int NULL DEFAULT 1 COMMENT '出现次数',
                       `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
                       `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
                       `gmt_create` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       `gmt_modified` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       `is_deleted` tinyint(1) NULL DEFAULT 0,
                       PRIMARY KEY (`id`) USING BTREE,
                       UNIQUE INDEX `unique_word`(`word` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '词云统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of word
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
