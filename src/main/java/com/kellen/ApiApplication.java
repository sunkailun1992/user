package com.kellen;

import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created with IntelliJ IDEA.
 * @author:     	孙凯伦
 * @since:   	    2021-02-04 11:06:07
 * @description:	项目启动
 * @source:  	    代码生成器
 */
@EnableCaching
@SpringBootApplication(exclude = {SeataFeignClientAutoConfiguration.class, DataSourceAutoConfiguration.class})
@RestController
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.kellen.*.mapper")
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
