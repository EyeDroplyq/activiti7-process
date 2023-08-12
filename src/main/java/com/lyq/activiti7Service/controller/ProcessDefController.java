package com.lyq.activiti7Service.controller;

import com.lyq.activiti7Service.pojo.dto.ProcessDefDTO;
import com.lyq.activiti7Service.service.ProcessDefService;
import com.lyq.activiti7Service.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-09 22:49
 * @version: 1.0
 */
@RestController
@RequestMapping("/process")
@Api("流程定义接口")
public class ProcessDefController {
    @Resource
    private ProcessDefService processDefService;

    @PostMapping("/list")
    public Result list(@RequestBody ProcessDefDTO processDefDTO){
        return processDefService.list(processDefDTO);
    }
}
