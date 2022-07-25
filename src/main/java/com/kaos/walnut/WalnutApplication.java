package com.kaos.walnut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({ "com.kaos.walnut.core.api.data.mapper", "com.kaos.walnut.api.data.*.mapper" })
public class WalnutApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalnutApplication.class, args);
	}

}
