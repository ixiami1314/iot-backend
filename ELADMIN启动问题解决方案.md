# ELADMIN项目启动问题解决方案

## 问题描述

启动ELADMIN项目时遇到以下错误：
```
Caused by: org.hibernate.InstantiationException: could not instantiate test object : me.zhengjie.modules.system.domain.Dept
Caused by: java.lang.Error: Unresolved compilation problems: 
    The import me.zhengjie.base cannot be resolved
    BaseEntity cannot be resolved to a type
    Update cannot be resolved to a type
```

## 问题分析

### 1. 模块依赖问题
- `eladmin-system`模块无法找到`me.zhengjie.base.BaseEntity`
- 原因是`eladmin-system`的pom.xml中排除了`eladmin-common`模块的依赖

### 2. Lombok依赖缺失
- `eladmin-common`模块缺少Lombok依赖
- 导致`@Getter`、`@Setter`等注解无法正常工作
- 枚举类的构造器无法正确生成

## 解决方案

### 方案1：修复模块依赖（已完成）

**修改 `eladmin-system/pom.xml`**：
```xml
<dependencies>
    <!-- 公共模块 -->
    <dependency>
        <groupId>me.zhengjie</groupId>
        <artifactId>eladmin-common</artifactId>
        <version>2.7</version>
    </dependency>

    <!-- 日志模块 -->
    <dependency>
        <groupId>me.zhengjie</groupId>
        <artifactId>eladmin-logging</artifactId>
        <version>2.7</version>
    </dependency>

    <!-- 其他依赖... -->
</dependencies>
```

### 方案2：添加Lombok依赖（已完成）

**修改 `eladmin-common/pom.xml`**：
```xml
<dependencies>
    <!--工具包-->
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>${hutool.version}</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 方案3：IDE配置Lombok（推荐）

如果编译仍然有问题，需要在IDE中配置Lombok：

1. **安装Lombok插件**：
   - IntelliJ IDEA: 安装Lombok插件
   - Eclipse: 安装Lombok插件

2. **启用注解处理**：
   - IntelliJ IDEA: Settings → Build → Compiler → Annotation Processors → Enable annotation processing
   - Eclipse: Project Properties → Java Build Path → Libraries → Add Library → Lombok

3. **重启IDE**：
   - 重启IDE让Lombok插件生效

### 方案4：Maven编译配置

在根pom.xml中添加Lombok注解处理器配置：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.10.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                        <version>1.18.24</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 验证步骤

### 1. 重新编译项目
```bash
mvn clean compile -DskipTests
```

### 2. 检查编译结果
- 不应该再有`BaseEntity`相关的编译错误
- 不应该再有Lombok相关的编译错误

### 3. 启动应用
```bash
mvn spring-boot:run -pl eladmin-system
```

### 4. 验证启动
- 应用应该正常启动
- 不应该再有Hibernate实例化错误

## 常见问题

### Q1: 仍然有Lombok相关错误
**A**: 确保IDE安装了Lombok插件并启用了注解处理

### Q2: 模块依赖仍然有问题
**A**: 检查Maven依赖树：`mvn dependency:tree`

### Q3: 编译成功但启动失败
**A**: 检查数据库连接配置和Redis配置

## 项目结构说明

```
eladmin/
├── eladmin-common/     # 公共模块（基础类、工具类）
├── eladmin-logging/    # 日志模块（依赖common）
├── eladmin-tools/      # 工具模块（依赖logging）
├── eladmin-generator/  # 代码生成模块
└── eladmin-system/     # 系统核心模块（依赖所有模块）
```

## 依赖关系

```
eladmin-system
├── eladmin-common     # 基础类和工具
├── eladmin-logging    # 日志功能
├── eladmin-tools      # 第三方工具
└── eladmin-generator  # 代码生成
```

## 总结

主要问题是模块依赖配置不正确和Lombok依赖缺失。通过修复这两个问题，项目应该能够正常编译和启动。

如果问题仍然存在，建议：
1. 检查IDE的Lombok插件配置
2. 清理Maven缓存：`mvn clean`
3. 重新导入Maven项目
4. 重启IDE
