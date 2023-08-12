package com.lyq.activiti7Service.service.impl;

import com.lyq.activiti7Service.enums.BusinessStatusEnum;
import com.lyq.activiti7Service.enums.LeaveTypeEnum;
import com.lyq.activiti7Service.mapper.LeaveMapper;
import com.lyq.activiti7Service.pojo.BusinessStatus;
import com.lyq.activiti7Service.pojo.Leave;
import com.lyq.activiti7Service.pojo.dto.QueryLeaveDTO;
import com.lyq.activiti7Service.service.BusinessStatusService;
import com.lyq.activiti7Service.service.LeaveService;
import com.lyq.activiti7Service.utils.Result;
import com.lyq.activiti7Service.utils.UserUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.lyq.activiti7Service.pojo.table.LeaveTableDef.LEAVE;
import static com.lyq.activiti7Service.pojo.table.BusinessStatusTableDef.BUSINESS_STATUS;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:48
 * @version: 1.0
 */
@Service
@Slf4j
public class LeaveServiceImpl implements LeaveService {
    @Resource
    private LeaveMapper leaveMapper;
    @Resource
    private BusinessStatusService businessStatusService;

    @Override
    public Result addLeave(Leave leave) {
        leave.setUsername(UserUtils.getUsername());
        int row = leaveMapper.insert(leave);
        if (row > 0) {
            //保存业务状态信息
            BusinessStatus businessStatus = new BusinessStatus();
            businessStatus.setBusinessKey(leave.getId());
            businessStatus.setStatus(BusinessStatusEnum.WAIT.getCode());
            businessStatusService.addBusinessStatus(businessStatus);
            return Result.ok();
        } else {
            return Result.error("新建请假流程失败");
        }
    }

    @Override
    public Result list(QueryLeaveDTO queryLeaveDTO) {
        Map<String, Object> result = new HashMap<>();
        Page<Leave> page = new Page<>();
        if (StringUtils.isNotEmpty(queryLeaveDTO.getTitle()) && queryLeaveDTO.getStatus() != null) {
            QueryWrapper query = QueryWrapper.create()
                    .select(LEAVE.ALL_COLUMNS, BUSINESS_STATUS.STATUS, BUSINESS_STATUS.PROCESS_INSTANCE_ID)
                    .from(LEAVE).leftJoin(BUSINESS_STATUS)
                    .on(LEAVE.ID.eq(BUSINESS_STATUS.BUSINESS_KEY))
                    .where(LEAVE.TITLE.eq(queryLeaveDTO.getTitle()).and(BUSINESS_STATUS.STATUS.eq(queryLeaveDTO.getStatus())));
            page = leaveMapper.paginate(queryLeaveDTO.getCurrent(), queryLeaveDTO.getSize(), query);
        } else if (StringUtils.isEmpty(queryLeaveDTO.getTitle()) || queryLeaveDTO.getStatus() == null) {
            QueryWrapper query = QueryWrapper.create().select(LEAVE.ALL_COLUMNS, BUSINESS_STATUS.STATUS, BUSINESS_STATUS.PROCESS_INSTANCE_ID).from(LEAVE).leftJoin(BUSINESS_STATUS).on(LEAVE.ID.eq(BUSINESS_STATUS.BUSINESS_KEY)).where(LEAVE.TITLE.eq(queryLeaveDTO.getTitle()).or(BUSINESS_STATUS.STATUS.eq(queryLeaveDTO.getStatus())));
            page = leaveMapper.paginate(queryLeaveDTO.getCurrent(), queryLeaveDTO.getSize(), query);
        } else {
            QueryWrapper query = QueryWrapper.create().select(LEAVE.ALL_COLUMNS, BUSINESS_STATUS.STATUS, BUSINESS_STATUS.PROCESS_INSTANCE_ID).from(LEAVE).leftJoin(BUSINESS_STATUS).on(LEAVE.ID.eq(BUSINESS_STATUS.BUSINESS_KEY));
            page = leaveMapper.paginate(queryLeaveDTO.getCurrent(), queryLeaveDTO.getSize(), query);
        }
        long total = page.getTotalRow();
        result.put("total", total);
        List<Leave> recordsList = page.getRecords();
        List<Map<String, Object>> records = new ArrayList<>();
        for (Leave record : recordsList) {
            Map<String, Object> recordMap = new HashMap<>();
            recordMap.put("id",record.getId());
            recordMap.put("username", record.getUsername());
            recordMap.put("title", record.getTitle());
            recordMap.put("leaveType", record.getLeaveType());
            recordMap.put("leaveTypeStr", LeaveTypeEnum.getEumByCode(record.getLeaveType()).getDesc());
            recordMap.put("startDateStr", record.getStartDateStr());
            recordMap.put("createDateStr", record.getCreateDateStr());
            recordMap.put("endDateStr", record.getEndDate());
            recordMap.put("status", record.getStatus());
            recordMap.put("statusStr", BusinessStatusEnum.getEumByCode(record.getStatus()).getDesc());
            records.add(recordMap);
        }
        result.put("records", records);
        return Result.ok(result);
    }

    @Override
    public Result getById(String id) {
        Leave leave = leaveMapper.selectOneById(id);
        return Result.ok(leave);
    }

    @Override
    public Result updateLeave(Leave leave) {
        if (leave == null || StringUtils.isEmpty(leave.getId())) {
            return Result.error("更新请假流程信息失败");
        }
        leaveMapper.update(leave);
        return Result.ok();
    }
}
