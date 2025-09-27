/*
 * IoT设备状态查询条件
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * 设备状态查询条件
 * @author IoT Team
 */
@Data
public class DeviceStatusQueryCriteria {

    @Query(type = Query.Type.EQUAL)
    private Long deviceId;

    @Query(type = Query.Type.EQUAL)
    private Integer status;

    @Query(type = Query.Type.EQUAL)
    private Integer dataQuality;

    @Query(type = Query.Type.INNER_LIKE)
    private String messageId;

    @Query(type = Query.Type.INNER_LIKE)
    private String mqttTopic;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> reportTime;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> receiveTime;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
