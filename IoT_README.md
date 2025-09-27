# IoT设备管理系统

基于ELADMIN开源项目二次开发的IoT设备管理系统，支持多客户设备管理、EMQX MQTT消息处理、设备状态监控等功能。

## 项目特性

- **多客户管理**：支持多个客户的设备统一管理
- **设备生命周期管理**：设备注册、配置、状态监控、故障处理
- **EMQX集成**：支持EMQX MQTT消息处理，实时接收设备数据
- **状态监控**：实时监控设备在线状态和数据上报
- **代码生成**：基于ELADMIN代码生成器快速开发新功能
- **权限管理**：基于Spring Security的细粒度权限控制

## 核心模块

### 1. 客户管理模块 (Customer)
- 客户基本信息管理
- 客户状态控制
- EMQX连接配置
- 客户设备统计

### 2. 设备管理模块 (Device)
- 设备基本信息管理
- 设备类型和型号管理
- MQTT主题配置
- 设备状态监控

### 3. 设备状态模块 (DeviceStatus)
- 设备状态记录
- 数据上报历史
- 消息解析和存储
- 数据质量监控

### 4. EMQX消息处理
- MQTT消息接收
- 设备状态更新
- 数据解析和存储
- 离线检测

## 数据库设计

### 核心表结构

1. **iot_customer** - 客户表
   - 客户基本信息
   - EMQX连接配置
   - 状态管理

2. **iot_device** - 设备表
   - 设备基本信息
   - MQTT主题配置
   - 状态和时间戳

3. **iot_device_status** - 设备状态记录表
   - 状态变化记录
   - 原始数据和解析数据
   - 消息ID和时间戳

## 快速开始

### 1. 环境要求
- JDK 1.8+
- MySQL 5.7+
- Redis 3.0+
- EMQX 4.0+ (可选)

### 2. 数据库初始化
```sql
-- 执行建表SQL
source sql/iot_tables.sql
```

### 3. 配置文件
```yaml
# application.yml
spring:
  profiles:
    active: dev,iot

# EMQX配置
emqx:
  host: localhost
  port: 1883
  username: admin
  password: admin123
```

### 4. 启动应用
```bash
mvn spring-boot:run
```

## API接口

### 客户管理
- `GET /api/iot/customers` - 查询客户列表
- `POST /api/iot/customers` - 创建客户
- `PUT /api/iot/customers` - 更新客户
- `DELETE /api/iot/customers` - 删除客户
- `GET /api/iot/customers/statistics` - 客户统计信息

### 设备管理
- `GET /api/iot/devices` - 查询设备列表
- `POST /api/iot/devices` - 创建设备
- `PUT /api/iot/devices` - 更新设备
- `DELETE /api/iot/devices` - 删除设备

### 设备状态
- `GET /api/iot/device-status` - 查询状态记录
- `GET /api/iot/device-status/{deviceId}` - 查询设备状态历史

## 代码生成器使用

### 1. 访问代码生成器
```
http://localhost:8000/doc.html
```

### 2. 生成步骤
1. 选择数据库表
2. 配置字段信息
3. 选择生成类型（生成/预览/下载）
4. 下载生成的代码

### 3. 生成的文件
- Entity实体类
- Repository接口
- Service接口和实现
- Controller控制器
- DTO和QueryCriteria
- MapStruct映射器

## EMQX集成

### 1. MQTT主题格式
```
device/{deviceType}/{deviceCode}
sensor/{sensorType}/{deviceCode}
status/{deviceCode}
```

### 2. 消息格式
```json
{
  "status": 1,
  "timestamp": 1704067200000,
  "data": {
    "temperature": 25.5,
    "humidity": 60
  },
  "config": {
    "interval": 30
  }
}
```

### 3. 消息处理流程
1. 接收MQTT消息
2. 解析消息内容
3. 更新设备状态
4. 保存状态记录
5. 处理离线检测

## 开发指南

### 1. 添加新实体
1. 创建数据库表
2. 使用代码生成器生成基础代码
3. 自定义业务逻辑
4. 添加权限控制

### 2. 扩展EMQX功能
1. 修改`EmqxMessageService`
2. 添加新的消息处理逻辑
3. 配置新的主题模式
4. 测试消息处理

### 3. 权限配置
```java
@PreAuthorize("@el.check('customer:list')")
public ResponseEntity<PageResult<CustomerDto>> queryCustomer() {
    // 业务逻辑
}
```

## 部署说明

### 1. 生产环境配置
```yaml
spring:
  profiles:
    active: prod,iot

emqx:
  host: your-emqx-host
  port: 1883
  username: your-username
  password: your-password
```

### 2. 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://your-db-host:3306/iot_db
    username: your-db-user
    password: your-db-password
```

### 3. Redis配置
```yaml
spring:
  redis:
    host: your-redis-host
    port: 6379
    password: your-redis-password
```

## 监控和运维

### 1. 应用监控
- Spring Boot Actuator
- 自定义健康检查
- 性能指标监控

### 2. 设备监控
- 设备在线状态
- 数据上报频率
- 异常数据检测

### 3. 日志管理
- 操作日志记录
- 错误日志追踪
- 性能日志分析

## 常见问题

### 1. 设备无法连接
- 检查EMQX配置
- 验证MQTT主题格式
- 确认设备密钥正确

### 2. 数据不更新
- 检查消息处理服务
- 验证数据库连接
- 查看错误日志

### 3. 权限问题
- 检查用户角色配置
- 验证权限注解
- 确认菜单权限

## 贡献指南

1. Fork项目
2. 创建功能分支
3. 提交代码
4. 创建Pull Request

## 许可证

基于Apache License 2.0开源协议
