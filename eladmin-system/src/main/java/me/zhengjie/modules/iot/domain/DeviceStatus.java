/*
 * IoT设备状态管理实体
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 设备状态实体 - 记录设备的状态变化和上报数据
 * @author IoT Team
 */
@Entity
@Getter
@Setter
@Table(name = "iot_device_status")
public class DeviceStatus extends BaseEntity implements Serializable {

    @Id
    @Column(name = "status_id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "状态记录ID", hidden = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "设备状态：0-离线，1-在线，2-故障", required = true)
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "设备上报的原始数据")
    @Column(columnDefinition = "TEXT")
    private String rawData;

    @ApiModelProperty(value = "解析后的数据")
    @Column(columnDefinition = "TEXT")
    private String parsedData;

    @ApiModelProperty(value = "数据上报时间")
    private java.sql.Timestamp reportTime;

    @ApiModelProperty(value = "数据接收时间")
    private java.sql.Timestamp receiveTime;

    @ApiModelProperty(value = "EMQX消息ID")
    private String messageId;

    @ApiModelProperty(value = "EMQX主题")
    private String mqttTopic;

    @ApiModelProperty(value = "数据质量：0-正常，1-异常")
    private Integer dataQuality = 0;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 多对一关系：多个状态记录属于一个设备
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    @ApiModelProperty(value = "所属设备")
    private Device device;
}
