package com.gb.account.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gb.account.entity.enums.UserRoleStateEnum;
import com.gb.account.entity.enums.UserRoleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-10-21 01:50:37
 * @description:	TODO  用户角色表传输
 * @source:  	    代码生成器
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户角色表传输")
public class UserRoleBO implements Serializable {

    @ApiModelProperty(value = "序列")
    @NotBlank(groups = {UserRoleBO.Update.class,UserRoleBO.Remove.class},message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "用户id")
    @NotBlank(groups = {UserRoleBO.Save.class},message = "userId不能为空")
    private String userId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "角色Code")
    private String roleCode;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyDateTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "删除状态")
    private Boolean isDelete;

    @ApiModelProperty(value = "类型（0：默认）")
    private UserRoleTypeEnum type;

    @ApiModelProperty(value = "状态（0：默认）")
    private UserRoleStateEnum state;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sorting;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "用户id唯一标志列表")
    private List<String> roleIdList;


    /**
    * 新增
    */
    public interface Save{}

    /**
    * 修改
    */
    public interface Update{}

    /**
    * 删除
    */
    public interface Remove{}
}


