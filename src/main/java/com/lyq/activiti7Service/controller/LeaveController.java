package com.lyq.activiti7Service.controller;

import com.lyq.activiti7Service.pojo.Leave;
import com.lyq.activiti7Service.pojo.dto.QueryLeaveDTO;
import com.lyq.activiti7Service.service.LeaveService;
import com.lyq.activiti7Service.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:37
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/leave")
public class LeaveController {
    @Resource
    private LeaveService leaveService;

    @PostMapping()
    public Result addLeave(@RequestBody Leave leave) {
        return leaveService.addLeave(leave);
    }

    @PostMapping("/list")
    public Result list(@RequestBody QueryLeaveDTO queryLeaveDTO) {
        return leaveService.list(queryLeaveDTO);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") String id) {
        return leaveService.getById(id);
    }

    @PutMapping("")
    public Result updateLeave(@RequestBody Leave leave){
        return leaveService.updateLeave(leave);
    }
}
