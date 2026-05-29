package com.kellen.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.example.entity.ExampleEntity;
import com.kellen.example.entity.bo.ExampleBO;
import com.kellen.example.entity.query.ExampleQuery;
import com.kellen.example.entity.vo.ExampleVO;
import com.kellen.example.service.ExampleService;
import com.kellen.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 示例业务 Controller 请求层。
 *
 * @author sunkailun
 * @className ExampleController
 * @time 2026/05/26
 */
@RestController
@RequestMapping("/example")
@Tag(name = "示例业务", description = "演示标准 Controller 分层、权限控制和 Knife4j 文档注解")
public class ExampleController {

    /**
     * 示例业务 Service。
     */
    private final ExampleService exampleService;

    /**
     * 构造示例业务 Controller。
     *
     * @param exampleService 示例业务 Service
     */
    public ExampleController(ExampleService exampleService) {
        // 注入示例业务 Service。
        this.exampleService = exampleService;
    }

    /**
     * 分页查询。
     *
     * @param exampleQuery 查询参数
     * @return 分页结果
     */
    @PostMapping("/select")
    @PreAuthorize("hasAuthority('example:select')")
    @Operation(summary = "分页查询示例", description = "按查询条件分页返回示例业务数据")
    public ApiResponse<Page<ExampleVO>> select(@Validated(ExampleQuery.Select.class) @RequestBody ExampleQuery exampleQuery) {
        // 创建分页对象。
        Page<ExampleEntity> page = new Page<>(exampleQuery.getCurrent(), exampleQuery.getSize());
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.pageEnhance(page, exampleQuery));
    }

    /**
     * 集合查询。
     *
     * @param exampleQuery 查询参数
     * @return 集合结果
     */
    @PostMapping("/selectList")
    @PreAuthorize("hasAuthority('example:select-list')")
    @Operation(summary = "查询示例集合", description = "按查询条件返回示例业务集合数据")
    public ApiResponse<List<ExampleVO>> selectList(@Validated(ExampleQuery.SelectList.class) @RequestBody ExampleQuery exampleQuery) {
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.listEnhance(exampleQuery));
    }

    /**
     * 单条查询。
     *
     * @param exampleQuery 查询参数
     * @return 单条结果
     */
    @PostMapping("/selectOne")
    @PreAuthorize("hasAuthority('example:select-one')")
    @Operation(summary = "查询单条示例", description = "按唯一条件返回一条示例业务数据")
    public ApiResponse<ExampleVO> selectOne(@Validated(ExampleQuery.SelectOne.class) @RequestBody ExampleQuery exampleQuery) {
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.getOneEnhance(exampleQuery));
    }

    /**
     * 总数查询。
     *
     * @param exampleQuery 查询参数
     * @return 总数
     */
    @PostMapping("/count")
    @PreAuthorize("hasAuthority('example:count')")
    @Operation(summary = "统计示例数量", description = "按查询条件统计示例业务数据数量")
    public ApiResponse<Long> count(@Validated(ExampleQuery.Count.class) @RequestBody ExampleQuery exampleQuery) {
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.countEnhance(exampleQuery));
    }

    /**
     * 新增。
     *
     * @param exampleBO 新增参数
     * @return 主键
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('example:save')")
    @Operation(summary = "新增示例", description = "创建示例业务数据并返回新数据主键")
    public ApiResponse<String> save(@Validated(ExampleBO.Save.class) @RequestBody ExampleBO exampleBO) {
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.saveEnhance(exampleBO));
    }

    /**
     * 修改。
     *
     * @param exampleBO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('example:update')")
    @Operation(summary = "修改示例", description = "根据主键和version修改示例业务数据")
    public ApiResponse<Boolean> update(@Validated(ExampleBO.Update.class) @RequestBody ExampleBO exampleBO) {
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.updateEnhance(exampleBO));
    }

    /**
     * 删除。
     *
     * @param exampleBO 删除参数
     * @return 是否成功
     */
    @DeleteMapping("/remove")
    @PreAuthorize("hasAuthority('example:remove')")
    @Operation(summary = "删除示例", description = "根据主键逻辑删除示例业务数据")
    public ApiResponse<Boolean> remove(@Validated(ExampleBO.Remove.class) @RequestBody ExampleBO exampleBO) {
        // 返回统一 ApiResponse 结果。
        return ApiResponse.success(exampleService.removeEnhance(exampleBO));
    }
}
