/*
 * IoT客户管理实体
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
 * 客户实体 - 管理各个客户的基本信息
 * @author IoT Team
 */
@Entity
@Getter
@Setter
@Table(name = "iot_customer")
public class Customer extends BaseEntity implements Serializable {

    @Id
    @Column(name = "customer_id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "客户ID", hidden = true)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @ApiModelProperty(value = "客户编码", required = true)
    private String customerCode;

    @NotBlank
    @ApiModelProperty(value = "客户名称", required = true)
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

    @NotNull
    @ApiModelProperty(value = "客户状态：0-禁用，1-启用")
    private Integer status = 1;

    @ApiModelProperty(value = "EMQX连接配置")
    @Column(columnDefinition = "TEXT")
    private String emqxConfig;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 一对多关系：一个客户有多个设备
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(value = "客户设备列表", hidden = true)
    private Set<Device> devices;
}
