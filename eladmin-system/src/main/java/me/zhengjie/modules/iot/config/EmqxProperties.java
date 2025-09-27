/*
 * EMQX MQTT消息处理配置
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * EMQX MQTT配置
 * @author IoT Team
 */
@Data
@Component
@ConfigurationProperties(prefix = "emqx")
public class EmqxProperties {

    /**
     * EMQX服务器地址
     */
    private String host = "localhost";

    /**
     * EMQX服务器端口
     */
    private Integer port = 1883;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 客户端ID前缀
     */
    private String clientIdPrefix = "iot-backend";

    /**
     * 连接超时时间（秒）
     */
    private Integer connectionTimeout = 30;

    /**
     * 保持连接时间（秒）
     */
    private Integer keepAliveInterval = 60;

    /**
     * 是否启用SSL
     */
    private Boolean sslEnabled = false;

    /**
     * 是否自动重连
     */
    private Boolean autoReconnect = true;

    /**
     * 重连间隔（秒）
     */
    private Integer reconnectInterval = 5;

    /**
     * 最大重连次数
     */
    private Integer maxReconnectAttempts = 10;
}
