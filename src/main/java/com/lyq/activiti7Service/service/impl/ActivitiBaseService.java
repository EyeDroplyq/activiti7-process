package com.lyq.activiti7Service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import javax.annotation.Resource;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-03 23:04
 * @version: 1.0
 */
public class ActivitiBaseService {
    @Resource
    public ObjectMapper objectMapper;
    @Resource
    public TaskService taskService;
    @Resource
    public RuntimeService runtimeService;
    @Resource
    public HistoryService historyService;
    @Resource
    public ProcessRuntime processRuntime;
    @Resource
    public RepositoryService repositoryService;
    @Resource
    public TaskRuntime taskRuntime;
}
