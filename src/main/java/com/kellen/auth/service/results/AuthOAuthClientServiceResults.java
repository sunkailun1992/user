package com.kellen.auth.service.results;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kellen.auth.entity.AuthOAuthClient;
import com.kellen.auth.entity.vo.AuthOAuthClientVO;
import com.kellen.utils.convert.GeneralConvertor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * OAuth 客户端查询结果转换增强。
 */
@Component
public class AuthOAuthClientServiceResults {

    public AuthOAuthClientVO toVO(AuthOAuthClient recordDO) {
        if (recordDO == null) {
            return null;
        }
        AuthOAuthClientVO vo = GeneralConvertor.convertor(recordDO, AuthOAuthClientVO.class);
        vo.setStateDesc(recordDO.getState() == null ? null : recordDO.getState().getDesc());
        return vo;
    }

    public List<AuthOAuthClientVO> toListVO(List<AuthOAuthClient> recordsDO) {
        if (recordsDO == null || recordsDO.isEmpty()) {
            return Collections.emptyList();
        }
        return recordsDO.stream().map(this::toVO).toList();
    }

    public Page<AuthOAuthClientVO> toPageVO(Page<AuthOAuthClient> pageDO) {
        if (pageDO == null) {
            return new Page<>();
        }
        Page<AuthOAuthClientVO> pageVO = new Page<>(pageDO.getCurrent(), pageDO.getSize(), pageDO.getTotal());
        pageVO.setRecords(toListVO(pageDO.getRecords()));
        return pageVO;
    }

    public List<AuthOAuthClientVO> assignment(List<AuthOAuthClientVO> records) {
        return records == null ? Collections.emptyList() : records;
    }

    public Page<AuthOAuthClientVO> assignment(Page<AuthOAuthClientVO> page) {
        return page;
    }
}
