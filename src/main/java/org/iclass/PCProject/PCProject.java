package org.iclass.PCProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@ComponentScan(basePackages = {"org.iclass.PCProject.config"})
public class PCProject {

	public static void main(String[] args) {
		SpringApplication.run(PCProject.class, args);
		System.out.println("안녕하세요");
	}

}
