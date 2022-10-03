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

 Date: 03/10/2022 17:25:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_requests
-- ----------------------------
INSERT INTO `t_requests` VALUES (108, 1, '张三', 1, '赵老师1', '2022-10-03 17:19:35', 1, 1, 0);
INSERT INTO `t_requests` VALUES (109, 2, '李四', 1, '赵老师1', '2022-10-03 17:19:50', 0, 1, 0);
INSERT INTO `t_requests` VALUES (110, 3, '王五', 1, '赵老师1', '2022-10-03 17:20:03', 0, 1, 0);
INSERT INTO `t_requests` VALUES (111, 4, '赵六', 1, '赵老师1', '2022-10-03 17:20:19', 0, 1, 0);
INSERT INTO `t_requests` VALUES (112, 5, '钱七', 1, '赵老师1', '2022-10-03 17:20:35', 0, 1, 0);
INSERT INTO `t_requests` VALUES (113, 5, '钱七', 1, '赵老师1', '2022-10-03 17:21:59', 1, 2, 0);
INSERT INTO `t_requests` VALUES (114, 4, '赵六', 1, '赵老师1', '2022-10-03 17:22:13', 1, 2, 0);
INSERT INTO `t_requests` VALUES (115, 3, '王五', 1, '赵老师1', '2022-10-03 17:22:29', 0, 2, 0);
INSERT INTO `t_requests` VALUES (116, 2, '李四', 1, '赵老师1', '2022-10-03 17:22:42', 1, 2, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_round
-- ----------------------------
INSERT INTO `t_round` VALUES (1, '2022-1', 0, '2022-08-05 00:00:00', '2022-08-06 00:00:00', 0);
INSERT INTO `t_round` VALUES (2, '2022-2', 2, '2022-08-07 00:00:00', '2022-08-08 00:00:00', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES (1, 'S210231001', 'S210231001', '张三', 'S210231001@cqupt.com', '13914562541', 1, '生物信息1班', '赵老师1', '重庆市南岸区江南水岸二组团7幢', 0, '3109eafd-39b1-4f3a-8e6e-8a1158f74a0f', 0);
INSERT INTO `t_student` VALUES (2, 'S210231002', 'S210231002', '李四', 'S210231002@cqupt.com', '13854612963', 1, '生物信息1班', '赵老师1', '重庆市南岸区江南水岸二组团7幢', 0, '481dae65-f9d0-40f2-8fcf-ec18e9e5a647', 0);
INSERT INTO `t_student` VALUES (3, 'S210231003', 'S210231003', '王五', 'S210231003@cqupt.com', '15646595659', 1, '生物信息1班', '0', '重庆市南岸区江南水岸二组团7幢', 0, '65e83996-5638-4e4b-8ea0-4c8504ec7e85', 0);
INSERT INTO `t_student` VALUES (4, 'S210231004', 'S210231004', '赵六', 'S210231004@cqupt.com', '16542548565', 1, '生物信息1班', '赵老师1', '重庆市南岸区江南水岸二组团7幢', 0, '042c8d7c-231b-4ca4-b1de-08ce1c935668', 0);
INSERT INTO `t_student` VALUES (5, 'S210231005', 'S210231005', '钱七', 'S210231005@cqupt.com', '15648456656', 1, '生物信息1班', '赵老师1', '重庆市南岸区江南水岸二组团7幢', 0, 'df96ad75-f377-4f03-9a2e-d2b76dd5831b', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES (1, 't111111', '111111', '赵老师1', 1, '生物学院', '副教授', '生物学院副教授，针对生物大数据颇有研究', 4, 4, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp1.itc.cn%2Fimages01%2F20200718%2F9d65e695aa2f48d6894683fdd2cb3b04.jpeg&refer=http%3A%2F%2Fp1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661952029&t=86daea887a1a10217bba8653ea8c3783', 'xxxxxx@qq.com', '15212345689', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', 'e304e0c4-b1c7-4bc2-bc0a-5079e56735eb', 0, 0);
INSERT INTO `t_teacher` VALUES (2, 't222222', '222222', '赵老师2', 1, '生物学院', '教授', '生物学院副教授，针对生物大数据颇有研究', 13, 0, 'https://github.com/348683013/Pictures/blob/main/QQ%E6%88%AA%E5%9B%BE20220804150525.png?raw=true', 'xxxxxx@qq.com', '15212345623', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', '83f67117-9a09-40fe-9c5f-50543c056201', 0, 0);
INSERT INTO `t_teacher` VALUES (3, 't333333', '333333', '赵老师3', 1, '生物学院', '教授', '生物学院副教授，针对生物大数据颇有研究', 20, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804150810.png', 'xxxxxx@qq.com', '15212345698', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (4, 't444444', '444444', '赵老师4', 1, '生物学院', '讲师', '生物学院副教授，针对生物大数据颇有研究', 9, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804150851.png', 'xxxxxx@qq.com', '15212345678', 1, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (5, 't555555', '555555', '赵老师5', 1, '生物学院', '副教授', '生物学院副教授，针对生物大数据颇有研究', 11, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804150914.png', 'xxxxxx@qq.com', '15212345678', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (6, 't666666', '666666', '赵老师6', 1, '生物学院', '讲师', '生物学院副教授，针对生物大数据颇有研究', 0, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804150950.png', 'xxxxxx@qq.com', '15212345678', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (7, 't777777', '777777', '赵老师7', 1, '生物学院', '教授', '生物学院副教授，针对生物大数据颇有研究', 9, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804151028.png', 'xxxxxx@qq.com', '15212345678', 1, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (9, 't999999', '999999', '赵老师8', 1, '生物学院', '讲师', '生物学院副教授，针对生物大数据颇有研究', 10, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804151058.png', 'xxxxxx@qq.com', '15212345677', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (10, 't101010', '101010', '名老师2号', 1, '生物学院', '校长', '我是校长', 0, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804151217.png', 'ming@qq.com', '18888888888', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (12, 'bao123', 'bao123', '赵老师', 1, '生物学院', '教授', '生物学院老牌教授', 1, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804151243.png', 'bao123@qq.com', '15310818596', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);
INSERT INTO `t_teacher` VALUES (13, 'bao123', 'bao123', '包老师', 1, '生物学院', '教授', '生物学院老牌教授', 2, 0, 'https://raw.githubusercontent.com/348683013/Pictures/main/QQ%E6%88%AA%E5%9B%BE20220804151331.png', 'bao123@qq.com', '15310818596', 0, '重庆邮电大学家属院', '1996-09-03 21:38:07', NULL, 0, 0);

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
