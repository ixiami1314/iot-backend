/*
 * IoT设备MapStruct映射器
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.iot.domain.Device;
import me.zhengjie.modules.iot.service.dto.DeviceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 设备实体与DTO映射器
 * @author IoT Team
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceMapper extends BaseMapper<DeviceDto, Device> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.customerName", target = "customerName")
    @Mapping(source = "customer.customerCode", target = "customerCode")
    DeviceDto toDto(Device device);

    @Mapping(source = "customerId", target = "customer.id")
    Device toEntity(DeviceDto deviceDto);
}
