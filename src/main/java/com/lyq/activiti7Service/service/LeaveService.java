package com.lyq.activiti7Service.service;

import com.lyq.activiti7Service.pojo.Leave;
import com.lyq.activiti7Service.pojo.dto.QueryLeaveDTO;
import com.lyq.activiti7Service.utils.Result;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:48
 * @version: 1.0
 */
public interface LeaveService {
    /**
     * 保存请假实体类
     *
     * @param leave:请假实体类
     * @return
     */
    Result addLeave(Leave leave);

    /**
     * 查询请假申请列表
     *
     * @param queryLeaveDTO:请假申请请求实体类
     * @return
     */
    Result list(QueryLeaveDTO queryLeaveDTO);

    /**
     * 根据id查询详情用来回显
     *
     * @param id
     * @return
     */
    Result getById(String id);

    /**
     * 更新请假信息
     * @param leave
     * @return
     */
    Result updateLeave(Leave leave);
}
