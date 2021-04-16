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

    public Chat(Activity activity, Set<Message> messages) {
        this.activity = activity;
        this.messages = messages;
    }

    public Chat() {
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString()
    {
        return "Chat{" +
                "chatId=" + chatId +
                ", activity=" + activity +
                ", messages=" + messages +
                '}';
    }
}
