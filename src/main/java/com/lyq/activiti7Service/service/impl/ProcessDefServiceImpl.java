package com.lyq.activiti7Service.service.impl;

import com.google.common.collect.Lists;
import com.lyq.activiti7Service.pojo.ProcessBusiness;
import com.lyq.activiti7Service.pojo.dto.ProcessDefDTO;
import com.lyq.activiti7Service.service.ProcessBusinessService;
import com.lyq.activiti7Service.service.ProcessDefService;
import com.lyq.activiti7Service.utils.DateUtils;
import com.lyq.activiti7Service.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-09 22:50
 * @version: 1.0
 */
@Service
@Slf4j
public class ProcessDefServiceImpl extends ActivitiBaseService implements ProcessDefService {
    @Resource
    private ProcessBusinessService processBusinessService;

    @Override
    public Result list(ProcessDefDTO processDefDTO) {
        //最终返回给前端的结果
        Map<String, Object> result = new HashMap<>();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        String processDefName = processDefDTO.getName();
        String processDefKey = processDefDTO.getKey();
        if (StringUtils.isNotEmpty(processDefName)) {
            query.processDefinitionName(processDefName);
        }
        if (StringUtils.isNotEmpty(processDefKey)) {
            query.processDefinitionKey(processDefKey);
        }
        List<ProcessDefinition> processDefList = query.latestVersion().listPage(processDefDTO.getCurrent() - 1, processDefDTO.getSize());
        Result processBusinessResult = processBusinessService.getProcessBusinessByKey(processDefKey);
        if (processBusinessResult.getData() != null) {
            ProcessBusiness processBusiness = (ProcessBusiness) processBusinessResult.getData();
            result = new HashMap<>();
            long total = query.count();
            result.put("total", total);
            List<Map<String, Object>> records = new ArrayList<>();
            for (ProcessDefinition processDefinition : processDefList) {
                Map<String, Object> record = new HashMap<>();
                record.put("id", processDefinition.getId());
                record.put("deploymentId", processDefinition.getDeploymentId());
                record.put("name", processDefinition.getName());
                record.put("key", processDefinition.getKey());
                record.put("state", processDefinition.isSuspended() ? "已暂停" : "已启动");
                record.put("xmlName", processDefinition.getResourceName());
                record.put("pngName", processDefinition.getDiagramResourceName());
                record.put("version", processDefinition.getVersion());
                record.put("businessRoute", processBusiness.getBusinessRoute());
                record.put("formName", processBusiness.getFormName());
                String deploymentId = processDefinition.getDeploymentId();
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
                Date deploymentTime = deployment.getDeploymentTime();
                record.put("deploymentTime", DateUtils.format(deploymentTime));
                records.add(record);
            }
            result.put("records", records);
        } else {
            ProcessBusiness processBusiness = (ProcessBusiness) processBusinessResult.getData();
            long total = query.count();
            result.put("total", total);
            List<Map<String, Object>> records = new ArrayList<>();
            for (ProcessDefinition processDefinition : processDefList) {
                Map<String, Object> record = new HashMap<>();
                record.put("id", processDefinition.getId());
                record.put("deploymentId", processDefinition.getDeploymentId());
                record.put("name", processDefinition.getName());
                record.put("key", processDefinition.getKey());
                record.put("state", processDefinition.isSuspended() ? "已暂停" : "已启动");
                record.put("xmlName", processDefinition.getResourceName());
                record.put("pngName", processDefinition.getDiagramResourceName());
                record.put("version", processDefinition.getVersion());
                String deploymentId = processDefinition.getDeploymentId();
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
                Date deploymentTime = deployment.getDeploymentTime();
                record.put("deploymentTime", DateUtils.format(deploymentTime));
                records.add(record);
            }
            result.put("records", records);
        }
        return Result.ok(result);
    }

    @Override
    public Result suspendOrActivitiModel(String definitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).singleResult();
        if (processDefinition == null) {
            return Result.error("流程定义不存在");
        }
        if (processDefinition.isSuspended()) {
            //如果流程挂起了，就激活
            repositoryService.activateProcessDefinitionById(definitionId);
        } else {
            //如果激活了就挂起
            repositoryService.suspendProcessDefinitionById(definitionId);
        }
        return Result.ok();
    }

    @Override
    public Result deleteProcessDefByDeploymentId(String deploymentId, String key) {
        repositoryService.deleteDeployment(deploymentId);
        List<ProcessDefinition> processDefList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list();
        if (processDefList.isEmpty()) {
            processBusinessService.deleteProcessBusinessByKey(key);
        }
        return Result.ok();
    }

    @Override
    public void export(String type, String definitionId, HttpServletResponse response) {
        try {
            String fileName = "文件不存在";
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(definitionId).singleResult();
            if ("xml".equals(type)) {
                response.setContentType("application/xml");
                String resourceName = processDefinition.getResourceName();
                if (StringUtils.isNotEmpty(resourceName)) {
                    fileName = resourceName;
                }
            }
            if ("png".equals(type)) {
                response.setContentType("image/png");
                String diagramResourceName = processDefinition.getDiagramResourceName();
                if (StringUtils.isNotEmpty(diagramResourceName)) {
                    fileName = diagramResourceName;
                }
            }
            InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), fileName);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //这句必须放在setHeader下面，否则超过10k的文件不能导出
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Result fileDeploy(MultipartFile file) {
        try {
            if (file.isEmpty() || file == null) {
                return Result.error("上传的文件为空");
            }
            //得到完整的文件名
            String fileName = file.getOriginalFilename();
            //后缀
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            //文件名
            String prefix = fileName.substring(0, fileName.lastIndexOf("."));
            DeploymentBuilder deployment = repositoryService.createDeployment();
            if (suffix.equals("zip")) {
                deployment.addZipInputStream(new ZipInputStream(file.getInputStream()));
            } else {
                deployment.addInputStream(fileName, file.getInputStream());
            }
            deployment.name(prefix);
            deployment.deploy();
            return Result.ok();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
