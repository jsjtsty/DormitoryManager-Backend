package com.nulstudio.dormitory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class NulDormitoryManagerApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(new BCryptPasswordEncoder().encode("sty@20030209"));
	}

}
