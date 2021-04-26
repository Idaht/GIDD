package idatt2106.group3.backend.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfiguration.class);

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/config.properties"))) {
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            List<String> usernameAndPassword = reader.lines().skip(3).collect(Collectors.toList());
            mailSender.setUsername(usernameAndPassword.get(0));
            mailSender.setPassword(usernameAndPassword.get(1));
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex.fillInStackTrace());
        }

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.debug", "true");

        return mailSender;
    }
}
