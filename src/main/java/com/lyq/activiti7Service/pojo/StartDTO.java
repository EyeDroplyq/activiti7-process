package com.lyq.activiti7Service.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 20:21
 * @version: 1.0
 */
@Data
@ApiModel("启动流程的参数实体类")
public class StartDTO {
    @ApiModelProperty("业务路由")
    private String businessRoute;
    @ApiModelProperty("业务id")
    private String businessKey;
    @ApiModelProperty("分配人")
    private List<String> assignees;
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        return variables == null ? new HashMap<>() : variables;
    }
}
