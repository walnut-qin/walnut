package com.kaos.skynet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({ "com.kaos.skynet.core.api.data.mapper", "com.kaos.skynet.api.data.docare.mapper" })
@SpringBootApplication
public class SkynetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkynetApplication.class, args);
	}

}
