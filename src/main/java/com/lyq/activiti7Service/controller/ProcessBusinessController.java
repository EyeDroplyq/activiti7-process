package com.lyq.activiti7Service.controller;

import com.lyq.activiti7Service.pojo.ProcessBusiness;
import com.lyq.activiti7Service.service.ProcessBusinessService;
import com.lyq.activiti7Service.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-09 23:27
 * @version: 1.0
 */
@RestController
@RequestMapping("/processConfig")
@Api("流程定义-业务关联接口")
public class ProcessBusinessController {
    @Resource
    private ProcessBusinessService processBusinessService;

    @GetMapping("/{key}")
    public Result getProcessBusinessByKey(@PathVariable("key") String key) {
        return processBusinessService.getProcessBusinessByKey(key);
    }

    @PutMapping()
    public Result updateOrSaveProcessBusiness(@RequestBody ProcessBusiness processBusiness) {
        int row = processBusinessService.updateOrSaveProcessBusiness(processBusiness);
        if (row > 0) {
            return Result.ok();
        }
        return Result.error("更新或保存流程定义-业务关联数据失败");
    }
}
