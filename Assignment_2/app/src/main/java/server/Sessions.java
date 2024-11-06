package server;

import java.util.ArrayList;

public class Sessions {
    private ArrayList<Session> sessions;

    public Sessions() {
        this.sessions = new ArrayList<>();
    }

    public class Session {
        String sessionId;
        String username;
        String expirationTime;
        String sessionToken;
        String symmetricKey;
        
        Session(String sessionId, String username, String expirationTime, String sessionToken, String symmetricKey) {
            this.sessionId = sessionId;
            this.username = username;
            this.expirationTime = expirationTime;
            this.sessionToken = sessionToken;
            this.symmetricKey = symmetricKey;
        }
    }
    

}
