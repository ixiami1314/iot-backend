/*
 * IoT设备查询条件
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * 设备查询条件
 * @author IoT Team
 */
@Data
public class DeviceQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String deviceCode;

    @Query(type = Query.Type.INNER_LIKE)
    private String deviceName;

    @Query(type = Query.Type.EQUAL)
    private String deviceType;

    @Query(type = Query.Type.EQUAL)
    private String deviceModel;

    @Query(type = Query.Type.EQUAL)
    private String manufacturer;

    @Query(type = Query.Type.EQUAL)
    private Integer status;

    @Query(type = Query.Type.EQUAL)
    private Long customerId;

    @Query(type = Query.Type.INNER_LIKE)
    private String location;

    @Query(type = Query.Type.INNER_LIKE)
    private String mqttTopic;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> installTime;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> lastOnlineTime;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
