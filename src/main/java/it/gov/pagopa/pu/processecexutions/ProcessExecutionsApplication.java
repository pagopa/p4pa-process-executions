package it.gov.pagopa.pu.processecexutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class ProcessExecutionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessExecutionsApplication.class, args);
	}

}
