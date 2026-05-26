package com.kellen.example.entity.query;

import com.kellen.example.entity.enums.ExampleStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 示例业务查询对象。
 * <p>
 * Query 用于分页、列表、单条、总数等查询入参。
 *
 * @author sunkailun
 * @className ExampleQuery
 * @time 2026/05/26
 */
@Data
@Schema(description = "示例业务查询对象")
public class ExampleQuery implements Serializable {

    /**
     * 主键。
     */
    @Schema(description = "主键")
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
     * 是否执行结果增强。
     */
    @Schema(description = "是否执行结果增强")
    private Boolean assignment;

    /**
     * 显示字段。
     */
    @Schema(description = "显示字段")
    private String fields;

    /**
     * 排序规则，true 为升序，false 为降序。
     */
    @Schema(description = "排序规则，true为升序，false为降序")
    private Boolean collation;

    /**
     * 排序字段。
     */
    @Schema(description = "排序字段")
    private String collationFields = "create_date_time";

    /**
     * 模糊查询关键字。
     */
    @Schema(description = "模糊查询关键字")
    private String query;

    /**
     * 当前页。
     */
    @Schema(description = "当前页")
    private Integer current;

    /**
     * 分页显示数量。
     */
    @Schema(description = "分页显示数量")
    private Integer size;

    /**
     * 分页查询校验分组。
     */
    public interface Select {
    }

    /**
     * 列表查询校验分组。
     */
    public interface SelectList {
    }

    /**
     * 单条查询校验分组。
     */
    public interface SelectOne {
    }

    /**
     * 总数查询校验分组。
     */
    public interface Count {
    }
}
