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
}
