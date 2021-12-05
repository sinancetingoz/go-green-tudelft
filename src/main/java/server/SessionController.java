package server;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import services.SessionService;

@RestController
public class SessionController {



    SessionService ss = new SessionService();


    /** Check whether the sessionID exists.
     * @param sessionId the sessionID to check for
     * @return the username of the holder of the sessionID
     */
    @GetMapping("/user/{sessionID}")
    public String getUser(@PathVariable("sessionID") String sessionId ) {

        String username = null;
        if (ss.sessionExists(sessionId)) {
            username = ss.getAllSessions().get(sessionId).getUsername();
        }

        return username;
    }

    /**
     * Logs a user out.
     * @param sessionId the sessionID of the user
     */
    @GetMapping("logout/{sessionID}")
    public void logOut(@PathVariable("sessionID") String sessionId) {
        if (ss.getAllSessions().containsKey(sessionId)) {
            ss.getAllSessions().remove(sessionId);
        }
    }
}
