package com.kellen.example.service.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kellen.example.entity.ExampleEntity;
import com.kellen.example.entity.query.ExampleQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 示例业务 Service 查询增强层。
 * <p>
 * 自动查询条件统一放在该类，ServiceImpl 只负责调用，不直接堆查询细节。
 *
 * @author sunkailun
 * @className ExampleServiceQuery
 * @time 2026/05/26
 */
@Service
public class ExampleServiceQuery {

    /**
     * 构建查询条件。
     *
     * @param exampleQuery 查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    public QueryWrapper<ExampleEntity> query(ExampleQuery exampleQuery, QueryWrapper<ExampleEntity> queryWrapper) {
        // 处理排序字段，默认按 create_date_time 倒序。
        if (exampleQuery.getCollation() != null && StringUtils.isNotBlank(exampleQuery.getCollationFields())) {
            // collation 为 true 时升序。
            if (exampleQuery.getCollation()) {
                // 设置升序排序。
                queryWrapper.orderByAsc(exampleQuery.getCollationFields());
            } else {
                // 设置降序排序。
                queryWrapper.orderByDesc(exampleQuery.getCollationFields());
            }
        } else {
            // 未传排序规则时使用默认排序字段降序。
            queryWrapper.orderByDesc(exampleQuery.getCollationFields());
        }

        // 处理显示字段。
        if (StringUtils.isNotBlank(exampleQuery.getFields())) {
            // 指定 select 字段。
            queryWrapper.select(exampleQuery.getFields());
        }

        // 处理名称右模糊查询。
        if (StringUtils.isNotBlank(exampleQuery.getName())) {
            // 根据 name 拼接 likeRight 查询。
            queryWrapper.likeRight("name", exampleQuery.getName());
        }

        // 处理状态等值查询。
        if (exampleQuery.getState() != null) {
            // 根据 state 拼接等值查询。
            queryWrapper.eq("state", exampleQuery.getState());
        }

        // 返回查询包装器。
        return queryWrapper;
    }
}
