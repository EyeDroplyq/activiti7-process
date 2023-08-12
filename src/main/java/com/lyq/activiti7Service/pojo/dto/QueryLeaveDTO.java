package com.lyq.activiti7Service.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 15:04
 * @version: 1.0
 */
@Data
@ApiModel("查询请假申请列表的请求实体")
public class QueryLeaveDTO {
    private String title;
    private Integer status;
    private Integer current;
    private Integer size;
}
