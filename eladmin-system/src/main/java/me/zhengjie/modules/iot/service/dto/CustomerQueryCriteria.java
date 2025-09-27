/*
 * IoT客户查询条件
 * @author IoT Team
 * @date 2024
 */
package me.zhengjie.modules.iot.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
 * 客户查询条件
 * @author IoT Team
 */
@Data
public class CustomerQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String customerCode;

    @Query(type = Query.Type.INNER_LIKE)
    private String customerName;

    @Query(type = Query.Type.INNER_LIKE)
    private String contactPerson;

    @Query(type = Query.Type.INNER_LIKE)
    private String contactPhone;

    @Query(type = Query.Type.EQUAL)
    private Integer status;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query(type = Query.Type.INNER_LIKE)
    private String address;
}
