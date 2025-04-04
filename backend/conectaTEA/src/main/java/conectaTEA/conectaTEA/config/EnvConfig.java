package conectaTEA.conectaTEA.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    public EnvConfig() {
        Dotenv dotenv = Dotenv.configure().directory("conectaTEA/.env").load();

        // Carregar variáveis de ambiente do arquivo .env
        System.setProperty("HOST", dotenv.get("HOST"));
        System.setProperty("DATABASE", dotenv.get("DATABASE"));
        System.setProperty("USER_DB", dotenv.get("USER_DB"));
        System.setProperty("PASSWORD_DB", dotenv.get("PASSWORD_DB"));
        System.setProperty("SECRET", dotenv.get("SECRET"));
        System.setProperty("REDIS_HOST", dotenv.get("REDIS_HOST"));
        System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
        System.setProperty("REDIS_PASSWORD", dotenv.get("REDIS_PASSWORD"));
    }
}
