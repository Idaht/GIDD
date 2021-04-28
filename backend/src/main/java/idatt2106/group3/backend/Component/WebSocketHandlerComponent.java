package idatt2106.group3.backend.Component;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import idatt2106.group3.backend.Model.DTO.MessageDTO;
import idatt2106.group3.backend.Service.MessageService;

public class WebSocketHandlerComponent extends TextWebSocketHandler{

    private final Set<WebSocketSession> sessions = new HashSet<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandlerComponent.class);

        @Autowired
        private MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Server connection opened");
        sessions.add(session);

        TextMessage message = new TextMessage("Hello");
        session.sendMessage(message);
        
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        MessageDTO messageDTO = new ObjectMapper().readValue(payload, MessageDTO.class);
        LOGGER.info("Server recieved: {}", messageDTO.getMessage());
        messageService.createMessageDTO(messageDTO);
        for(WebSocketSession webSocketSession: sessions) webSocketSession.sendMessage(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.warn("Server transport error: {}", exception.getMessage(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        LOGGER.info("Server connection closed: {}", closeStatus);
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
}