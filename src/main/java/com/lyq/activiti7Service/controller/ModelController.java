package com.lyq.activiti7Service.controller;

import com.lyq.activiti7Service.pojo.dto.AddModelManagerDTO;
import com.lyq.activiti7Service.pojo.dto.ModelManagerDTO;
import com.lyq.activiti7Service.service.ModelService;
import com.lyq.activiti7Service.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.regexp.RE;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-04 20:45
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/model")
@Api("创建model的Controller")
public class ModelController {
    @Resource
    private ModelService modelService;

    @ApiOperation("新增流程模型")
    @PostMapping
    public Result createModel(@RequestBody AddModelManagerDTO addModelManagerDTO) {
        try {
            return modelService.createModel(addModelManagerDTO);
        } catch (Exception e) {
            log.error("创建流程失败!!");
            return Result.error("创建模型失败");
        }
    }

    @ApiOperation("分页查询流程定义列表")
    @PostMapping("/list")
    public Result list(@RequestBody ModelManagerDTO modelManagerDTO) {
        try {
            return modelService.list(modelManagerDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询流程模型列表失败", e.getMessage());
        }
        return Result.error("查询流程模型列表失败");
    }

    @ApiOperation("部署流程模型")
    @PostMapping("/deploy/{modelId}")
    public Result deploy(@PathVariable("modelId") String modelId) {
        try {
            return modelService.deploy(modelId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Result.error("流程部署失败");
    }

    @ApiOperation("导出流程定义信息")
    @GetMapping("/export/zip/{modelId}")
    public void exportZip(@PathVariable("modelId") String modelId, HttpServletResponse response) {
        try {
            modelService.exportZip(modelId, response);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @ApiOperation("删除流程")
    @DeleteMapping("/{modelId}")
    public Result deleteModelById(@PathVariable("modelId") String modelId) {
        modelService.deleteModelById(modelId);
        return Result.ok();
    }
}
