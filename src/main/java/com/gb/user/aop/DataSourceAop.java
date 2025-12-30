package com.gb.user.aop;

import com.gb.utils.DataSourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: sunx
 * @Date 2021/8/6 10:39
 * @Classname DataSourceAop
 * @Description
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class DataSourceAop {

    /**
     * 对所有DynamicDataSource的注解类实现切点
     */
    @Pointcut("within(@com.gb.utils.annotations.DynamicDataSource *)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String dataSource = DataSourceUtil.getDataSource(httpServletRequest.getHeader("dataSource"));
        DataSourceUtil.put(dataSource);
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        DataSourceUtil.clear();
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrow(JoinPoint joinPoint, Throwable e) {
        DataSourceUtil.clear();
    }

    private HttpServletRequest getHttpServletRequest() {
        //获得请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
