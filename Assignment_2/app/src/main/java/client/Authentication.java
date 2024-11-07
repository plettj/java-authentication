package client;

import authentication.VerificationResult;

public class Authentication {
    private String sessionId;

    public VerificationResult authenticate(String clientId, String password) {
        // Simulate sending password to server and receiving a VerificationResult
        VerificationResult result = new VerificationResult(true, "mock-session-id");
        if (result.isSuccess()) {
            this.sessionId = result.getMessage();
        }
        return result;
    }

    public boolean isSessionValid() {
        return this.sessionId != null;
    }
}
