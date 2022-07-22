package com.walnut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({ "com.walnut.core.api.data.mapper", "com.walnut.api.data.*.mapper" })
public class WalnutApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalnutApplication.class, args);
	}

}
