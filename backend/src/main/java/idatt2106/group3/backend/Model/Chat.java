package idatt2106.group3.backend.Model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @OneToOne(mappedBy = "chat", targetEntity = Activity.class)
    private Activity activity;

    @OneToMany(mappedBy = "chat", targetEntity = Message.class)
    private Set<Message> messages;

    public Chat() {
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
