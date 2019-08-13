-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2019-08-13 15:40:40
-- 服务器版本： 8.0.16
-- PHP 版本： 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `seckill`
--

-- --------------------------------------------------------

--
-- 表的结构 `goods`
--

CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL,
  `goods_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `goods_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `goods_img` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `goods_detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `goods_price` decimal(10,2) DEFAULT '0.00',
  `goods_stock` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 转存表中的数据 `goods`
--

INSERT INTO `goods` (`id`, `goods_name`, `goods_title`, `goods_img`, `goods_detail`, `goods_price`, `goods_stock`) VALUES
(1, 'iphoneX', 'Apple iPhone X 256G 银色', '/img/iphonex.png', 'Apple iPhone X (A1865) 256GB 深空灰色 移动联通电信4G手机', '7299.00', 100),
(2, 'HUAWEI Mate 20 Pro', 'HUAWEI Mate 20 Pro 8GB+128GB 翡翠冷', '/img/iphonex.png', '华为 HUAWEI Mate 20 Pro (UD)屏内指纹版麒麟980芯片全面屏超大广角徕卡三摄8GB+128GB亮黑色全网通双4G手机', '5200.00', 50),
(3, 'OPPO Reno 10倍变焦版', 'OPPO Reno 10倍变焦版 6GB+128GB 雾海绿', '/img/iphonex.png', 'OPPO Reno 10倍变焦版 高通骁龙855 4800万超清三摄 6GB+128GB 雾海绿 全网通 全面屏拍照智能游戏手机', '3999.00', 10),
(4, '小米9', '小米9 8GB+128GB 全息幻彩蓝', '/img/iphonex.png', '小米9 4800万超广角三摄 6GB+128GB全息幻彩蓝 骁龙855 全网通4G 双卡双待 水滴全面屏拍照智能游戏手机', '2999.00', 100),
(5, '魅族', '魅族', '/img/iphonex.png', '魅族', '10000.00', 20),
(6, '锤子', '锤子', '/img/iphonex.png', '锤子', '20000.00', 30);

-- --------------------------------------------------------

--
-- 表的结构 `miaosha_goods`
--

CREATE TABLE `miaosha_goods` (
  `id` bigint(20) NOT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `miaosha_price` decimal(10,2) DEFAULT '0.00',
  `stock_count` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 转存表中的数据 `miaosha_goods`
--

INSERT INTO `miaosha_goods` (`id`, `goods_id`, `miaosha_price`, `stock_count`, `start_date`, `end_date`) VALUES
(1, 1, '0.01', 10, '2019-08-11 00:00:00', '2019-08-11 00:00:00'),
(2, 2, '0.01', 9, '2019-08-11 12:04:00', '2019-08-12 12:05:00');

-- --------------------------------------------------------

--
-- 表的结构 `miaosha_order`
--

CREATE TABLE `miaosha_order` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 转存表中的数据 `miaosha_order`
--

INSERT INTO `miaosha_order` (`id`, `user_id`, `order_id`, `goods_id`) VALUES
(4, 17863113969, 1, 2);

-- --------------------------------------------------------

--
-- 表的结构 `order_info`
--

CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `delivery_addr_id` bigint(20) DEFAULT NULL,
  `goods_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `goods_count` int(11) DEFAULT '0',
  `goods_price` decimal(10,2) DEFAULT '0.00',
  `order_channel` tinyint(4) DEFAULT '0',
  `order_status` tinyint(4) DEFAULT '0' COMMENT '0新建未支付、1已支付、2已发货、3已收货、4已退款、5已完成',
  `create_date` datetime DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 转存表中的数据 `order_info`
--

INSERT INTO `order_info` (`id`, `user_id`, `goods_id`, `delivery_addr_id`, `goods_name`, `goods_count`, `goods_price`, `order_channel`, `order_status`, `create_date`, `pay_date`) VALUES
(13, 17863113969, 2, NULL, 'HUAWEI Mate 20 Pro', 1, '0.01', 1, 1, '2019-08-11 17:31:08', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID，手机号码',
  `nickname` varchar(255) NOT NULL,
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5（MD5（明文密码 + 固定salt） + salt）',
  `salt` varchar(10) DEFAULT NULL,
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `nickname`, `password`, `salt`, `head`, `register_date`, `last_login_date`, `login_count`) VALUES
(17863113969, '郑天乐', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', NULL, '2019-08-07 00:00:00', '2019-08-07 00:00:00', 1);

--
-- 转储表的索引
--

--
-- 表的索引 `goods`
--
ALTER TABLE `goods`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `miaosha_goods`
--
ALTER TABLE `miaosha_goods`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `miaosha_order`
--
ALTER TABLE `miaosha_order`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE;

--
-- 表的索引 `order_info`
--
ALTER TABLE `order_info`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `goods`
--
ALTER TABLE `goods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 使用表AUTO_INCREMENT `miaosha_goods`
--
ALTER TABLE `miaosha_goods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `miaosha_order`
--
ALTER TABLE `miaosha_order`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `order_info`
--
ALTER TABLE `order_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
