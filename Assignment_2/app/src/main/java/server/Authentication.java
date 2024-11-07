package server;

import authentication.VerificationResult;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Authentication {

    private Users users;
    
    private Map<String, String> sessionStore; // Stores client sessions

    public Authentication() {
        this.users = new Users();
        this.sessionStore = new HashMap<>();
    }

    public VerificationResult verifyPassword(String clientId, String password) {
        // String storedPassword = passwordStore.get(clientId);
        // if (storedPassword != null && storedPassword.equals(password)) {
        //     String sessionId = UUID.randomUUID().toString();
        //     sessionStore.put(clientId, sessionId);
        //     return new VerificationResult(true, sessionId);
        // } else {
            return new VerificationResult(false, "Invalid password");
        // }
    }

    public boolean validateSession(String clientId, String sessionId) {
        return sessionId.equals(sessionStore.get(clientId));
    }

    public void print() {
        System.out.println("All Users in Server Authentication:");
        this.users.print();

        System.out.println("All Sessions in Server Authentication:");
        for (Map.Entry<String, String> entry : sessionStore.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
