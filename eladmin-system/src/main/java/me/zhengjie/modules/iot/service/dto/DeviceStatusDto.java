/*
 * IoT设备状态DTO
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 设备状态数据传输对象
 * @author IoT Team
 */
@Getter
@Setter
public class DeviceStatusDto extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "状态记录ID")
    private Long id;

    @ApiModelProperty(value = "设备状态：0-离线，1-在线，2-故障")
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "设备上报的原始数据")
    private String rawData;

    @ApiModelProperty(value = "解析后的数据")
    private String parsedData;

    @ApiModelProperty(value = "数据上报时间")
    private Timestamp reportTime;

    @ApiModelProperty(value = "数据接收时间")
    private Timestamp receiveTime;

    @ApiModelProperty(value = "EMQX消息ID")
    private String messageId;

    @ApiModelProperty(value = "EMQX主题")
    private String mqttTopic;

    @ApiModelProperty(value = "数据质量：0-正常，1-异常")
    private Integer dataQuality;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "所属设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;
}
