package com.gb.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.bo.UserExtendsBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户经纪人认证
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user_agent_certification`")
@ApiModel(value="UserAgentCertification对象", description="用户经纪人认证")
public class UserAgentCertification implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序列")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "证件号")
    private String certificateCode;

    @ApiModelProperty(value = "执业证发证日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate issueCertificatesDate;

    @ApiModelProperty(value = "执业证发证日期（主要给前端使用，便于清空日期为空）")
    @TableField(exist = false)
    private String issueCertificatesDateStr;

    @ApiModelProperty(value = "证件文件地址")
    private String certificateFileAddress;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "开始时间起")
    private String createDateTimeStart;

    @TableField(exist = false)
    @ApiModelProperty(value = "开始时间止")
    private String createDateTimeEnd;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态（0：未删除，1：删除）")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "状态（0：待认证，1：认证成功，2：认证失败，3：认证关闭）")
    private Integer state = null;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;
    
    @Version
    @ApiModelProperty(hidden = true,value = "版本号")
    private Integer version;

    @TableField(exist = false)
    @ApiModelProperty(value = "显示字段")
    private String fields;

    @TableField(exist = false)
    @ApiModelProperty(value = "排序规则(0:desc,1:asc)")
    private Boolean collation;

    @TableField(exist = false)
    @ApiModelProperty(value = "排序字段")
    private String collationFields = "create_date_time";

    @TableField(exist = false)
    @ApiModelProperty(value = "模糊查询")
    private String query;

    @TableField(exist = false)
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @TableField(exist = false)
    @ApiModelProperty(value = "真实姓名")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "别称")
    private String alias;

    @ApiModelProperty(value = "手机号模糊查询")
    @TableField(exist = false)
    private String mobileQuery;

    @ApiModelProperty(value = "姓名模糊查询")
    @TableField(exist = false)
    private String nameQuery;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户昵称模糊查询")
    private String aliasQuery;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户唯一标志列表")
    private List<String> userIdList;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作员名称")
    private String opName;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作员唯一标志")
    private String opUserId;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户扩展信息")
    private UserExtendsBO userExtendsBO;

    @TableField(exist = false)
    @ApiModelProperty(value = "离职状体")
    private Boolean dimission;
}
