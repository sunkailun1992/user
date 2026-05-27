package com.kellen.example.entity.query;

import com.kellen.example.entity.enums.ExampleStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 示例业务查询对象。
 * <p>
 * Query 用于分页、列表、单条、总数等查询入参。
 * 与 Entity 同名的字段会先转换成 Entity，再由 QueryWrapper 自动拼接等值条件；
 * 不属于 Entity 的分页、排序、显示字段和关键字条件，由 ServiceQuery 或人工查询方法处理。
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
     * <p>
     * 该字段会通过 GeneralConvertor 转换到 ExampleEntity.id，用于 QueryWrapper 自动等值查询。
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 示例名称。
     * <p>
     * 该字段会通过 GeneralConvertor 转换到 ExampleEntity.name，用于 QueryWrapper 自动等值查询。
     */
    @Schema(description = "示例名称")
    private String name;

    /**
     * 示例状态。
     * <p>
     * 该字段会通过 GeneralConvertor 转换到 ExampleEntity.state，用于状态等值查询。
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
     * <p>
     * 该字段不是 Entity 字段，只用于 ServiceQuery 中控制 select 字段。
     */
    @Schema(description = "显示字段")
    private String fields;

    /**
     * 排序规则，true 为升序，false 为降序。
     * <p>
     * 该字段不是 Entity 字段，只用于 ServiceQuery 中控制排序方向。
     */
    @Schema(description = "排序规则，true为升序，false为降序")
    private Boolean collation;

    /**
     * 排序字段。
     * <p>
     * 该字段不是 Entity 字段，只用于 ServiceQuery 中控制排序字段。
     */
    @Schema(description = "排序字段")
    private String collationFields = "create_date_time";

    /**
     * 模糊查询关键字。
     * <p>
     * 该字段不是 Entity 字段，只用于 queryArtificial 或 ServiceQuery 中拼接人工查询条件。
     */
    @Schema(description = "模糊查询关键字")
    private String query;

    /**
     * 当前页。
     */
    @Schema(description = "当前页")
    @NotNull(groups = {Select.class}, message = "current不能为空")
    @Min(groups = {Select.class}, value = 1, message = "current最小为1")
    private Integer current;

    /**
     * 分页显示数量。
     */
    @Schema(description = "分页显示数量")
    @NotNull(groups = {Select.class}, message = "size不能为空")
    @Min(groups = {Select.class}, value = 1, message = "size最小为1")
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
