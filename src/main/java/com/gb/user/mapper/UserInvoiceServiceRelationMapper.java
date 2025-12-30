package com.gb.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.user.entity.UserInvoiceServiceRelation;
import feign.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * <p>
 * 用户发票与发票服务关联关系表 Mapper 接口
 * </p>
 *
 * @author sunx
 * @since 2021-05-27
 */
public interface UserInvoiceServiceRelationMapper extends BaseMapper<UserInvoiceServiceRelation> {

    /**
     * 批量插入发票关联关系
     * @param list:
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author sunx
     * @DateTime 2021/3/8  2:11 下午
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @Insert("<script>" +
            "insert into user_invoice_service_relation " +
            "(`id`,`user_invoice_id`,`invoice_service_id`,`create_date_time`,`create_name`,`modify_date_time`,`modify_name`) " +
            "values " +
            "<foreach collection='list' item='invoice' index='index' separator=','>" +
            "(#{invoice.id}, #{invoice.userInvoiceId}, #{invoice.invoiceServiceId},#{invoice.createDateTime},#{invoice.createName},#{invoice.modifyDateTime},#{invoice.modifyName})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertBatch(@Param("list") List<UserInvoiceServiceRelation> list);
}
