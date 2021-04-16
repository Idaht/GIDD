package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.Message;
import idatt2106.group3.backend.Repository.ChatRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService 
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChats() {
        LOGGER.info("getChats() called"); 
        return chatRepository.findAll();
    }

    public Chat getChat(long chatId) {
        LOGGER.info("getChat(long chatId) called with chatId: {}", chatId); 
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent())
            return chat.get();
        return null;
    }

    public Chat createChat(Chat chat) {
        LOGGER.info("createChat(Chat chat) called with chatId: {}", chat.getChatId()); 
        return chatRepository.save(chat);
    }

    public Chat updateChat(long chatId, Chat chat) {
        LOGGER.info("updateChat(long chatId, Chat chat) called with chatId: {}", chatId); 
        chat.setChatId(chatId);
        return chatRepository.save(chat);
    }

    public Set<Message> getMessages(long chatId) {
        LOGGER.info("getMessages(long chatId) called with chatId: {}", chatId); 
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()) return chat.get().getMessages();
        return null;
    }


    public boolean deleteChat(long chatId) {
        LOGGER.info("deleteChat(long chatId) called with chatId: {}", chatId); 
        chatRepository.deleteById(chatId);
        return !chatRepository.existsById(chatId);
    }
}
