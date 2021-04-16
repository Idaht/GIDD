package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Model.Message;
import idatt2106.group3.backend.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{
    @Autowired
    private MessageRepository messageRepository;

    public boolean createMessage(Chat chat, Message message)
    {
        message.setChat(chat);
        Message returnMessage = messageRepository.save(message);
        return messageRepository.existsById(returnMessage.getMessageId());
    }
}
