/*
 * IoT客户Service接口
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service;

import me.zhengjie.modules.iot.domain.Customer;
import me.zhengjie.modules.iot.service.dto.CustomerDto;
import me.zhengjie.modules.iot.service.dto.CustomerQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 客户服务接口
 * @author IoT Team
 */
public interface CustomerService {

    /**
     * 分页查询客户
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return 客户分页结果
     */
    PageResult<CustomerDto> queryAll(CustomerQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有客户
     * @param criteria 查询条件
     * @return 客户列表
     */
    List<CustomerDto> queryAll(CustomerQueryCriteria criteria);

    /**
     * 根据ID查询客户
     * @param id 客户ID
     * @return 客户信息
     */
    CustomerDto findById(Long id);

    /**
     * 创建客户
     * @param resources 客户信息
     * @return 客户信息
     */
    CustomerDto create(Customer resources);

    /**
     * 更新客户
     * @param resources 客户信息
     */
    void update(Customer resources);

    /**
     * 删除客户
     * @param ids 客户ID列表
     */
    void delete(Set<Long> ids);

    /**
     * 导出客户数据
     * @param all 所有客户数据
     * @param response HTTP响应
     */
    void download(List<CustomerDto> all, HttpServletResponse response);

    /**
     * 验证客户编码是否唯一
     * @param customerCode 客户编码
     * @param id 客户ID（更新时使用）
     * @return 是否唯一
     */
    boolean isCustomerCodeUnique(String customerCode, Long id);

    /**
     * 获取客户统计信息
     * @return 统计信息
     */
    Map<String, Object> getCustomerStatistics();
}
