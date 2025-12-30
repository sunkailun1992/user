package com.gb.user.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户经纪人认证VO
 * </p>
 *
 * @author sunx
 * @since 2021-11-07
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户标签信息VO")
public class UserTypeInfoVO implements Serializable {

        private String userTypeId;

        private String userTypeName;

        private String userTypeValueId;

        private String userTypeValueName;

        private UserBasicInfoVO basicInfoVo;
}
