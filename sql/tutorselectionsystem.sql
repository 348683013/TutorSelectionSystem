/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : tutorselectionsystem

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 05/10/2022 20:10:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员主键id',
  `account_number` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `admin_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录凭证',
  `is_lock` tinyint(1) NULL DEFAULT 1 COMMENT '是否上锁，0是上锁，默认是1',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', 'admin', '管理员1', '27e661d5-9964-4586-997d-714b0e5b2b10', 1, 0);
INSERT INTO `t_admin` VALUES (2, '111111', '111111', '管理员2', 'a90b2a3e-5945-47e5-8a23-6684ce6a0b4e', 1, 0);

-- ----------------------------
-- Table structure for t_requests
-- ----------------------------
DROP TABLE IF EXISTS `t_requests`;
CREATE TABLE `t_requests`  (
  `request_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` bigint(19) NULL DEFAULT NULL COMMENT '学生id',
  `student_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '老师id',
  `teacher_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '老师姓名',
  `send_time` datetime NULL DEFAULT NULL COMMENT '请求发送时间',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '请求状态，0未处理，1已同意，2为已拒绝',
  `round_id` bigint(19) NULL DEFAULT NULL COMMENT '第几轮id',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`request_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 220 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_requests
-- ----------------------------
INSERT INTO `t_requests` VALUES (161, 121, '约翰1', 22, '紫霞仙子1', '2022-10-05 19:43:12', 2, 1, 0);
INSERT INTO `t_requests` VALUES (162, 121, '约翰1', 24, '紫霞仙子2', '2022-10-05 19:43:12', 2, 1, 0);
INSERT INTO `t_requests` VALUES (163, 121, '约翰1', 26, '紫霞仙子2', '2022-10-05 19:43:12', 2, 1, 0);
INSERT INTO `t_requests` VALUES (164, 121, '约翰1', 27, '白骨精2', '2022-10-05 19:43:12', 2, 1, 0);
INSERT INTO `t_requests` VALUES (165, 121, '约翰1', 29, '白骨精3', '2022-10-05 19:43:12', 2, 1, 0);
INSERT INTO `t_requests` VALUES (166, 122, '纳什2', 22, '紫霞仙子1', '2022-10-05 19:43:44', 2, 1, 0);
INSERT INTO `t_requests` VALUES (167, 122, '纳什2', 24, '紫霞仙子2', '2022-10-05 19:43:44', 2, 1, 0);
INSERT INTO `t_requests` VALUES (168, 122, '纳什2', 29, '白骨精3', '2022-10-05 19:43:44', 2, 1, 0);
INSERT INTO `t_requests` VALUES (169, 122, '纳什2', 32, '紫霞仙子4', '2022-10-05 19:43:44', 2, 1, 0);
INSERT INTO `t_requests` VALUES (170, 122, '纳什2', 34, '紫霞仙子4', '2022-10-05 19:43:44', 2, 1, 0);
INSERT INTO `t_requests` VALUES (171, 127, '约翰4', 27, '白骨精2', '2022-10-05 19:44:10', 2, 1, 0);
INSERT INTO `t_requests` VALUES (172, 127, '约翰4', 32, '紫霞仙子4', '2022-10-05 19:44:10', 2, 1, 0);
INSERT INTO `t_requests` VALUES (173, 127, '约翰4', 46, '紫霞仙子7', '2022-10-05 19:44:10', 2, 1, 0);
INSERT INTO `t_requests` VALUES (174, 127, '约翰4', 48, '紫霞仙子8', '2022-10-05 19:44:10', 2, 1, 0);
INSERT INTO `t_requests` VALUES (175, 127, '约翰4', 26, '紫霞仙子2', '2022-10-05 19:44:15', 2, 1, 0);
INSERT INTO `t_requests` VALUES (176, 139, '约翰10', 25, '白骨精2', '2022-10-05 19:44:35', 1, 1, 0);
INSERT INTO `t_requests` VALUES (177, 139, '约翰10', 48, '紫霞仙子8', '2022-10-05 19:44:35', 2, 1, 0);
INSERT INTO `t_requests` VALUES (178, 139, '约翰10', 51, '白骨精8', '2022-10-05 19:44:35', 2, 1, 0);
INSERT INTO `t_requests` VALUES (179, 139, '约翰10', 53, '白骨精9', '2022-10-05 19:44:35', 2, 1, 0);
INSERT INTO `t_requests` VALUES (180, 139, '约翰10', 55, '白骨精9', '2022-10-05 19:44:35', 2, 1, 0);
INSERT INTO `t_requests` VALUES (181, 142, '纳什12', 22, '紫霞仙子1', '2022-10-05 19:49:23', 1, 1, 0);
INSERT INTO `t_requests` VALUES (182, 142, '纳什12', 40, '紫霞仙子6', '2022-10-05 19:49:23', 2, 1, 0);
INSERT INTO `t_requests` VALUES (183, 142, '纳什12', 43, '白骨精6', '2022-10-05 19:49:23', 2, 1, 0);
INSERT INTO `t_requests` VALUES (184, 142, '纳什12', 50, '紫霞仙子8', '2022-10-05 19:49:23', 2, 1, 0);
INSERT INTO `t_requests` VALUES (185, 142, '纳什12', 52, '紫霞仙子9', '2022-10-05 19:49:23', 2, 1, 0);
INSERT INTO `t_requests` VALUES (186, 143, '约翰12', 22, '紫霞仙子1', '2022-10-05 19:49:39', 1, 1, 0);
INSERT INTO `t_requests` VALUES (187, 143, '约翰12', 28, '紫霞仙子3', '2022-10-05 19:49:39', 2, 1, 0);
INSERT INTO `t_requests` VALUES (188, 143, '约翰12', 31, '白骨精3', '2022-10-05 19:49:39', 2, 1, 0);
INSERT INTO `t_requests` VALUES (189, 143, '约翰12', 33, '白骨精4', '2022-10-05 19:49:39', 2, 1, 0);
INSERT INTO `t_requests` VALUES (190, 143, '约翰12', 35, '白骨精4', '2022-10-05 19:49:39', 2, 1, 0);
INSERT INTO `t_requests` VALUES (191, 137, '约翰9', 22, '紫霞仙子1', '2022-10-05 19:49:56', 1, 1, 0);
INSERT INTO `t_requests` VALUES (192, 137, '约翰9', 27, '白骨精2', '2022-10-05 19:49:56', 2, 1, 0);
INSERT INTO `t_requests` VALUES (193, 137, '约翰9', 30, '紫霞仙子3', '2022-10-05 19:49:56', 2, 1, 0);
INSERT INTO `t_requests` VALUES (194, 137, '约翰9', 33, '白骨精4', '2022-10-05 19:49:56', 2, 1, 0);
INSERT INTO `t_requests` VALUES (195, 137, '约翰9', 35, '白骨精4', '2022-10-05 19:49:56', 2, 1, 0);
INSERT INTO `t_requests` VALUES (196, 121, '约翰1', 22, '紫霞仙子1', '2022-10-05 19:54:29', 2, 2, 0);
INSERT INTO `t_requests` VALUES (197, 121, '约翰1', 24, '紫霞仙子2', '2022-10-05 19:54:29', 2, 2, 0);
INSERT INTO `t_requests` VALUES (198, 121, '约翰1', 25, '白骨精2', '2022-10-05 19:54:29', 1, 2, 0);
INSERT INTO `t_requests` VALUES (199, 121, '约翰1', 28, '紫霞仙子3', '2022-10-05 19:54:29', 2, 2, 0);
INSERT INTO `t_requests` VALUES (200, 121, '约翰1', 29, '白骨精3', '2022-10-05 19:54:29', 2, 2, 0);
INSERT INTO `t_requests` VALUES (201, 122, '纳什2', 22, '紫霞仙子1', '2022-10-05 19:55:20', 2, 2, 0);
INSERT INTO `t_requests` VALUES (202, 122, '纳什2', 23, '白骨精1', '2022-10-05 19:55:20', 2, 2, 0);
INSERT INTO `t_requests` VALUES (203, 122, '纳什2', 26, '紫霞仙子2', '2022-10-05 19:55:20', 2, 2, 0);
INSERT INTO `t_requests` VALUES (204, 122, '纳什2', 28, '紫霞仙子3', '2022-10-05 19:55:20', 2, 2, 0);
INSERT INTO `t_requests` VALUES (205, 136, '纳什9', 22, '紫霞仙子1', '2022-10-05 19:55:50', 1, 2, 0);
INSERT INTO `t_requests` VALUES (206, 136, '纳什9', 26, '紫霞仙子2', '2022-10-05 19:55:50', 2, 2, 0);
INSERT INTO `t_requests` VALUES (207, 136, '纳什9', 30, '紫霞仙子3', '2022-10-05 19:55:50', 2, 2, 0);
INSERT INTO `t_requests` VALUES (208, 136, '纳什9', 32, '紫霞仙子4', '2022-10-05 19:55:50', 2, 2, 0);
INSERT INTO `t_requests` VALUES (209, 136, '纳什9', 34, '紫霞仙子4', '2022-10-05 19:55:50', 2, 2, 0);
INSERT INTO `t_requests` VALUES (210, 122, '纳什2', 22, '紫霞仙子1', '2022-10-05 19:58:53', 1, 2, 0);
INSERT INTO `t_requests` VALUES (211, 123, '约翰2', 23, '白骨精1', '2022-10-05 19:59:18', 1, 2, 0);
INSERT INTO `t_requests` VALUES (212, 124, '纳什3', 23, '白骨精1', '2022-10-05 19:59:18', 1, 2, 0);
INSERT INTO `t_requests` VALUES (213, 125, '约翰3', 23, '白骨精1', '2022-10-05 19:59:18', 1, 2, 0);
INSERT INTO `t_requests` VALUES (214, 127, '约翰4', 23, '白骨精1', '2022-10-05 19:59:18', 1, 2, 0);
INSERT INTO `t_requests` VALUES (215, 128, '纳什5', 23, '白骨精1', '2022-10-05 19:59:18', 1, 2, 0);
INSERT INTO `t_requests` VALUES (216, 129, '约翰5', 23, '白骨精1', '2022-10-05 19:59:18', 1, 2, 0);
INSERT INTO `t_requests` VALUES (217, 126, '纳什4', 24, '紫霞仙子2', '2022-10-05 20:00:49', 1, 2, 0);
INSERT INTO `t_requests` VALUES (218, 126, '纳什4', 24, '紫霞仙子2', '2022-10-05 20:00:49', 1, 2, 0);
INSERT INTO `t_requests` VALUES (219, 130, '纳什6', 26, '紫霞仙子2', '2022-10-05 20:08:43', 1, 2, 0);

