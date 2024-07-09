package com.corsificio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.corsificio.model.PropertyClass;

@SpringBootApplication
@EnableConfigurationProperties(PropertyClass.class)
public class CorsificioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorsificioApplication.class, args);
	}

}
