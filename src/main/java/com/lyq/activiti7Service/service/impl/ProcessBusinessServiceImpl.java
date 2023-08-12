package com.lyq.activiti7Service.service.impl;

import com.lyq.activiti7Service.mapper.ProcessBusinessMapper;
import com.lyq.activiti7Service.pojo.ProcessBusiness;
import com.lyq.activiti7Service.service.ProcessBusinessService;
import com.lyq.activiti7Service.utils.Result;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.lyq.activiti7Service.pojo.table.ProcessBusinessTableDef.PROCESS_BUSINESS;
import javax.annotation.Resource;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-09 23:28
 * @version: 1.0
 */
@Service
@Slf4j
public class ProcessBusinessServiceImpl implements ProcessBusinessService {
    @Resource
    private ProcessBusinessMapper processBusinessMapper;
    @Override
    public Result getProcessBusinessByKey(String key) {
        QueryWrapper queryWrapper=QueryWrapper.create().select(PROCESS_BUSINESS.BUSINESS_ROUTE,PROCESS_BUSINESS.FORM_NAME).from(PROCESS_BUSINESS).where(PROCESS_BUSINESS.PROCESS_KEY.eq(key));
        ProcessBusiness processBusiness = processBusinessMapper.selectOneByQuery(queryWrapper);
        return Result.ok(processBusiness);
    }

    @Override
    public int updateOrSaveProcessBusiness(ProcessBusiness processBusiness) {
        int row = processBusinessMapper.insertOrUpdate(processBusiness);
        return row;
    }

    @Override
    public void deleteProcessBusinessByKey(String key) {
        QueryWrapper query=QueryWrapper.create().select(PROCESS_BUSINESS.PROCESS_KEY).from(PROCESS_BUSINESS).where(PROCESS_BUSINESS.PROCESS_KEY.eq(key));
        processBusinessMapper.deleteByQuery(query);
    }
}
