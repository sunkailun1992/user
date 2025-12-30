package com.gb.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gb.account.entity.User;
import com.gb.account.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.hutool.core.date.Month.JANUARY;

/**
 * Created with IntelliJ IDEA.
 *
 * @author sunx
 * @DateTime 2021/1/4  下午2:09
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain 清空用户登录次数定时任务
 */
@Component
@Slf4j
public class ClearUserLoginCountJob extends JavaProcessor {

    @Resource
    private UserMapper userMapper;

    @Override
    public ProcessResult process(JobContext context) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("today_login_count", 0);
        updateWrapper.set("modify_name", "clearUserLoginCountJob");
        DateTime date = DateUtil.date();
        if(DateUtil.dayOfMonth(date) == 1) {
            updateWrapper.set("monthly_login_count", 0);
            if(DateUtil.monthEnum(date).equals(JANUARY)) {
                updateWrapper.set("years_login_count", 0);
            }
        }
        userMapper.update(new User(), updateWrapper);
        log.debug("ClearUserLoginCountJob-执行完毕！");
        return new ProcessResult(true);
    }
}
