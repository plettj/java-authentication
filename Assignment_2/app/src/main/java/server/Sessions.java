package server;

import java.util.HashMap;
import java.time.Instant;

public class Sessions {
    private HashMap<String, Session> sessions;

    public Sessions() {
        this.sessions = new HashMap<>();
    }

    public class Session {
        String username;
        long expirationEpoch;
        String sessionToken;
        String symmetricKey;
        
        Session(String sessionId, String username, String sessionToken, String symmetricKey) {
            this.username = username;
            this.expirationEpoch = Instant.now().getEpochSecond() + 60;
            this.sessionToken = sessionToken;
            this.symmetricKey = symmetricKey;
        }

        public void updateTime() {
            this.expirationEpoch = Instant.now().getEpochSecond() + 60;
        }   
    }

    public void updateTime(String sessionToken) {
        this.sessions.get("ID-" + sessionToken).updateTime();
    }
    /**
     * Returns `true` only if created session, `false` if it already existed.
     */
    public boolean createSession(String username, String sessionToken, String symmetricKey) {
        boolean alreadyExists = validateSession(username, sessionToken);

        if (alreadyExists) {
            this.sessions.get(sessionToken).updateTime();
        } else {
            this.sessions.put("ID-" + sessionToken, new Session("ID-" + sessionToken, username, sessionToken, symmetricKey));
        }

        return !alreadyExists;
    }

    public boolean validateSession(String username, String sessionToken) {
        System.out.println("Validating session... {" + username + ", " + sessionToken + "}");
        if (this.sessions.get("ID-" + sessionToken) == null) {
            return false;
        }
        if (!this.sessions.get("ID-" + sessionToken).username.equals(username)) {
            return false;
        }
        if (this.sessions.get("ID-" + sessionToken).expirationEpoch < Instant.now().getEpochSecond()) {
            return false;
        }
        return true;
    }
}