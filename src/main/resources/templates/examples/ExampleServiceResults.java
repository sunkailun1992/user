package com.kellen.example.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.example.entity.ExampleEntity;
import com.kellen.example.entity.vo.ExampleVO;
import com.kellen.example.entity.enums.ExampleStateEnum;
import com.kellen.utils.GeneralConvertor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 示例业务 Service 结果增强层。
 * <p>
 * DO 转 VO、枚举说明、关联信息补全等逻辑统一放在该类。
 *
 * @author sunkailun
 * @className ExampleServiceResults
 * @time 2026/05/26
 */
@Service
public class ExampleServiceResults {

    /**
     * 列表结果增强。
     *
     * @param records 列表结果
     * @return 增强后的列表结果
     */
    public List<ExampleVO> assignment(List<ExampleVO> records) {
        // 遍历列表并补充展示字段。
        records.forEach(this::assignment);
        // 返回增强后的列表。
        return records;
    }

    /**
     * 单条结果增强。
     *
     * @param record 单条结果
     * @return 增强后的单条结果
     */
    public ExampleVO assignment(ExampleVO record) {
        // 空对象直接返回。
        if (record == null) {
            // 返回空对象。
            return null;
        }
        // 读取状态枚举。
        ExampleStateEnum state = record.getState();
        // 设置状态说明。
        record.setStateDesc(state == null ? null : state.getDesc());
        // 返回增强结果。
        return record;
    }

    /**
     * 分页结果增强。
     *
     * @param page 分页结果
     * @return 增强后的分页结果
     */
    public Page<ExampleVO> assignment(Page<ExampleVO> page) {
        // 增强分页记录。
        assignment(page.getRecords());
        // 返回分页对象。
        return page;
    }

    /**
     * DO 分页转换为 VO 分页。
     *
     * @param pageDO DO 分页
     * @return VO 分页
     */
    public Page<ExampleVO> toPageVO(Page<ExampleEntity> pageDO) {
        // 创建 VO 分页对象。
        Page<ExampleVO> pageVO = new Page<>();
        // 设置当前页。
        pageVO.setCurrent(pageDO.getCurrent());
        // 设置分页大小。
        pageVO.setSize(pageDO.getSize());
        // 设置总数。
        pageVO.setTotal(pageDO.getTotal());
        // 设置分页记录。
        pageVO.setRecords(GeneralConvertor.convertor(pageDO.getRecords(), ExampleVO.class));
        // 返回 VO 分页。
        return pageVO;
    }
}
