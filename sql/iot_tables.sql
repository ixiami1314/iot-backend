-- IoT设备管理系统数据库表结构
-- 基于ELADMIN项目结构设计
-- @author IoT Team
-- @date 2024

-- 客户表
CREATE TABLE `iot_customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_code` varchar(50) NOT NULL COMMENT '客户编码',
  `customer_name` varchar(100) NOT NULL COMMENT '客户名称',
  `description` varchar(500) DEFAULT NULL COMMENT '客户描述',
  `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '客户地址',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '客户状态：0-禁用，1-启用',
  `emqx_config` text COMMENT 'EMQX连接配置',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `uk_customer_code` (`customer_code`),
  KEY `idx_customer_name` (`customer_name`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IoT客户表';

-- 设备表
CREATE TABLE `iot_device` (
  `device_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `device_code` varchar(50) NOT NULL COMMENT '设备编码',
  `device_name` varchar(100) NOT NULL COMMENT '设备名称',
  `device_type` varchar(50) DEFAULT NULL COMMENT '设备类型',
  `device_model` varchar(50) DEFAULT NULL COMMENT '设备型号',
  `manufacturer` varchar(50) DEFAULT NULL COMMENT '设备厂商',
  `description` varchar(500) DEFAULT NULL COMMENT '设备描述',
  `location` varchar(200) DEFAULT NULL COMMENT '设备位置',
  `install_time` timestamp NULL DEFAULT NULL COMMENT '安装时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '设备状态：0-离线，1-在线，2-故障',
  `last_online_time` timestamp NULL DEFAULT NULL COMMENT '最后在线时间',
  `last_offline_time` timestamp NULL DEFAULT NULL COMMENT '最后离线时间',
  `mqtt_topic` varchar(200) DEFAULT NULL COMMENT 'EMQX主题',
  `device_secret` varchar(100) DEFAULT NULL COMMENT '设备密钥',
  `device_config` text COMMENT '设备配置信息',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `customer_id` bigint(20) NOT NULL COMMENT '所属客户ID',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`device_id`),
  UNIQUE KEY `uk_device_code` (`device_code`),
  UNIQUE KEY `uk_mqtt_topic` (`mqtt_topic`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_device_name` (`device_name`),
  KEY `idx_device_type` (`device_type`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_device_customer` FOREIGN KEY (`customer_id`) REFERENCES `iot_customer` (`customer_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IoT设备表';

-- 设备状态记录表
CREATE TABLE `iot_device_status` (
  `status_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '状态记录ID',
  `device_id` bigint(20) NOT NULL COMMENT '设备ID',
  `status` int(1) NOT NULL COMMENT '设备状态：0-离线，1-在线，2-故障',
  `status_desc` varchar(100) DEFAULT NULL COMMENT '状态描述',
  `raw_data` text COMMENT '设备上报的原始数据',
  `parsed_data` text COMMENT '解析后的数据',
  `report_time` timestamp NULL DEFAULT NULL COMMENT '数据上报时间',
  `receive_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据接收时间',
  `message_id` varchar(100) DEFAULT NULL COMMENT 'EMQX消息ID',
  `mqtt_topic` varchar(200) DEFAULT NULL COMMENT 'EMQX主题',
  `data_quality` int(1) NOT NULL DEFAULT '0' COMMENT '数据质量：0-正常，1-异常',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`status_id`),
  KEY `idx_device_id` (`device_id`),
  KEY `idx_status` (`status`),
  KEY `idx_report_time` (`report_time`),
  KEY `idx_message_id` (`message_id`),
  KEY `idx_mqtt_topic` (`mqtt_topic`),
  CONSTRAINT `fk_status_device` FOREIGN KEY (`device_id`) REFERENCES `iot_device` (`device_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IoT设备状态记录表';

-- 插入示例数据
-- 插入示例客户
INSERT INTO `iot_customer` (`customer_code`, `customer_name`, `description`, `contact_person`, `contact_phone`, `contact_email`, `address`, `status`, `emqx_config`, `remark`) VALUES
('CUST001', '智慧城市管理有限公司', '负责智慧城市项目的设备管理', '张三', '13800138001', 'zhangsan@smartcity.com', '北京市朝阳区智慧大厦', 1, '{"host":"localhost","port":1883,"username":"admin","password":"admin123"}', '主要客户'),
('CUST002', '工业物联网科技有限公司', '专注于工业设备监控和管理', '李四', '13800138002', 'lisi@industrial.com', '上海市浦东新区工业园', 1, '{"host":"localhost","port":1883,"username":"admin","password":"admin123"}', '工业客户');

-- 插入示例设备
INSERT INTO `iot_device` (`device_code`, `device_name`, `device_type`, `device_model`, `manufacturer`, `description`, `location`, `install_time`, `status`, `mqtt_topic`, `device_secret`, `customer_id`) VALUES
('DEV001', '温度传感器-001', '传感器', 'TEMP-100', '传感器厂商A', '用于监测环境温度', '办公楼1层大厅', '2024-01-01 10:00:00', 1, 'device/temp/001', 'secret001', 1),
('DEV002', '湿度传感器-001', '传感器', 'HUMI-200', '传感器厂商B', '用于监测环境湿度', '办公楼2层会议室', '2024-01-02 14:30:00', 0, 'device/humi/001', 'secret002', 1),
('DEV003', '压力传感器-001', '传感器', 'PRESS-300', '传感器厂商C', '用于监测管道压力', '工厂车间A', '2024-01-03 09:15:00', 1, 'device/press/001', 'secret003', 2);

-- 插入示例设备状态记录
INSERT INTO `iot_device_status` (`device_id`, `status`, `status_desc`, `raw_data`, `parsed_data`, `report_time`, `message_id`, `mqtt_topic`, `data_quality`) VALUES
(1, 1, '在线', '{"temperature":25.5,"humidity":60,"timestamp":1704067200000}', '{"temperature":25.5,"humidity":60,"timestamp":1704067200000}', '2024-01-01 12:00:00', 'msg001', 'device/temp/001', 0),
(2, 0, '离线', '{"humidity":55,"timestamp":1704067200000}', '{"humidity":55,"timestamp":1704067200000}', '2024-01-02 15:00:00', 'msg002', 'device/humi/001', 0),
(3, 1, '在线', '{"pressure":1.2,"unit":"MPa","timestamp":1704067200000}', '{"pressure":1.2,"unit":"MPa","timestamp":1704067200000}', '2024-01-03 10:00:00', 'msg003', 'device/press/001', 0);
