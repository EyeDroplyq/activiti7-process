package com.lyq.activiti7Service.service.impl;

import com.lyq.activiti7Service.mapper.BusinessStatusMapper;
import com.lyq.activiti7Service.pojo.BusinessStatus;
import com.lyq.activiti7Service.service.BusinessStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:52
 * @version: 1.0
 */
@Service
@Slf4j
public class BusinessStatusServiceImpl implements BusinessStatusService {
    @Resource
    private BusinessStatusMapper businessStatusMapper;

    @Override
    public void addBusinessStatus(BusinessStatus businessStatus) {
        businessStatusMapper.insert(businessStatus);
    }
}
