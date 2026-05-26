package com.kellen.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kellen.example.entity.ExampleEntity;
import com.kellen.example.entity.bo.ExampleBO;
import com.kellen.example.entity.query.ExampleQuery;
import com.kellen.example.entity.vo.ExampleVO;
import com.kellen.example.mapper.ExampleMapper;
import com.kellen.example.service.ExampleService;
import com.kellen.example.service.query.ExampleServiceQuery;
import com.kellen.example.service.results.ExampleServiceResults;
import com.kellen.utils.GeneralConvertor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 示例业务 Service 服务实现层。
 *
 * @author sunkailun
 * @className ExampleServiceImpl
 * @time 2026/05/26
 */
@Service
public class ExampleServiceImpl extends ServiceImpl<ExampleMapper, ExampleEntity> implements ExampleService {

    /**
     * 示例业务 Mapper。
     */
    private final ExampleMapper exampleMapper;

    /**
     * 示例业务查询增强。
     */
    private final ExampleServiceQuery exampleServiceQuery;

    /**
     * 示例业务结果增强。
     */
    private final ExampleServiceResults exampleServiceResults;

    /**
     * 构造示例业务 Service。
     *
     * @param exampleMapper         示例业务 Mapper
     * @param exampleServiceQuery   示例业务查询增强
     * @param exampleServiceResults 示例业务结果增强
     */
    public ExampleServiceImpl(ExampleMapper exampleMapper,
                              ExampleServiceQuery exampleServiceQuery,
                              ExampleServiceResults exampleServiceResults) {
        // 注入 Mapper。
        this.exampleMapper = exampleMapper;
        // 注入查询增强。
        this.exampleServiceQuery = exampleServiceQuery;
        // 注入结果增强。
        this.exampleServiceResults = exampleServiceResults;
    }

    /**
     * 分页查询。
     *
     * @param page         分页对象
     * @param exampleQuery 查询参数
     * @return 分页结果
     */
    @Override
    public Page<ExampleVO> pageEnhance(Page<ExampleEntity> page, ExampleQuery exampleQuery) {
        // 将查询参数转换为实体，用于基础等值查询。
        ExampleEntity entity = GeneralConvertor.convertor(exampleQuery, ExampleEntity.class);
        // 创建查询包装器。
        QueryWrapper<ExampleEntity> queryWrapper = new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        exampleServiceQuery.query(exampleQuery, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(exampleQuery, queryWrapper);
        // 执行分页查询。
        Page<ExampleEntity> pageDO = exampleMapper.selectPage(page, queryWrapper);
        // 转换为 VO 分页。
        Page<ExampleVO> pageVO = exampleServiceResults.toPageVO(pageDO);
        // 判断是否需要结果增强。
        return Boolean.FALSE.equals(exampleQuery.getAssignment()) ? pageVO : exampleServiceResults.assignment(pageVO);
    }

    /**
     * 集合查询。
     *
     * @param exampleQuery 查询参数
     * @return 列表结果
     */
    @Override
    public List<ExampleVO> listEnhance(ExampleQuery exampleQuery) {
        // 将查询参数转换为实体，用于基础等值查询。
        ExampleEntity entity = GeneralConvertor.convertor(exampleQuery, ExampleEntity.class);
        // 创建查询包装器。
        QueryWrapper<ExampleEntity> queryWrapper = new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        exampleServiceQuery.query(exampleQuery, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(exampleQuery, queryWrapper);
        // 执行列表查询。
        List<ExampleEntity> records = exampleMapper.selectList(queryWrapper);
        // 转换为 VO 列表。
        List<ExampleVO> voRecords = GeneralConvertor.convertor(records, ExampleVO.class);
        // 判断是否需要结果增强。
        return Boolean.FALSE.equals(exampleQuery.getAssignment()) ? voRecords : exampleServiceResults.assignment(voRecords);
    }

    /**
     * 单条查询。
     *
     * @param exampleQuery 查询参数
     * @return 单条结果
     */
    @Override
    public ExampleVO getOneEnhance(ExampleQuery exampleQuery) {
        // 将查询参数转换为实体，用于基础等值查询。
        ExampleEntity entity = GeneralConvertor.convertor(exampleQuery, ExampleEntity.class);
        // 创建查询包装器。
        QueryWrapper<ExampleEntity> queryWrapper = new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        exampleServiceQuery.query(exampleQuery, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(exampleQuery, queryWrapper);
        // 执行单条查询。
        ExampleEntity record = exampleMapper.selectOne(queryWrapper);
        // 转换为 VO。
        ExampleVO vo = GeneralConvertor.convertor(record, ExampleVO.class);
        // 判断是否需要结果增强。
        return Boolean.FALSE.equals(exampleQuery.getAssignment()) ? vo : exampleServiceResults.assignment(vo);
    }

    /**
     * 总数查询。
     *
     * @param exampleQuery 查询参数
     * @return 总数
     */
    @Override
    public Long countEnhance(ExampleQuery exampleQuery) {
        // 将查询参数转换为实体，用于基础等值查询。
        ExampleEntity entity = GeneralConvertor.convertor(exampleQuery, ExampleEntity.class);
        // 创建查询包装器。
        QueryWrapper<ExampleEntity> queryWrapper = new QueryWrapper<>(entity);
        // 拼接自动查询条件。
        exampleServiceQuery.query(exampleQuery, queryWrapper);
        // 拼接人工查询条件。
        queryArtificial(exampleQuery, queryWrapper);
        // 返回总数。
        return exampleMapper.selectCount(queryWrapper);
    }

    /**
     * 新增。
     *
     * @param exampleBO 新增参数
     * @return 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveEnhance(ExampleBO exampleBO) {
        // 将 BO 转换为实体。
        ExampleEntity entity = GeneralConvertor.convertor(exampleBO, ExampleEntity.class);
        // 插入数据。
        exampleMapper.insert(entity);
        // 返回主键。
        return entity.getId();
    }

    /**
     * 修改。
     *
     * @param exampleBO 修改参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateEnhance(ExampleBO exampleBO) {
        // 将 BO 转换为实体。
        ExampleEntity entity = GeneralConvertor.convertor(exampleBO, ExampleEntity.class);
        // 使用 MyBatis-Plus 内置 updateById，确保 @Version 乐观锁插件能读取实体中的旧版本号。
        int count = exampleMapper.updateById(entity);
        // 返回是否成功。
        return count > 0;
    }

    /**
     * 删除。
     *
     * @param exampleBO 删除参数
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeEnhance(ExampleBO exampleBO) {
        // 创建查询包装器。
        QueryWrapper<ExampleEntity> queryWrapper = new QueryWrapper<>();
        // 根据主键删除。
        queryWrapper.eq("id", exampleBO.getId());
        // 执行逻辑删除。
        int count = exampleMapper.delete(queryWrapper);
        // 返回是否成功。
        return count > 0;
    }

    /**
     * 人工查询条件。
     *
     * @param exampleQuery 查询参数
     * @param queryWrapper 查询包装器
     * @return 查询包装器
     */
    private QueryWrapper<ExampleEntity> queryArtificial(ExampleQuery exampleQuery, QueryWrapper<ExampleEntity> queryWrapper) {
        // 业务特殊查询条件统一写在这里。
        return queryWrapper;
    }
}
