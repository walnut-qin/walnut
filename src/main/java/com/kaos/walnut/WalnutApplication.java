package com.kaos.walnut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({ "com.kaos.walnut.api.**.mapper" })
public class WalnutApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalnutApplication.class, args);
	}

}
