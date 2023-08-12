package com.lyq.activiti7Service.service;

import com.lyq.activiti7Service.pojo.ProcessBusiness;
import com.lyq.activiti7Service.utils.Result;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-09 23:28
 * @version: 1.0
 */
public interface ProcessBusinessService {
    /**
     * 根据流程定义key查询流程定义-业务关联信息
     * @param key:流程定义key
     * @return ProcessBusiness
     */
    Result getProcessBusinessByKey(String key);

    /**
     * 更新或者保存 流程定义-业务关联信息
     * @param processBusiness 流程定义-业务关联实体类
     */
    int updateOrSaveProcessBusiness(ProcessBusiness processBusiness);
}
