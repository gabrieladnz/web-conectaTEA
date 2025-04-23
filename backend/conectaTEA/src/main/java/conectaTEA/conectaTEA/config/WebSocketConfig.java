package conectaTEA.conectaTEA.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Define um broker simples para mensagens
        registry.setApplicationDestinationPrefixes("/app"); // Prefixo para mensagens enviadas pelos clientes
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Define o endpoint do WebSocket
    }
}


