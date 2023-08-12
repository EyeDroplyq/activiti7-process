package com.lyq.activiti7Service.service.impl;

import com.lyq.activiti7Service.pojo.dto.ProcessDefDTO;
import com.lyq.activiti7Service.service.ProcessDefService;
import com.lyq.activiti7Service.utils.DateUtils;
import com.lyq.activiti7Service.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-09 22:50
 * @version: 1.0
 */
@Service
@Slf4j
public class ProcessDefServiceImpl extends ActivitiBaseService implements ProcessDefService {
    
    @Override
    public Result list(ProcessDefDTO processDefDTO) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        String processDefName = processDefDTO.getName();
        String processDefKey = processDefDTO.getKey();
        if (StringUtils.isNotEmpty(processDefName)){
            query.processDefinitionName(processDefName);
        }
        if (StringUtils.isNotEmpty(processDefKey)){
            query.processDefinitionKey(processDefKey);
        }
        List<ProcessDefinition> processDefList = query.latestVersion().listPage(processDefDTO.getCurrent() - 1, processDefDTO.getSize());
        //最终返回给前端的结果
        Map<String,Object> result=new HashMap<>();
        long total = query.count();
        result.put("total",total);
        List<Map<String,Object>> records=new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefList) {
            Map<String,Object> record=new HashMap<>();
            record.put("id",processDefinition.getId());
            record.put("name",processDefinition.getName());
            record.put("key",processDefinition.getKey());
            record.put("state",processDefinition.isSuspended()?"已暂停":"已启动");
            record.put("xmlName",processDefinition.getResourceName());
            record.put("pngName",processDefinition.getDiagramResourceName());
            record.put("version",processDefinition.getVersion());
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            Date deploymentTime = deployment.getDeploymentTime();
            record.put("deploymentTime", DateUtils.format(deploymentTime));
            records.add(record);
        }
        result.put("records",records);
        return Result.ok(result);
    }
}
