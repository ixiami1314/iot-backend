/*
 * IoT客户Controller
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.iot.domain.Customer;
import me.zhengjie.modules.iot.service.CustomerService;
import me.zhengjie.modules.iot.service.dto.CustomerDto;
import me.zhengjie.modules.iot.service.dto.CustomerQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 客户管理控制器
 * @author IoT Team
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "IoT：客户管理")
@RequestMapping("/api/iot/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Log("查询客户")
    @ApiOperation("查询客户")
    @GetMapping
    @PreAuthorize("@el.check('customer:list')")
    public ResponseEntity<PageResult<CustomerDto>> queryCustomer(CustomerQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(customerService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询客户")
    @ApiOperation("查询客户")
    @GetMapping("/all")
    @PreAuthorize("@el.check('customer:list')")
    public ResponseEntity<List<CustomerDto>> queryAllCustomer(CustomerQueryCriteria criteria) {
        return new ResponseEntity<>(customerService.queryAll(criteria), HttpStatus.OK);
    }

    @Log("新增客户")
    @ApiOperation("新增客户")
    @PostMapping
    @PreAuthorize("@el.check('customer:add')")
    public ResponseEntity<CustomerDto> createCustomer(@Validated @RequestBody Customer resources) {
        return new ResponseEntity<>(customerService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改客户")
    @ApiOperation("修改客户")
    @PutMapping
    @PreAuthorize("@el.check('customer:edit')")
    public ResponseEntity<HttpStatus> updateCustomer(@Validated @RequestBody Customer resources) {
        customerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除客户")
    @ApiOperation("删除客户")
    @DeleteMapping
    @PreAuthorize("@el.check('customer:del')")
    public ResponseEntity<HttpStatus> deleteCustomer(@RequestBody Set<Long> ids) {
        customerService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("导出客户数据")
    @ApiOperation("导出客户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('customer:list')")
    public void downloadCustomer(HttpServletResponse response, CustomerQueryCriteria criteria) throws IOException {
        customerService.download(customerService.queryAll(criteria), response);
    }

    @Log("查询客户详情")
    @ApiOperation("查询客户详情")
    @GetMapping("/{id}")
    @PreAuthorize("@el.check('customer:list')")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @Log("验证客户编码唯一性")
    @ApiOperation("验证客户编码唯一性")
    @GetMapping("/check-code")
    @PreAuthorize("@el.check('customer:list')")
    public ResponseEntity<Boolean> checkCustomerCode(@RequestParam String customerCode, 
                                                   @RequestParam(required = false) Long id) {
        return new ResponseEntity<>(customerService.isCustomerCodeUnique(customerCode, id), HttpStatus.OK);
    }

    @Log("获取客户统计信息")
    @ApiOperation("获取客户统计信息")
    @GetMapping("/statistics")
    @PreAuthorize("@el.check('customer:list')")
    public ResponseEntity<Map<String, Object>> getCustomerStatistics() {
        return new ResponseEntity<>(customerService.getCustomerStatistics(), HttpStatus.OK);
    }
}
