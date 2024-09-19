package ar.edu.unq.apc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "APC-API", 
								version = "1.0", 
								description = "Web application to help customers carry out and manage their purchases"))
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ApcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApcApplication.class, args);
	}

}
