package com.example.spring24;


import com.example.spring24.config.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(value = "/application-test.yml", factory = YamlPropertySourceFactory.class)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
