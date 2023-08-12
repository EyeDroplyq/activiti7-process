package com.lyq.activiti7Service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lyq.activiti7Service.pojo.dto.AddModelManagerDTO;
import com.lyq.activiti7Service.pojo.dto.ModelManagerDTO;
import com.lyq.activiti7Service.service.ModelService;
import com.lyq.activiti7Service.utils.DateUtils;
import com.lyq.activiti7Service.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-04 20:55
 * @version: 1.0
 */
@Service
@Slf4j
public class ModelServiceImpl extends ActivitiBaseService implements ModelService {
    @Override
    public Result createModel(AddModelManagerDTO addModelManagerDTO) {
        Model model = repositoryService.newModel();
        model.setKey(addModelManagerDTO.getKey());
        model.setName(addModelManagerDTO.getName());
        model.setVersion(0);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(ModelDataJsonConstants.MODEL_NAME, addModelManagerDTO.getName());
        objectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, addModelManagerDTO.getRemark());
        objectNode.put(ModelDataJsonConstants.MODEL_REVISION, 0);
        model.setMetaInfo(objectNode.toString());
        repositoryService.saveModel(model);

        ObjectNode editNode = objectMapper.createObjectNode();
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editNode.replace("stencilset", stencilSetNode);
        ObjectNode propertoesNode = objectMapper.createObjectNode();
        propertoesNode.put("process_id", addModelManagerDTO.getKey());
        editNode.replace("properties", propertoesNode);
        repositoryService.addModelEditorSource(model.getId(), editNode.toString().getBytes(StandardCharsets.UTF_8));
        return Result.ok(model.getId());
    }

    @Override
    public Result list(ModelManagerDTO modelManagerDTO) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StringUtils.isNotEmpty(modelManagerDTO.getName())) {
            modelQuery.modelNameLike(modelManagerDTO.getName());
        }
        if (StringUtils.isNotEmpty(modelManagerDTO.getKey())) {
            modelQuery.modelKey(modelManagerDTO.getKey());
        }
        Map<String, Object> result = new HashMap<>();
        modelQuery.orderByCreateTime().desc();
        List<Model> modelList = modelQuery.listPage(modelManagerDTO.getCurrent() - 1, modelManagerDTO.getSize());
        long total = modelQuery.count();
        result.put("total", total);
        List<Map<String, Object>> records = new ArrayList<>();
        for (Model model : modelList) {
            Map<String, Object> record = new HashMap<>();
            record.put("id", model.getId());
            record.put("name", model.getName());
            record.put("key", model.getKey());
            record.put("version", model.getVersion());
            String description = JSONObject.parseObject(model.getMetaInfo()).getString(ModelDataJsonConstants.MODEL_DESCRIPTION);
            record.put("description", description);
            record.put("createDate", DateUtils.format(model.getCreateTime()));
            record.put("updateDate", DateUtils.format(model.getLastUpdateTime()));
            records.add(record);
        }
        result.put("records", records);
        return Result.ok(result);
    }

    @Override
    public Result deploy(String modelId) throws IOException {
        //获取流程的bpmn字节数组
        byte[] bpmnJsonBytes = repositoryService.getModelEditorSource(modelId);
        if (bpmnJsonBytes == null) {
            return Result.error("bpmn部署失败");
        }
        byte[] bpmnBytesToXmlBytes = bpmnBytesToXmlBytes(bpmnJsonBytes);
        if (bpmnBytesToXmlBytes == null) {
            return Result.error("部署bpmn失败");
        }
        Model model = repositoryService.getModel(modelId);
        String bpmnName = model.getName() + "bpmn20.xml";
        //获取图片的字节数组
        byte[] photoBytes = repositoryService.getModelEditorSourceExtra(modelId);
        if (photoBytes == null) {
            return Result.error("图片部署失败");
        }
        String photoName = model.getName() + "." + model.getKey() + ".png";
        Deployment deploy = repositoryService.createDeployment()
                .name(model.getName())
                .key(model.getKey())
                .addString(bpmnName, new String(bpmnBytesToXmlBytes, "UTF-8"))
                .addBytes(photoName, photoBytes)
                .deploy();
        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return Result.ok("流程部署成功");
    }

    @Override
    public void exportZip(String modelId, HttpServletResponse response) {
        String zipName = "模型为空,请先定义模型" + ".zip";
        ZipOutputStream zipOut = null;
        Model model = repositoryService.getModel(modelId);
        if (model != null) {
            zipName = model.getName() + "." + model.getKey();
            try {
                zipOut = new ZipOutputStream(response.getOutputStream());
                //下载xml文件
                byte[] bpmnJsonBytes = repositoryService.getModelEditorSource(modelId);
                byte[] bpmnXmlBytes = bpmnBytesToXmlBytes(bpmnJsonBytes);
                String xmlName = "";
                if (bpmnXmlBytes == null) {
                    xmlName = "xml文件为空，请先定义流程" + ".bpmn20.xml";
                } else {
                    xmlName = model.getName() + ".bpmn20.xml";
                }
                zipOut.putNextEntry(new ZipEntry(xmlName));
                zipOut.write(bpmnXmlBytes);
                //下载图片文件
                String photoName = "";
                byte[] photoBytes = repositoryService.getModelEditorSourceExtra(modelId);
                if (photoBytes == null) {
                    photoName = "png文件为空，请先定义流程" + ".png";
                } else {
                    photoName = model.getName() + "." + model.getKey() + ".png";
                }
                zipOut.putNextEntry(new ZipEntry(photoName));
                zipOut.write(photoBytes);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(zipName, "UTF-8") + ".zip");
                //刷新response
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (zipOut != null) {
                    try {
                        zipOut.closeEntry();
                        zipOut.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void deleteModelById(String modelId) {
        repositoryService.deleteModel(modelId);
    }

    /**
     * bpmn字节码数组转成xmlxml字节码
     *
     * @param bpmnJsonBytes
     * @return
     * @throws IOException
     */
    private byte[] bpmnBytesToXmlBytes(byte[] bpmnJsonBytes) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(bpmnJsonBytes);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        if (bpmnModel.getProcesses().size() == 0) {
            return null;
        }
        byte[] bpmnXmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        return bpmnXmlBytes;
    }
}
