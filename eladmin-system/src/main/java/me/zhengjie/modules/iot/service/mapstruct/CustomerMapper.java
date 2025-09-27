/*
 * IoT客户MapStruct映射器
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.iot.domain.Customer;
import me.zhengjie.modules.iot.service.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 客户实体与DTO映射器
 * @author IoT Team
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper extends BaseMapper<CustomerDto, Customer> {
}
