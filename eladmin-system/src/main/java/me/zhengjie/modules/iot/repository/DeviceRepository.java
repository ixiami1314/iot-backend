/*
 * IoT设备Repository接口
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.repository;

import me.zhengjie.modules.iot.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * 设备数据访问层
 * @author IoT Team
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    /**
     * 根据设备编码查找设备
     * @param deviceCode 设备编码
     * @return 设备信息
     */
    Optional<Device> findByDeviceCode(String deviceCode);

    /**
     * 根据客户ID查找设备列表
     * @param customerId 客户ID
     * @return 设备列表
     */
    List<Device> findByCustomerId(Long customerId);

    /**
     * 根据设备状态查找设备列表
     * @param status 设备状态
     * @return 设备列表
     */
    List<Device> findByStatus(Integer status);

    /**
     * 根据EMQX主题查找设备
     * @param mqttTopic EMQX主题
     * @return 设备信息
     */
    Optional<Device> findByMqttTopic(String mqttTopic);

    /**
     * 查找所有在线设备
     * @return 在线设备列表
     */
    @Query("SELECT d FROM Device d WHERE d.status = 1")
    List<Device> findAllOnline();

    /**
     * 查找所有离线设备
     * @return 离线设备列表
     */
    @Query("SELECT d FROM Device d WHERE d.status = 0")
    List<Device> findAllOffline();

    /**
     * 检查设备编码是否存在
     * @param deviceCode 设备编码
     * @param id 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    @Query("SELECT COUNT(d) > 0 FROM Device d WHERE d.deviceCode = ?1 AND d.id != ?2")
    boolean existsByDeviceCodeAndIdNot(String deviceCode, Long id);

    /**
     * 统计客户设备数量
     * @param customerId 客户ID
     * @return 设备数量
     */
    @Query("SELECT COUNT(d) FROM Device d WHERE d.customer.id = :customerId")
    Long countByCustomerId(@Param("customerId") Long customerId);

    /**
     * 统计各状态设备数量
     * @return 状态统计结果
     */
    @Query("SELECT d.status, COUNT(d) FROM Device d GROUP BY d.status")
    List<Object[]> countByStatus();
}
