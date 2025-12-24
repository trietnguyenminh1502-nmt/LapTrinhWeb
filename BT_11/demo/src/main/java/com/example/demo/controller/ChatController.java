package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.ChatMessage;

@Controller
public class ChatController {

	@MessageMapping("/chat.send")
	@SendTo("/topic/messages")
	public ChatMessage sendMessage(ChatMessage message) {
		if (message.getTimestamp() == 0) {
			message.setTimestamp(System.currentTimeMillis());
		}
		return message;
	}

}
