package idatt2106.group3.backend.Web;

import idatt2106.group3.backend.Model.Activity;
import idatt2106.group3.backend.Model.Chat;
import idatt2106.group3.backend.Service.ActivityService;
import idatt2106.group3.backend.Service.ChatService;
import idatt2106.group3.backend.Service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ChatControllerTest
{
    @InjectMocks
    private ChatController chatController;

    @Mock
    private ChatService chatService;

    @BeforeEach
    public void setUp() {
        Chat chat = new Chat(null, null);
    }
}
