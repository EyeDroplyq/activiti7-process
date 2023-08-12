package com.lyq.activiti7Service.controller;

import com.lyq.activiti7Service.pojo.dto.ProcessDefDTO;
import com.lyq.activiti7Service.service.ProcessDefService;
import com.lyq.activiti7Service.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class ProcessDefController {
    @Resource
    private ProcessDefService processDefService;

    @ApiOperation("查询流程定义列表")
    @PostMapping("/list")
    public Result list(@RequestBody ProcessDefDTO processDefDTO) {
        return processDefService.list(processDefDTO);
    }

    @ApiOperation("激活或者挂起流程定义")
    @PutMapping("/state/{definitionId}")
    public Result suspendOrActivitiModel(@PathVariable("definitionId") String definitionId) {
        return processDefService.suspendOrActivitiModel(definitionId);
    }

    @ApiOperation("根据部署id来删除流程定义")
    @DeleteMapping("/{deploymentId}")
    public Result deleteProcessDefByDeploymentId(@PathVariable("deploymentId") String deploymentId, @RequestParam("key") String key) {
        try {
            return processDefService.deleteProcessDefByDeploymentId(deploymentId, key);
        } catch (Exception e) {
            String msg = e.getMessage();
            log.error(msg);
            if (StringUtils.contains(msg, "a foreign key constraint fails")) {
                return Result.error("有正在执行的流程,删除失败");
            } else {
                return Result.error("删除失败，失败原因为:" + msg);
            }
        }
    }
}
