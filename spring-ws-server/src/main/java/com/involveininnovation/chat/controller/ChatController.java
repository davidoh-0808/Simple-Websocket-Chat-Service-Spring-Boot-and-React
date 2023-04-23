package com.involveininnovation.chat.controller;

import com.involveininnovation.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // this allows unique, variable topic subscriptions based on users
    // the topic will be unique based on the users' names (more preferably ids)
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")     // corresponds to the client invocation of /app/message
    @SendTo("/chatroom/public")     // when the client invokes /app/message -> the Springboot MessageHandler converts the url into the topic /chatroom/public which is consumed back by the client
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")     // corresponds to the client invocation of /app/private-message
    public Message recMessage(@Payload Message message){
        /*
            SimpleMessagingTemplate.convertAndSendToUser allows

            so the client must subscribe to the url pattern below :
                                /user/{username}/private
                                (i.e /user/David/private)
         */
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
}














