package com.gb.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gb.user.entity.UserInvoice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户发票表 Mapper 接口
 * </p>
 *
 * @author sunx
 * @since 2021-05-25
 */
public interface UserInvoiceMapper extends BaseMapper<UserInvoice> {

    /**
     * 发票信息分页查询
     *
     * @param page: 分页
     * @param queryWrapper:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 01:50:40
     */
    @Select("<script>" +
            "select * from ("+
            "select a.* from `user_invoice` a inner join `user_invoice_service_relation` b on a.`id` = b.`user_invoice_id` and a.`is_delete` = false and b.`is_delete` = false" +
            "<if test=\"ew.entity.invoiceServiceId != null and ew.entity.invoiceServiceId !=''\">" +
            "and b.`invoice_service_id` = ${ew.entity.invoiceServiceId} " +
            "</if>"+
            "<if test=\"ew.entity.userId  != null and ew.entity.userId  !=''\">" +
            "and a.`user_id` = ${ew.entity.userId} " +
            "</if>"+
            "<if test=\"ew.entity.type  != null and ew.entity.type  !=''\">" +
            "and a.`type` = ${ew.entity.type} " +
            "</if>"+
            "<if test=\"ew.entity.invoiceTaxCoding  != null and ew.entity.invoiceTaxCoding  !=''\">" +
            "and a.`invoice_tax_coding` = '${ew.entity.invoiceTaxCoding}' " +
            "</if>"+
            "<if test=\"ew.entity.invoiceLookedUp  != null and ew.entity.invoiceLookedUp  !=''\">" +
            "and a.`invoice_looked_up` = '${ew.entity.invoiceLookedUp}' " +
            "</if>"+
            ") AS q ${ew.customSqlSegment}" +
            "</script>")
    IPage<UserInvoice> selectPageByInvoiceServiceId(Page page, @Param("ew") QueryWrapper<UserInvoice> queryWrapper);


    /**
     * 发票信息查询
     *
     * @param queryWrapper:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-10-21 01:50:40
     */
    @Select("<script>" +
            "select a.* from `user_invoice` a inner join `user_invoice_service_relation` b on a.`id` = b.`user_invoice_id` and a.`is_delete` = false and b.`is_delete` = false " +
            "<if test=\"ew.entity.invoiceServiceId != null and ew.entity.invoiceServiceId !=''\">" +
            "and b.`invoice_service_id` = ${ew.entity.invoiceServiceId} " +
            "</if>"+
            "<if test=\"ew.entity.userId  != null and ew.entity.userId  !=''\">" +
            "and a.`user_id` = ${ew.entity.userId} " +
            "</if>"+
            "<if test=\"ew.entity.type  != null and ew.entity.type  !=''\">" +
            "and a.`type` = ${ew.entity.type} " +
            "</if>"+
            "<if test=\"ew.entity.invoiceTaxCoding  != null and ew.entity.invoiceTaxCoding  !=''\">" +
            "and a.`invoice_tax_coding` = '${ew.entity.invoiceTaxCoding}' " +
            "</if>"+
            "<if test=\"ew.entity.invoiceLookedUp  != null and ew.entity.invoiceLookedUp  !=''\">" +
            "and a.`invoice_looked_up` = '${ew.entity.invoiceLookedUp}' " +
            "</if>"+
            " ${ew.customSqlSegment} "+
            "</script>")
    List<UserInvoice> selectListByInvoiceServiceId(@Param("ew") QueryWrapper<UserInvoice> queryWrapper);
}
