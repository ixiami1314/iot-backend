/*
 * IoT客户Repository接口
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.repository;

import me.zhengjie.modules.iot.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * 客户数据访问层
 * @author IoT Team
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 根据客户编码查找客户
     * @param customerCode 客户编码
     * @return 客户信息
     */
    Optional<Customer> findByCustomerCode(String customerCode);

    /**
     * 根据客户名称查找客户
     * @param customerName 客户名称
     * @return 客户信息
     */
    Optional<Customer> findByCustomerName(String customerName);

    /**
     * 查找所有启用的客户
     * @return 启用客户列表
     */
    @Query("SELECT c FROM Customer c WHERE c.status = 1")
    List<Customer> findAllEnabled();

    /**
     * 检查客户编码是否存在
     * @param customerCode 客户编码
     * @param id 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.customerCode = ?1 AND c.id != ?2")
    boolean existsByCustomerCodeAndIdNot(String customerCode, Long id);

    Long countByCustomerId(Long id);
}
