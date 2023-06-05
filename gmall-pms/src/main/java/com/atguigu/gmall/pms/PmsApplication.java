package com.atguigu.gmall.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jiangli
 * @since 2020/1/10 3:46
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan(value={"com.atguigu.gmall.pms.dao"})
public class PmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsApplication.class,args);
	}
}
