/*
 * EMQX MQTT消息处理服务
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.iot.domain.Device;
import me.zhengjie.modules.iot.domain.DeviceStatus;
import me.zhengjie.modules.iot.repository.DeviceRepository;
import me.zhengjie.modules.iot.repository.DeviceStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * EMQX MQTT消息处理服务
 * @author IoT Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmqxMessageService {

    private final DeviceRepository deviceRepository;
    private final DeviceStatusRepository deviceStatusRepository;

    /**
     * 处理设备上报的消息
     * @param topic MQTT主题
     * @param payload 消息内容
     * @param messageId 消息ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleDeviceMessage(String topic, String payload, String messageId) {
        try {
            log.info("收到设备消息 - Topic: {}, MessageId: {}, Payload: {}", topic, messageId, payload);

            // 根据主题查找设备
            Optional<Device> deviceOpt = deviceRepository.findByMqttTopic(topic);
            if (!deviceOpt.isPresent()) {
                log.warn("未找到主题对应的设备: {}", topic);
                return;
            }

            Device device = deviceOpt.get();
            
            // 解析消息内容
            JSONObject messageData = JSON.parseObject(payload);
            
            // 更新设备状态
            updateDeviceStatus(device, messageData);
            
            // 保存设备状态记录
            saveDeviceStatusRecord(device, topic, payload, messageData, messageId);
            
            log.info("设备消息处理完成 - Device: {}, Status: {}", device.getDeviceCode(), device.getStatus());
            
        } catch (Exception e) {
            log.error("处理设备消息失败 - Topic: {}, MessageId: {}, Error: {}", topic, messageId, e.getMessage(), e);
        }
    }

    /**
     * 更新设备状态
     * @param device 设备
     * @param messageData 消息数据
     */
    private void updateDeviceStatus(Device device, JSONObject messageData) {
        // 更新最后在线时间
        device.setLastOnlineTime(new Timestamp(System.currentTimeMillis()));
        
        // 根据消息内容更新设备状态
        Integer status = messageData.getInteger("status");
        if (status != null) {
            device.setStatus(status);
        } else {
            // 默认设置为在线状态
            device.setStatus(1);
        }
        
        // 更新设备配置信息（如果有）
        String config = messageData.getString("config");
        if (config != null) {
            device.setDeviceConfig(config);
        }
        
        deviceRepository.save(device);
    }

    /**
     * 保存设备状态记录
     * @param device 设备
     * @param topic MQTT主题
     * @param rawPayload 原始消息
     * @param messageData 解析后的消息数据
     * @param messageId 消息ID
     */
    private void saveDeviceStatusRecord(Device device, String topic, String rawPayload, 
                                       JSONObject messageData, String messageId) {
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setDevice(device);
        deviceStatus.setMqttTopic(topic);
        deviceStatus.setMessageId(messageId);
        deviceStatus.setRawData(rawPayload);
        deviceStatus.setReceiveTime(new Timestamp(System.currentTimeMillis()));
        
        // 设置上报时间
        Long reportTime = messageData.getLong("timestamp");
        if (reportTime != null) {
            deviceStatus.setReportTime(new Timestamp(reportTime));
        } else {
            deviceStatus.setReportTime(new Timestamp(System.currentTimeMillis()));
        }
        
        // 设置设备状态
        Integer status = messageData.getInteger("status");
        if (status != null) {
            deviceStatus.setStatus(status);
            deviceStatus.setStatusDesc(getStatusDescription(status));
        } else {
            deviceStatus.setStatus(1);
            deviceStatus.setStatusDesc("在线");
        }
        
        // 设置解析后的数据
        deviceStatus.setParsedData(JSON.toJSONString(messageData));
        
        // 设置数据质量
        deviceStatus.setDataQuality(0); // 默认正常
        
        deviceStatusRepository.save(deviceStatus);
    }

    /**
     * 获取状态描述
     * @param status 状态码
     * @return 状态描述
     */
    private String getStatusDescription(Integer status) {
        switch (status) {
            case 0:
                return "离线";
            case 1:
                return "在线";
            case 2:
                return "故障";
            default:
                return "未知状态";
        }
    }

    /**
     * 处理设备离线事件
     * @param topic MQTT主题
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleDeviceOffline(String topic) {
        try {
            Optional<Device> deviceOpt = deviceRepository.findByMqttTopic(topic);
            if (deviceOpt.isPresent()) {
                Device device = deviceOpt.get();
                device.setStatus(0);
                device.setLastOfflineTime(new Timestamp(System.currentTimeMillis()));
                deviceRepository.save(device);
                
                log.info("设备离线 - Device: {}, Topic: {}", device.getDeviceCode(), topic);
            }
        } catch (Exception e) {
            log.error("处理设备离线事件失败 - Topic: {}, Error: {}", topic, e.getMessage(), e);
        }
    }
}
