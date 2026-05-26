package com.kellen.example.entity.bo;

import com.kellen.example.entity.enums.ExampleStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 示例业务传输对象。
 * <p>
 * BO 用于新增、修改、删除等写操作入参，字段应贴近前端提交内容。
 *
 * @author sunkailun
 * @className ExampleBO
 * @time 2026/05/26
 */
@Data
@Schema(description = "示例业务传输对象")
public class ExampleBO implements Serializable {

    /**
     * 主键。
     */
    @Schema(description = "主键")
    @NotBlank(groups = {Update.class, Remove.class}, message = "id不能为空")
    private String id;

    /**
     * 示例名称。
     */
    @Schema(description = "示例名称")
    private String name;

    /**
     * 示例状态。
     */
    @Schema(description = "示例状态")
    private ExampleStateEnum state;

    /**
     * 创建人。
     */
    @Schema(description = "创建人")
    private String createName;

    /**
     * 修改人。
     */
    @Schema(description = "修改人")
    private String modifyName;

    /**
     * 新增校验分组。
     */
    public interface Save {
    }

    /**
     * 修改校验分组。
     */
    public interface Update {
    }

    /**
     * 删除校验分组。
     */
    public interface Remove {
    }
}
