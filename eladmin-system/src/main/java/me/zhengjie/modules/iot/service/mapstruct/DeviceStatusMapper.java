/*
 * IoT设备状态MapStruct映射器
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.mapstruct;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.iot.domain.DeviceStatus;
import me.zhengjie.modules.iot.service.dto.DeviceStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 设备状态实体与DTO映射器
 * @author IoT Team
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeviceStatusMapper extends BaseMapper<DeviceStatusDto, DeviceStatus> {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "device.deviceCode", target = "deviceCode")
    @Mapping(source = "device.deviceName", target = "deviceName")
    DeviceStatusDto toDto(DeviceStatus deviceStatus);

    @Mapping(source = "deviceId", target = "device.id")
    DeviceStatus toEntity(DeviceStatusDto deviceStatusDto);
}
