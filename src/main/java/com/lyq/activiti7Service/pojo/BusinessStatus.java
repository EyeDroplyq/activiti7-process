package com.lyq.activiti7Service.pojo;

import com.lyq.activiti7Service.utils.DateUtils;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:49
 * @version: 1.0
 */
@Data
@ApiModel("业务状态实体类")
@Table("tb_business_status")
public class BusinessStatus implements Serializable {
    private String businessKey;
    private String processInstanceId;
    private Integer status;
    @Column(onInsertValue = "now()")
    private Date createDate;
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private Date updateDate;

    public String getCreateDate() {
        return DateUtils.format(createDate);
    }

    public String getUpdateDate() {
        return DateUtils.format(updateDate);
    }
}
