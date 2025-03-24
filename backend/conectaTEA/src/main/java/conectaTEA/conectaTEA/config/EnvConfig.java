package conectaTEA.conectaTEA.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    public EnvConfig() {
        Dotenv dotenv = Dotenv.configure().load();

        // Carregar variáveis de ambiente do arquivo .env
        System.setProperty("HOST", dotenv.get("HOST"));
        System.setProperty("DATABASE", dotenv.get("DATABASE"));
        System.setProperty("USER", dotenv.get("USER"));
        System.setProperty("PASSWORD", dotenv.get("PASSWORD"));
        System.setProperty("SECRET", dotenv.get("SECRET"));
    }
}
