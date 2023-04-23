package com.involveininnovation.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        /*
            the client establishes the websocket connection as below:

            let Sock = new SockJS('<server-url>/ws');
            stompClient = over(Sock);
            stompClient.connect({},onConnected, onError);
         */
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /* Use /app prefix to invoke api controller annotated with @MessageMapping
           /app prefix is used when the client sends streaming messages (as STOMP format) to the server
         */
        registry.setApplicationDestinationPrefixes("/app");

        //configure the topic prefixes, the client destination prefixes (the url prefixes that receives streaming messages once the websocket is established)
        registry.enableSimpleBroker("/chatroom","/user");

        /*
            /user prefix allows the client to subscribe to unique, variable topics based on the username (or preferably user id)

            /user/{username or preferably user id}/private
         */
        registry.setUserDestinationPrefix("/user");
    }
}
