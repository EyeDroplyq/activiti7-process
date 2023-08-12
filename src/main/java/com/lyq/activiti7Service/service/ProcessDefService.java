package com.lyq.activiti7Service.service;

import com.lyq.activiti7Service.pojo.dto.ProcessDefDTO;
import com.lyq.activiti7Service.utils.Result;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-09 22:50
 * @version: 1.0
 */
public interface ProcessDefService {
    /**
     * 查询流程定义列表
     * @param processDefDTO：查询请求实体类，里面有查询的参数
     * @return Result.ok(data)
     */
    Result list(ProcessDefDTO processDefDTO);

    /**
     * 根据流程定义id 激活或者挂起流程实例
     * @param definitionId 流程定义id
     * @return
     */
    Result suspendOrActivitiModel(String definitionId);

    /**
     * 通过流程部署id来删除流程定义
     * @param deploymentId：流程部署id
     * @param key：流程定义的key
     * @return
     */
    Result deleteProcessDefByDeploymentId(String deploymentId,String key);
}
