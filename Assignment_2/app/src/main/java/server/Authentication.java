package server;

import authentication.VerificationResult;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Authentication {
    private Map<String, String> passwordStore; // Stores client passwords
    private Map<String, String> sessionStore; // Stores client sessions

    public Authentication() {
        this.passwordStore = new HashMap<>();
        this.sessionStore = new HashMap<>();
    }

    public VerificationResult verifyPassword(String clientId, String password) {
        String storedPassword = passwordStore.get(clientId);
        if (storedPassword != null && storedPassword.equals(password)) {
            String sessionId = UUID.randomUUID().toString();
            sessionStore.put(clientId, sessionId);
            return new VerificationResult(true, sessionId);
        } else {
            return new VerificationResult(false, "Invalid password");
        }
    }

    public boolean validateSession(String clientId, String sessionId) {
        return sessionId.equals(sessionStore.get(clientId));
    }
}
