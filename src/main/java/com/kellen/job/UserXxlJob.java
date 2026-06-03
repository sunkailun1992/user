package com.kellen.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 用户模块XXL-JOB测试任务
 *
 * @author sunkailun
 * @DateTime 2026/6/3 11:55
 * @email 376253703@qq.com
 */
@Slf4j
@Component
public class UserXxlJob {

    /**
     * 执行用户模块测试任务
     *
     * @return void
     * @author sunkailun
     * @DateTime 2026/6/3 11:55
     * @email 376253703@qq.com
     */
    @XxlJob("userTestJob")
    public void userTestJob() {
        // 获取调度中心传入的任务参数，便于后台手动触发时验证参数链路。
        String jobParam = XxlJobHelper.getJobParam();
        // 获取当前执行时间，便于在应用日志和XXL-JOB日志中核对触发时间。
        LocalDateTime executeTime = LocalDateTime.now();
        // 输出应用本地日志，便于在微服务日志中确认任务已经进入业务进程。
        log.info("XXL-JOB userTestJob executed, param={}, executeTime={}", jobParam, executeTime);
        // 输出XXL-JOB调度日志，便于在调度中心任务日志页面直接查看执行结果。
        XxlJobHelper.log("XXL-JOB userTestJob executed, param={}, executeTime={}", jobParam, executeTime);
    }
}
