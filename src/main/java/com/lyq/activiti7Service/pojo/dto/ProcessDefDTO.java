package com.lyq.activiti7Service.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-09 22:47
 * @version: 1.0
 */
@Data
@ApiModel("查询流程定义信息实体类")
public class ProcessDefDTO {
    @ApiModelProperty("流程定义的名字")
    private String name;
    @ApiModelProperty("流程定义的key")
    private String key;
    @ApiModelProperty("当前页")
    private Integer current;
    @ApiModelProperty("每页的大小")
    private Integer size;
}
