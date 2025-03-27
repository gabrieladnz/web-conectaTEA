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
        System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
        System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
        System.setProperty("REDIS_PASSWORD", dotenv.get("REDIS_PASSWORD"));
    }
}
