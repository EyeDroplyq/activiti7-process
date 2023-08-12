package com.lyq.activiti7Service.service;

import com.lyq.activiti7Service.pojo.BusinessStatus;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:52
 * @version: 1.0
 */
public interface BusinessStatusService {
    /**
     * 保存业务状态信息
     * @param businessStatus:业务状态实体类
     */
    void addBusinessStatus(BusinessStatus businessStatus);
}
