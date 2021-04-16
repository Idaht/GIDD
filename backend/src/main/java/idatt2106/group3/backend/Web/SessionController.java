package idatt2106.group3.backend.Web;

import idatt2106.group3.backend.Model.Session;
import idatt2106.group3.backend.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;


    @RequestMapping("/{session_id}")
    public Session getSession(@PathVariable("session_id") long sessionId) {
        return sessionService.getSession(sessionId);
    }

    @PostMapping
    public ResponseEntity<Session> loginToGetSession(@RequestBody String username, String password) {

        //TODO: Implementer logikken som faktsik validerer passord og brukernavn, da hashing og salting
        Session session = sessionService.loginToGetSession(username, password);
        ResponseEntity<Session> returnSession;
        if (1 == 1) {
            returnSession = new ResponseEntity<>(session, HttpStatus.OK);
        }
        else {
            returnSession = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return returnSession;
    }

    @DeleteMapping("/{session_id}")
    public void deleteSession(@PathVariable("session_id") long sessionId) {
        sessionService.deleteSession(sessionId);
    }
}