-- ----------------------------
-- Table structure for t_round
-- ----------------------------
DROP TABLE IF EXISTS `t_round`;
CREATE TABLE `t_round`  (
  `round_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '轮次名称，指明哪一轮',
  `is_start` tinyint(1) NULL DEFAULT 0 COMMENT '是否开启，0未开启，1学生端开启，2是老师端开启，所有记录中只能有一个是开启状态',
  `start_time` datetime NULL DEFAULT NULL COMMENT '发起时间',
  `stop_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`round_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_round
-- ----------------------------
INSERT INTO `t_round` VALUES (1, '2022-1', 0, '2022-08-05 00:00:00', '2022-08-06 00:00:00', 0);
INSERT INTO `t_round` VALUES (2, '2022-2', 0, '2022-08-07 00:00:00', '2022-08-08 00:00:00', 0);

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`  (
  `student_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_number` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号，一般是学号',
  `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `realname` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `telephone` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `class_id` bigint(19) NULL DEFAULT NULL COMMENT '班级id',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班级名称',
  `has_tutor` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否有导师，没有导师为0，有导师就是导师姓名',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `has_science_class` tinyint(1) NULL DEFAULT 0 COMMENT '是否加入科研办，0表示没有，1表示有',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户token身份校验',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除，默认是0没删除',
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES (121, 'yuehan11', 'yuehan11', '约翰1', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '白骨精2', '美国洛杉矶', 0, 'edf04c2e-1437-40f2-9c09-628f4f465842', 0);
INSERT INTO `t_student` VALUES (122, 'nashi111', 'nashi111', '纳什2', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子1', '美国纽约', 1, '0aeaf944-4340-4ea2-a13f-e929f58fc05d', 0);
INSERT INTO `t_student` VALUES (123, 'yuehan12', 'yuehan12', '约翰2', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '白骨精1', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (124, 'nashi112', 'nashi112', '纳什3', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '白骨精1', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (125, 'yuehan13', 'yuehan13', '约翰3', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '白骨精1', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (126, 'nashi113', 'nashi113', '纳什4', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子2', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (127, 'yuehan14', 'yuehan14', '约翰4', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '白骨精1', '美国洛杉矶', 0, '11891447-12aa-4532-b839-9fd96ff98b54', 0);
INSERT INTO `t_student` VALUES (128, 'nashi114', 'nashi114', '纳什5', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '白骨精1', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (129, 'yuehan15', 'yuehan15', '约翰5', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '白骨精1', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (130, 'nashi115', 'nashi115', '纳什6', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子2', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (131, 'yuehan16', 'yuehan16', '约翰6', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (132, 'nashi116', 'nashi116', '纳什7', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (133, 'yuehan17', 'yuehan17', '约翰7', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (134, 'nashi117', 'nashi117', '纳什8', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (135, 'yuehan18', 'yuehan18', '约翰8', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (136, 'nashi118', 'nashi118', '纳什9', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子1', '美国纽约', 0, '010ae147-9a3a-4177-91c6-597a2a157cd9', 0);
INSERT INTO `t_student` VALUES (137, 'yuehan19', 'yuehan19', '约翰9', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子1', '美国洛杉矶', 0, '14f92f85-9e86-455f-bb9f-c556655e9278', 0);
INSERT INTO `t_student` VALUES (138, 'nashi119', 'nashi119', '纳什10', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (139, 'yuehan20', 'yuehan20', '约翰10', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '白骨精2', '美国洛杉矶', 0, 'ed9f2ab6-31f9-4583-9dfb-511821bcdcb6', 0);
INSERT INTO `t_student` VALUES (140, 'nashi120', 'nashi120', '纳什11', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (141, 'yuehan21', 'yuehan21', '约翰11', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国洛杉矶', 0, NULL, 0);
INSERT INTO `t_student` VALUES (142, 'nashi121', 'nashi121', '纳什12', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子1', '美国纽约', 0, '29c3e6b1-8ab3-4ce5-b627-ca16e50ec21e', 0);
INSERT INTO `t_student` VALUES (143, 'yuehan22', 'yuehan22', '约翰12', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '紫霞仙子1', '美国洛杉矶', 0, 'f04717ac-504c-4b8d-834f-264bcef73159', 0);
INSERT INTO `t_student` VALUES (144, 'nashi122', 'nashi122', '纳什13', 'nashi@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国纽约', 0, NULL, 0);
INSERT INTO `t_student` VALUES (145, 'yuehan23', 'yuehan23', '约翰13', 'yuehan@qq.com', '15310818596', NULL, '生物信息1班', '0', '美国洛杉矶', 0, NULL, 0);

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher`  (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师账号',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `realname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `college_id` int(10) NULL DEFAULT NULL COMMENT '所属学院',
  `college_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属学院名称',
  `teacher_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职称',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '老师详细信息',
  `total_student` int(11) NULL DEFAULT NULL COMMENT '剩余能够带领的学生数量',
  `agree_count` int(11) NULL DEFAULT 0 COMMENT '已同意学生数，默认是0',
  `head_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '照片路径',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `telephone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '性别，默认是男0，女士1',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `birthday` datetime NULL DEFAULT NULL COMMENT '出生日期',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录验证token',
  `is_lock` tinyint(1) NULL DEFAULT 0 COMMENT '是否上锁，0是上锁，1是未上锁，默认上锁，上锁时候不能进行选择学生操作',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除，默认是0',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES (22, 'zixiaxianzi11', 'zixiaxianzi', '紫霞仙子1', NULL, '计算机学院', '教授', '盘丝大仙', 5, 5, NULL, 'zixiaxianzi@qq.com', '15310818596', 1, '重庆邮电大学', '1999-12-12 00:00:00', '03317970-e7bc-420a-9574-4502eb34e774', 0, 0);
INSERT INTO `t_teacher` VALUES (23, 'baigujing22', 'baigujing', '白骨精1', NULL, '计算机学院', '讲师', '她是白骨精', 6, 6, NULL, 'baigujing@qq.com', '15310818511', 1, '盘丝洞', '2022-11-11 00:00:00', '2ce9a105-bef1-4f12-b6d3-216b1d29fa80', 0, 0);
INSERT INTO `t_teacher` VALUES (24, 'zixiaxianzi12', 'zixiaxianzi', '紫霞仙子2', NULL, '计算机学院', '教授', '盘丝大仙', 5, 2, NULL, 'zixiaxianzi@qq.com', '15310818512', 1, '重庆邮电大学', '1999-12-13 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (25, 'baigujing23', 'baigujing', '白骨精2', NULL, '计算机学院', '讲师', '她是白骨精', 6, 2, NULL, 'baigujing@qq.com', '15310818513', 1, '盘丝洞', '2022-11-12 00:00:00', '92205082-769f-4340-b717-25eeb9d55149', 0, 0);
INSERT INTO `t_teacher` VALUES (26, 'zixiaxianzi13', 'zixiaxianzi', '紫霞仙子2', NULL, '计算机学院', '教授', '盘丝大仙', 6, 1, NULL, 'zixiaxianzi@qq.com', '15310818511', 1, '重庆邮电大学', '1999-12-14 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (27, 'baigujing24', 'baigujing', '白骨精2', NULL, '计算机学院', '讲师', '她是白骨精', 6, 0, NULL, 'baigujing@qq.com', '15310818512', 1, '盘丝洞', '2022-11-13 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (28, 'zixiaxianzi14', 'zixiaxianzi', '紫霞仙子3', NULL, '计算机学院', '教授', '盘丝大仙', 6, 0, NULL, 'zixiaxianzi@qq.com', '15310818513', 1, '重庆邮电大学', '1999-12-15 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (29, 'baigujing25', 'baigujing', '白骨精3', NULL, '计算机学院', '讲师', '她是白骨精', 6, 0, NULL, 'baigujing@qq.com', '15310818511', 1, '盘丝洞', '2022-11-14 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (30, 'zixiaxianzi15', 'zixiaxianzi', '紫霞仙子3', NULL, '计算机学院', '教授', '盘丝大仙', 6, 0, NULL, 'zixiaxianzi@qq.com', '15310818512', 1, '重庆邮电大学', '1999-12-16 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (31, 'baigujing26', 'baigujing', '白骨精3', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818513', 1, '盘丝洞', '2022-11-15 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (32, 'zixiaxianzi16', 'zixiaxianzi', '紫霞仙子4', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818511', 1, '重庆邮电大学', '1999-12-17 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (33, 'baigujing27', 'baigujing', '白骨精4', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818512', 1, '盘丝洞', '2022-11-16 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (34, 'zixiaxianzi17', 'zixiaxianzi', '紫霞仙子4', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818513', 1, '重庆邮电大学', '1999-12-18 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (35, 'baigujing28', 'baigujing', '白骨精4', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818511', 1, '盘丝洞', '2022-11-17 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (36, 'zixiaxianzi18', 'zixiaxianzi', '紫霞仙子5', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818512', 1, '重庆邮电大学', '1999-12-19 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (37, 'baigujing29', 'baigujing', '白骨精5', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818513', 1, '盘丝洞', '2022-11-18 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (38, 'zixiaxianzi19', 'zixiaxianzi', '紫霞仙子5', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818511', 1, '重庆邮电大学', '1999-12-20 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (39, 'baigujing30', 'baigujing', '白骨精5', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818512', 1, '盘丝洞', '2022-11-19 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (40, 'zixiaxianzi20', 'zixiaxianzi', '紫霞仙子6', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818513', 1, '重庆邮电大学', '1999-12-21 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (41, 'baigujing31', 'baigujing', '白骨精6', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818511', 1, '盘丝洞', '2022-11-20 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (42, 'zixiaxianzi21', 'zixiaxianzi', '紫霞仙子6', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818512', 1, '重庆邮电大学', '1999-12-22 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (43, 'baigujing32', 'baigujing', '白骨精6', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818513', 1, '盘丝洞', '2022-11-21 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (44, 'zixiaxianzi22', 'zixiaxianzi', '紫霞仙子7', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818511', 1, '重庆邮电大学', '1999-12-23 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (45, 'baigujing33', 'baigujing', '白骨精7', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818512', 1, '盘丝洞', '2022-11-22 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (46, 'zixiaxianzi23', 'zixiaxianzi', '紫霞仙子7', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818513', 1, '重庆邮电大学', '1999-12-24 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (47, 'baigujing34', 'baigujing', '白骨精7', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818511', 1, '盘丝洞', '2022-11-23 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (48, 'zixiaxianzi24', 'zixiaxianzi', '紫霞仙子8', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818512', 1, '重庆邮电大学', '1999-12-25 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (49, 'baigujing35', 'baigujing', '白骨精8', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818513', 1, '盘丝洞', '2022-11-24 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (50, 'zixiaxianzi25', 'zixiaxianzi', '紫霞仙子8', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818511', 1, '重庆邮电大学', '1999-12-26 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (51, 'baigujing36', 'baigujing', '白骨精8', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818512', 1, '盘丝洞', '2022-11-25 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (52, 'zixiaxianzi26', 'zixiaxianzi', '紫霞仙子9', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818513', 1, '重庆邮电大学', '1999-12-27 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (53, 'baigujing37', 'baigujing', '白骨精9', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818511', 1, '盘丝洞', '2022-11-26 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (54, 'zixiaxianzi27', 'zixiaxianzi', '紫霞仙子9', NULL, '计算机学院', '教授', '盘丝大仙', 12, 0, NULL, 'zixiaxianzi@qq.com', '15310818512', 1, '重庆邮电大学', '1999-12-28 00:00:00', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (55, 'baigujing38', 'baigujing', '白骨精9', NULL, '计算机学院', '讲师', '她是白骨精', 12, 0, NULL, 'baigujing@qq.com', '15310818513', 1, '盘丝洞', '2022-11-27 00:00:00', NULL, 0, 0);

-- ----------------------------
-- Table structure for t_timetask
-- ----------------------------
DROP TABLE IF EXISTS `t_timetask`;
CREATE TABLE `t_timetask`  (
  `timetask_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `start` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开启时间',
  `end` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关闭时间',
  `status` int(2) NULL DEFAULT 0 COMMENT '状态，0为还未开启，1为已开启，2为已关闭',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`timetask_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 217 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_timetask
-- ----------------------------
INSERT INTO `t_timetask` VALUES (211, '00 29 14 05 8 ?', '10 29 14 05 8 ?', 0, 0);
INSERT INTO `t_timetask` VALUES (216, '03 03 03 05 08 ?', '04 04 04 05 08 ?', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
