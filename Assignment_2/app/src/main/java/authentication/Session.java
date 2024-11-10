package authentication;

import java.io.Serializable;

public class Session implements Serializable {
    String username;
    String sessionToken;

    public Session(String username, String sessionToken) {
        this.username = username;
        this.sessionToken = sessionToken;
    }
}