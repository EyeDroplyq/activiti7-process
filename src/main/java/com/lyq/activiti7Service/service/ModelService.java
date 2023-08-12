package com.lyq.activiti7Service.service;

import com.lyq.activiti7Service.pojo.dto.AddModelManagerDTO;
import com.lyq.activiti7Service.pojo.dto.ModelManagerDTO;
import com.lyq.activiti7Service.utils.Result;
import org.activiti.engine.repository.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-04 20:55
 * @version: 1.0
 */
public interface ModelService {
    Result createModel(AddModelManagerDTO addModelManagerDTO);

    Result list(ModelManagerDTO modelManagerDTO);

    /**
     * 流程部署
     * @param modelId:流程模型id
     * @return
     */
    Result deploy(String modelId) throws IOException;

    /**
     * 导出流程定义信息
     * @param modelId
     * @param response
     */
    void exportZip(String modelId, HttpServletResponse response) throws IOException;

    /**
     * 删除流程模型
     * @param modelId：流程模型id
     */
    void deleteModelById(String modelId);
}
