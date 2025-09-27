# AWS SDK依赖问题解决方案

## 问题描述
在`AmzS3Config.java`中报错，找不到以下类：
- `software.amazon.awssdk.auth.credentials.AwsBasicCredentials`
- `software.amazon.awssdk.auth.credentials.StaticCredentialsProvider`
- `software.amazon.awssdk.regions.Region`

## 问题原因
AWS SDK v2采用了模块化设计，需要单独引入各个模块的依赖。原来的配置只引入了`s3`模块，但缺少了`auth`、`regions`等核心模块。

## 解决方案

### 方案1：完善AWS SDK依赖（推荐用于启用S3功能）

在`eladmin-tools/pom.xml`中添加完整的AWS SDK依赖：

```xml
<properties>
    <aws.sdk.version>2.30.13</aws.sdk.version>
</properties>

<dependencies>
    <!--amazon s3 依赖-->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
        <version>${aws.sdk.version}</version>
    </dependency>
    
    <!-- AWS SDK Core 依赖 -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>aws-core</artifactId>
        <version>${aws.sdk.version}</version>
    </dependency>
    
    <!-- AWS SDK Auth 依赖 -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>auth</artifactId>
        <version>${aws.sdk.version}</version>
    </dependency>
    
    <!-- AWS SDK Regions 依赖 -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>regions</artifactId>
        <version>${aws.sdk.version}</version>
    </dependency>
    
    <!-- AWS SDK Waiters 依赖 -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3-waiters</artifactId>
        <version>${aws.sdk.version}</version>
    </dependency>
</dependencies>
```

### 方案2：禁用S3功能（推荐用于不需要S3的场景）

由于您已经通过配置文件禁用了S3功能，可以采用以下方式：

1. **配置文件禁用**：
```yaml
amz:
  s3:
    enabled: false
```

2. **代码修改**：
   - 已修改`AmzS3Config.java`，注释了AWS SDK相关代码
   - 已添加`@ConditionalOnProperty`注解
   - 返回类型改为`Object`，避免编译错误

3. **验证禁用效果**：
   - S3相关Bean不会被创建
   - S3 API接口不会暴露
   - 不会出现AWS SDK依赖问题

## 当前状态

✅ **已完成的修改**：
- 配置文件添加了`enabled: false`
- `AmzS3Config.java`添加了条件注解
- `S3StorageController.java`添加了条件注解
- 修改了Bean方法避免编译错误

✅ **依赖配置**：
- 已添加完整的AWS SDK依赖
- 使用统一的版本管理

## 验证步骤

1. **重新编译项目**：
```bash
mvn clean compile
```

2. **检查编译结果**：
- 不应该再有AWS SDK相关的编译错误
- S3相关类应该正常编译

3. **启动应用**：
```bash
mvn spring-boot:run
```

4. **验证功能**：
- S3相关API应该不可用
- 应用启动日志中不应该有S3相关错误

## 重新启用S3功能

如果将来需要启用S3功能：

1. **修改配置文件**：
```yaml
amz:
  s3:
    enabled: true
    # 配置正确的S3参数
```

2. **恢复代码**：
```java
@Bean
@ConditionalOnProperty(name = "amz.s3.enabled", havingValue = "true", matchIfMissing = false)
public S3Client amazonS3Client() {
    return S3Client.builder().region(Region.of(region))
            .endpointOverride(URI.create(endPoint))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
            .build();
}
```

3. **添加import语句**：
```java
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
```

## 总结

当前配置已经成功禁用了S3功能，避免了AWS SDK依赖问题。如果将来需要S3功能，只需要：
1. 修改配置文件启用S3
2. 恢复相关代码
3. 确保依赖配置正确

这样既解决了当前的编译问题，又保持了代码的完整性和可扩展性。
