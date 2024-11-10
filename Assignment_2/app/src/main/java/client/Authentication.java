package client;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class Authentication {
    private String sessionId;
    private PublicKey serverPublicKey;

    private static final String ALGORITHM = "RSA";

    public byte[] encryptForServer(String data) throws Exception {
        System.out.println("Encrypting data for server... (public key: " + serverPublicKey + ")");
        System.out.println("Data is " + data);
        
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return encryptedBytes;
    }

    public boolean isSessionValid() {
        return this.sessionId != null;
    }

    public void setServerKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }
}
