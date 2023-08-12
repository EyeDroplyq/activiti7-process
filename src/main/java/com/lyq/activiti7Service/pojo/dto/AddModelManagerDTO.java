package com.lyq.activiti7Service.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-03 23:02
 * @version: 1.0
 */
@Data
@ApiModel("新增流程模型参数")
public class AddModelManagerDTO {
    @ApiModelProperty("模型名称")
    private String name;
    @ApiModelProperty("标识Key")
    private String key;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("当前页")
    private Integer current;
    @ApiModelProperty("每页大小")
    private Integer size;

}
