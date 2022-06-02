package cc.abing.abstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ABStartApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ABStartApplication.class).web(WebApplicationType.SERVLET).run(args);
		SpringApplication.run(ABStartApplication.class, args);
	}

}
