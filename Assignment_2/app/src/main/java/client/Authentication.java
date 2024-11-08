package client;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Authentication {
    private String sessionId;
    private String serverPublicKey = "incorrect-server-public-key";

    private static final String ALGORITHM = "RSA";

    public String encryptForServer(String data) throws Exception {
        System.out.println("Encrypting data for server... (public key: " + serverPublicKey + ")");

        // FIXME: This throws an exception, somehow the server key is wrong!
        PublicKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePublic(new X509EncodedKeySpec(serverPublicKey.getBytes()));

        System.out.println("Public key: " + key.toString());

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());

        return encryptedBytes.toString();
    }

    public boolean isSessionValid() {
        return this.sessionId != null;
    }

    public void setServerKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }
}
