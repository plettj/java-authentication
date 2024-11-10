package authentication;

import java.io.Serializable;

/**
 * Encrypted with `symmetricKey` encryption (theoretically!)
 */
public class VerificationResult implements Serializable {
    private final boolean success;
    private final String message;
    private final String sessionToken;

    public VerificationResult(boolean success, String message, String sessionToken) {
        this.success = success;
        this.message = message;
        this.sessionToken = sessionToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
