package ar.edu.unq.apc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ApcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApcApplication.class, args);
	}

}
