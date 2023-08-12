package com.lyq.activiti7Service.pojo;

import com.lyq.activiti7Service.utils.DateUtils;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description: 流程定义-业务关联实体类 对应表中 tb_process_config
 * @author: 小琦
 * @createDate: 2023-08-09 23:21
 * @version: 1.0
 */
@Data
@ApiModel("流程定义-业务关联实体类")
@Table("tb_process_config")
public class ProcessBusiness {
    @ApiModelProperty("主键id")
    @Id(keyType = KeyType.Generator,value = KeyGenerators.snowFlakeId)
    private String id;
    @ApiModelProperty("流程定义key")
    private String processKey;
    @ApiModelProperty("业务申请路由名")
    private String businessRoute;
    @ApiModelProperty("关联表单组件名")
    private String formName;
    @ApiModelProperty("创建时间")
    @Column(onInsertValue = "now()")
    private Date createDate;

    @ApiModelProperty("修改时间")
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private Date updateDate;

    public String getCreateDate() {
        if (createDate == null) {
            return "";
        }
        return DateUtils.format(createDate);
    }

    public String getUpdateDate() {
        if (updateDate == null) {
            return "";
        }
        return DateUtils.format(updateDate);
    }
}
