package com.atguigu.gmall.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description TODO
 * @Date 2020/9/7 19:26
 * @Created by yuant
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SmsApplicatoin {

	public static void main(String[] args) {
		SpringApplication.run(SmsApplicatoin.class, args);
	}

}
