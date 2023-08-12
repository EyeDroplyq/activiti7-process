package com.lyq.activiti7Service.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 模型管理查询参数
 * @author: lyq
 * @createDate: 2023-08-03 23:00
 * @version: 1.0
 */
@Data
@ApiModel("模型管理查询参数")
public class ModelManagerDTO {
    @ApiModelProperty("模型名称")
    private String name;
    @ApiModelProperty("标识Key")
    private String key;
    @ApiModelProperty("当前页")
    private Integer current;
    @ApiModelProperty("一页的大小")
    private Integer size;
}
