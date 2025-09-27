/*
 * IoT客户DTO
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;
import java.io.Serializable;

/**
 * 客户数据传输对象
 * @author IoT Team
 */
@Getter
@Setter
public class CustomerDto extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "客户ID")
    private Long id;

    @ApiModelProperty(value = "客户编码")
    private String customerCode;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户描述")
    private String description;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系邮箱")
    private String contactEmail;

    @ApiModelProperty(value = "客户地址")
    private String address;

    @ApiModelProperty(value = "客户状态：0-禁用，1-启用")
    private Integer status;

    @ApiModelProperty(value = "EMQX连接配置")
    private String emqxConfig;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "设备数量")
    private Long deviceCount;
}
