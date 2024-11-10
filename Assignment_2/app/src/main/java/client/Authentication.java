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
        // byte[] decodedKey = Base64.getDecoder().decode(serverPublicKey);

        // Generate a PublicKey object from the decoded key bytes
        // PublicKey key = KeyFactory.getInstance(ALGORITHM)
                // .generatePublic(new X509EncodedKeySpec(decodedKey));
        // // FIXME: This throws an exception, somehow the server key is wrong!
        // PublicKey key = KeyFactory.getInstance(ALGORITHM)
        //         .generatePublic(new X509EncodedKeySpec(serverPublicKey.getBytes()));

        // System.out.println("Public key: " + serverPublicKey.toString());

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        System.out.println("end of encrypt for server" + encryptedBytes);
        return encryptedBytes;
    }

    public boolean isSessionValid() {
        return this.sessionId != null;
    }

    public void setServerKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }
}
