package com.itheima.lss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.lss.currency.mapper")
public class LssApplication {

	public static void main(String[] args) {
		SpringApplication.run(LssApplication.class, args);
	}

}
