package idatt2106.group3.backend.Service;

import idatt2106.group3.backend.Model.Session;
import idatt2106.group3.backend.Repository.SessionRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService
{
    @Autowired
    private SessionRepository sessionRepository;


    public Session getSession(long sessionId)
    {
        Optional<Session> session = sessionRepository.findById(sessionId);
        if(session.isPresent())return session.get();
        return null;
    }

    public Session loginToGetSession(String username, String password)
    {
        //Her må det skje litt logikk
        //TODO: Implementer
        return new Session();
    }

    public void deleteSession(long sessionId)
    {
        sessionRepository.deleteById(sessionId);
    }
}
