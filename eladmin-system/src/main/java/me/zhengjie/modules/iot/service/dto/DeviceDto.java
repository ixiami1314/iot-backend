/*
 * IoT设备DTO
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
 * 设备数据传输对象
 * @author IoT Team
 */
@Getter
@Setter
public class DeviceDto extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "设备ID")
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备名称")
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
    private Timestamp installTime;

    @ApiModelProperty(value = "设备状态：0-离线，1-在线，2-故障")
    private Integer status;

    @ApiModelProperty(value = "最后在线时间")
    private Timestamp lastOnlineTime;

    @ApiModelProperty(value = "最后离线时间")
    private Timestamp lastOfflineTime;

    @ApiModelProperty(value = "EMQX主题")
    private String mqttTopic;

    @ApiModelProperty(value = "设备密钥")
    private String deviceSecret;

    @ApiModelProperty(value = "设备配置信息")
    private String deviceConfig;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "所属客户ID")
    private Long customerId;

    @ApiModelProperty(value = "所属客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户编码")
    private String customerCode;
}
