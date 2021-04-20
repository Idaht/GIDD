package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MessageServiceTest
{
    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    public void setup() {
    }
}
