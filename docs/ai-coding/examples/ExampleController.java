package com.kellen.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.example.entity.ExampleEntity;
import com.kellen.example.entity.bo.ExampleBO;
import com.kellen.example.entity.query.ExampleQuery;
import com.kellen.example.entity.vo.ExampleVO;
import com.kellen.example.service.ExampleService;
import com.kellen.utils.Json;
import com.kellen.utils.enumeration.ReturnCode;
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
    public Json<Page<ExampleVO>> select(@Validated(ExampleQuery.Select.class) @RequestBody ExampleQuery exampleQuery) {
        // 创建分页对象。
        Page<ExampleEntity> page = new Page<>(exampleQuery.getCurrent(), exampleQuery.getSize());
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.pageEnhance(page, exampleQuery));
    }

    /**
     * 集合查询。
     *
     * @param exampleQuery 查询参数
     * @return 集合结果
     */
    @PostMapping("/selectList")
    @PreAuthorize("hasAuthority('example:select-list')")
    public Json<List<ExampleVO>> selectList(@Validated(ExampleQuery.SelectList.class) @RequestBody ExampleQuery exampleQuery) {
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.listEnhance(exampleQuery));
    }

    /**
     * 单条查询。
     *
     * @param exampleQuery 查询参数
     * @return 单条结果
     */
    @PostMapping("/selectOne")
    @PreAuthorize("hasAuthority('example:select-one')")
    public Json<ExampleVO> selectOne(@Validated(ExampleQuery.SelectOne.class) @RequestBody ExampleQuery exampleQuery) {
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.getOneEnhance(exampleQuery));
    }

    /**
     * 总数查询。
     *
     * @param exampleQuery 查询参数
     * @return 总数
     */
    @PostMapping("/count")
    @PreAuthorize("hasAuthority('example:count')")
    public Json<Long> count(@Validated(ExampleQuery.Count.class) @RequestBody ExampleQuery exampleQuery) {
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.countEnhance(exampleQuery));
    }

    /**
     * 新增。
     *
     * @param exampleBO 新增参数
     * @return 主键
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('example:save')")
    public Json<String> save(@Validated(ExampleBO.Save.class) @RequestBody ExampleBO exampleBO) {
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.saveEnhance(exampleBO));
    }

    /**
     * 修改。
     *
     * @param exampleBO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('example:update')")
    public Json<Boolean> update(@Validated(ExampleBO.Update.class) @RequestBody ExampleBO exampleBO) {
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.updateEnhance(exampleBO));
    }

    /**
     * 删除。
     *
     * @param exampleBO 删除参数
     * @return 是否成功
     */
    @DeleteMapping("/remove")
    @PreAuthorize("hasAuthority('example:remove')")
    public Json<Boolean> remove(@Validated(ExampleBO.Remove.class) @RequestBody ExampleBO exampleBO) {
        // 返回统一 Json 结果。
        return new Json<>(ReturnCode.成功, exampleService.removeEnhance(exampleBO));
    }
}
