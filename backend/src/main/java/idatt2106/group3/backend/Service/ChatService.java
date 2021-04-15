package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.Message;
import idatt2106.group3.backend.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatService
{
    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChats()
    {
        return chatRepository.findAll();
    }

    public Chat getChat(long chatId)
    {
        return chatRepository.getOne(chatId);
    }

    public Chat createChat(Chat chat)
    {
        return chatRepository.save(chat);
    }

    public Chat updateChat(long chatId, Chat chat)
    {
        chat.setChatId(chatId);
        return chatRepository.save(chat);
    }


    public Set<Message> getMessages(long chatId)
    {
        return chatRepository.getOne(chatId).getMessages();
    }

    public boolean deleteChat(long chatId)
    {
        chatRepository.deleteById(chatId);
        return !chatRepository.existsById(chatId);

    }
}
