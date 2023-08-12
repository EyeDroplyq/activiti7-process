package com.lyq.activiti7Service.pojo;

import com.lyq.activiti7Service.enums.LeaveTypeEnum;
import com.lyq.activiti7Service.utils.DateUtils;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2023-08-12 14:38
 * @version: 1.0
 */
@Data
@ApiModel("请假实体类")
@Table("tb_leave")
public class Leave implements Serializable {
    @ApiModelProperty("id")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private String id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("请假时长")
    private Double duration;
    @ApiModelProperty("工作委托人")
    private String principal;
    @ApiModelProperty("联系电话")
    private String contactPhone;
    @ApiModelProperty("请假类型")
    private Integer leaveType;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("请假原因")
    private String leaveReason;
    @ApiModelProperty("请假开始时间")
    private Date startDate;
    @ApiModelProperty("请假结束时间")
    private Date endDate;
    @ApiModelProperty("创建时间")
    @Column(onInsertValue = "now()")
    private Date createDate;
    @ApiModelProperty("更新时间")
    @Column(onUpdateValue = "now()", onInsertValue = "now()")
    private Date updateDate;
    @Column(ignore = true)
    private Integer status;
    @Column(ignore = true)
    private String processInstanceId;

    public String getStartDateStr() {
        if (startDate==null){
            return "";
        }
        return DateUtils.format(startDate);
    }

    public String getEndDateStr() {
        if (endDate==null){
            return "";
        }
        return DateUtils.format(endDate);
    }

    public String getCreateDateStr() {
        if (createDate==null){
            return "";
        }
        return DateUtils.format(createDate);
    }

    public String getUpdateDateStr() {
        if (updateDate==null){
            return "";
        }
        return DateUtils.format(updateDate);
    }

    public String getLeaveTypeStr() {
        LeaveTypeEnum leaveTypeEnum = LeaveTypeEnum.getEumByCode(leaveType);
        return leaveTypeEnum.getDesc();
    }
}
