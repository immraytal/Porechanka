package com.kisel.Porechanka;

import org.modelmapper.ModelMapper;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = { "com.kisel.Porechanka.model" })
public class PorechankaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PorechankaApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.registerModule(new Jsr310Module());
		return modelMapper;
	}
}
