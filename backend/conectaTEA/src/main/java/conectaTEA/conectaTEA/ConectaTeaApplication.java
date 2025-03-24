package conectaTEA.conectaTEA;

import conectaTEA.conectaTEA.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConectaTeaApplication {

	public static void main(String[] args) {
		EnvConfig envConfig = new EnvConfig();
		SpringApplication.run(ConectaTeaApplication.class, args);
	}

}
