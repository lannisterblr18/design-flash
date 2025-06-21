package com.designflash.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesignFlashApplication {

	@Value("${server.port}")
	private String port;
	public static void main(String[] args) {
		SpringApplication.run(DesignFlashApplication.class, args);
	}

	@PostConstruct
	public void logPort() {
		System.out.println("🚀 Running on port: " + port);
	}

}
