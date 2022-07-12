package com.kaos.skynet;

import com.kaos.skynet.core.api.data.mapper.KaosUserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkynetApplicationTests {
	@Autowired
	KaosUserMapper kaosUserMapper;

	@Test
	void contextLoads() {
		kaosUserMapper.selectById("0306");
	}

}
