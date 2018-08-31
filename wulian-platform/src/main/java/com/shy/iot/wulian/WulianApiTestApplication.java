package com.shy.iot.wulian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wulian.business.*.dao")
public class WulianApiTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WulianApiTestApplication.class, args);
	}
}
