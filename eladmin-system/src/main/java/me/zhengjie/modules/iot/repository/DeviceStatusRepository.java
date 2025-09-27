/*
 * IoT设备状态Repository接口
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.repository;

import me.zhengjie.modules.iot.domain.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * 设备状态数据访问层
 * @author IoT Team
 */
@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long>, JpaSpecificationExecutor<DeviceStatus> {

    /**
     * 根据设备ID查找最新的状态记录
     * @param deviceId 设备ID
     * @return 最新状态记录
     */
    @Query("SELECT ds FROM DeviceStatus ds WHERE ds.device.id = :deviceId ORDER BY ds.reportTime DESC")
    List<DeviceStatus> findLatestByDeviceId(@Param("deviceId") Long deviceId);

    /**
     * 根据设备ID查找指定时间范围内的状态记录
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 状态记录列表
     */
    @Query("SELECT ds FROM DeviceStatus ds WHERE ds.device.id = :deviceId AND ds.reportTime BETWEEN :startTime AND :endTime ORDER BY ds.reportTime DESC")
    List<DeviceStatus> findByDeviceIdAndTimeRange(@Param("deviceId") Long deviceId, 
                                                  @Param("startTime") java.sql.Timestamp startTime, 
                                                  @Param("endTime") java.sql.Timestamp endTime);

    /**
     * 根据EMQX消息ID查找状态记录
     * @param messageId EMQX消息ID
     * @return 状态记录
     */
    Optional<DeviceStatus> findByMessageId(String messageId);

    /**
     * 根据设备状态查找记录
     * @param status 设备状态
     * @return 状态记录列表
     */
    List<DeviceStatus> findByStatus(Integer status);

    /**
     * 统计设备状态变化次数
     * @param deviceId 设备ID
     * @return 状态变化次数
     */
    @Query("SELECT COUNT(ds) FROM DeviceStatus ds WHERE ds.device.id = :deviceId")
    Long countByDeviceId(@Param("deviceId") Long deviceId);

    /**
     * 查找设备最后一条状态记录
     * @param deviceId 设备ID
     * @return 最后状态记录
     */
    @Query("SELECT ds FROM DeviceStatus ds WHERE ds.device.id = :deviceId ORDER BY ds.reportTime DESC LIMIT 1")
    Optional<DeviceStatus> findLastByDeviceId(@Param("deviceId") Long deviceId);
}
