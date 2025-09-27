/*
 * IoT设备管理实体
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * 设备实体 - 管理IoT设备的基本信息
 * @author IoT Team
 */
@Entity
@Getter
@Setter
@Table(name = "iot_device")
public class Device extends BaseEntity implements Serializable {

    @Id
    @Column(name = "device_id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "设备ID", hidden = true)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @ApiModelProperty(value = "设备编码", required = true)
    private String deviceCode;

    @NotBlank
    @ApiModelProperty(value = "设备名称", required = true)
    private String deviceName;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @ApiModelProperty(value = "设备厂商")
    private String manufacturer;

    @ApiModelProperty(value = "设备描述")
    private String description;

    @ApiModelProperty(value = "设备位置")
    private String location;

    @ApiModelProperty(value = "安装时间")
    private java.sql.Timestamp installTime;

    @ApiModelProperty(value = "设备状态：0-离线，1-在线，2-故障")
    private Integer status = 0;

    @ApiModelProperty(value = "最后在线时间")
    private java.sql.Timestamp lastOnlineTime;

    @ApiModelProperty(value = "最后离线时间")
    private java.sql.Timestamp lastOfflineTime;

    @ApiModelProperty(value = "EMQX主题")
    private String mqttTopic;

    @ApiModelProperty(value = "设备密钥")
    private String deviceSecret;

    @ApiModelProperty(value = "设备配置信息")
    @Column(columnDefinition = "TEXT")
    private String deviceConfig;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 多对一关系：多个设备属于一个客户
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @ApiModelProperty(value = "所属客户")
    private Customer customer;

    // 一对多关系：一个设备有多个状态记录
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "设备状态记录", hidden = true)
    private Set<DeviceStatus> deviceStatuses;
}
