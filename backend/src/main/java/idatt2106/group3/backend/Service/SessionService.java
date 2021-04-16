package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Session;
import idatt2106.group3.backend.Repository.SessionRepository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);
    
    @Autowired
    private SessionRepository sessionRepository;


    public Session getSession(long sessionId)
    {
        LOGGER.info("getSession(long sessionId) was called with sessionId: {}", sessionId);
        Optional<Session> session = sessionRepository.findById(sessionId);
        if(session.isPresent())return session.get();
        return null;
    }


    public Session loginToGetSession(String email, String password)
    {
        LOGGER.info("loginToGetSession(String email, String password) was called with email: {}", email);
        //Her må det skje litt logikk
        //TODO: Implementer
        return new Session();
    }

    public void deleteSession(long sessionId)
    {
        LOGGER.info("deleteSession(long sessionId) was called with sessionId: {}", sessionId);
        sessionRepository.deleteById(sessionId);
    }
}
