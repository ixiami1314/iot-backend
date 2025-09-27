/*
 * IoT客户Service实现类
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.iot.domain.Customer;
import me.zhengjie.modules.iot.repository.CustomerRepository;
import me.zhengjie.modules.iot.service.CustomerService;
import me.zhengjie.modules.iot.service.dto.CustomerDto;
import me.zhengjie.modules.iot.service.dto.CustomerQueryCriteria;
import me.zhengjie.modules.iot.service.mapstruct.CustomerMapper;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户服务实现类
 * @author IoT Team
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public PageResult<CustomerDto> queryAll(CustomerQueryCriteria criteria, Pageable pageable) {
        Page<Customer> page = customerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        
        List<CustomerDto> customerDtos = page.getContent().stream()
            .map(customer -> {
                CustomerDto dto = customerMapper.toDto(customer);
                // 设置设备数量
                dto.setDeviceCount(customerRepository.countByCustomerId(customer.getId()));
                return dto;
            })
            .collect(Collectors.toList());
        
        return PageUtil.toPage(customerDtos, page.getTotalElements());
    }

    @Override
    public List<CustomerDto> queryAll(CustomerQueryCriteria criteria) {
        List<Customer> customers = customerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> 
            QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return customerMapper.toDto(customers);
    }

    @Override
    public CustomerDto findById(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("客户不存在"));
        CustomerDto dto = customerMapper.toDto(customer);
        dto.setDeviceCount(customerRepository.countByCustomerId(id));
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerDto create(Customer resources) {
        // 验证客户编码唯一性
        if (customerRepository.existsByCustomerCodeAndIdNot(resources.getCustomerCode(), 0L)) {
            throw new EntityExistException(Customer.class, "customerCode", resources.getCustomerCode());
        }
        
        // 验证客户名称唯一性
        if (customerRepository.findByCustomerName(resources.getCustomerName()).isPresent()) {
            throw new EntityExistException(Customer.class, "customerName", resources.getCustomerName());
        }
        
        Customer customer = customerRepository.save(resources);
        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Customer resources) {
        Customer customer = customerRepository.findById(resources.getId())
            .orElseThrow(() -> new BadRequestException("客户不存在"));
        
        // 验证客户编码唯一性
        if (customerRepository.existsByCustomerCodeAndIdNot(resources.getCustomerCode(), resources.getId())) {
            throw new EntityExistException(Customer.class, "customerCode", resources.getCustomerCode());
        }
        
        customer.setCustomerCode(resources.getCustomerCode());
        customer.setCustomerName(resources.getCustomerName());
        customer.setDescription(resources.getDescription());
        customer.setContactPerson(resources.getContactPerson());
        customer.setContactPhone(resources.getContactPhone());
        customer.setContactEmail(resources.getContactEmail());
        customer.setAddress(resources.getAddress());
        customer.setStatus(resources.getStatus());
        customer.setEmqxConfig(resources.getEmqxConfig());
        customer.setRemark(resources.getRemark());
        
        customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("客户不存在"));
            
            // 检查是否有关联设备
            Long deviceCount = customerRepository.countByCustomerId(id);
            if (deviceCount > 0) {
                throw new BadRequestException("客户下存在设备，无法删除");
            }
            
            customerRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CustomerDto> all, HttpServletResponse response) {
        // TODO: 实现Excel导出功能
        throw new BadRequestException("导出功能待实现");
    }

    @Override
    public boolean isCustomerCodeUnique(String customerCode, Long id) {
        if (!StringUtils.hasText(customerCode)) {
            return false;
        }
        return !customerRepository.existsByCustomerCodeAndIdNot(customerCode, id);
    }

    @Override
    public Map<String, Object> getCustomerStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 总客户数
        long totalCustomers = customerRepository.count();
        statistics.put("totalCustomers", totalCustomers);
        
        // 启用客户数
        long enabledCustomers = customerRepository.findAllEnabled().size();
        statistics.put("enabledCustomers", enabledCustomers);
        
        // 禁用客户数
        statistics.put("disabledCustomers", totalCustomers - enabledCustomers);
        
        return statistics;
    }
}
