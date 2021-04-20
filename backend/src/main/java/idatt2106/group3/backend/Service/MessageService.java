package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.Message;
import idatt2106.group3.backend.Repository.ChatRepository;
import idatt2106.group3.backend.Repository.MessageRepository;

import java.util.Optional;

import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;

    public Message createMessage(long chatId, Message message)
    {
        LOGGER.info("createMessage(Chat chat, Message message) was called with chatId: {}, and messageId: {}", chatId, message.getMessageId());
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (!chat.isPresent())
        {
            return null;
        }
        message.setChat(chat.get());
        return messageRepository.save(message);
        //return messageRepository.existsById(returnMessage.getMessageId());
    }
}
