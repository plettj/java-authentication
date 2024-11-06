package client;

import authentication.VerificationResult;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;

public class Authentication {
    private String sessionId;
    // private final String passwordFile = "src/passwords/client_password.txt";

    // public void storePassword(String password) {
    //     try {
    //         Files.write(Paths.get(passwordFile), password.getBytes());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public String loadPassword() {
    //     try {
    //         return new String(Files.readAllBytes(Paths.get(passwordFile)));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

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
