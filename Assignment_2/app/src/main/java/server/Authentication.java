package server;

import authentication.VerificationResult;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class Authentication {

    private Users users;

    private Map<String, String> sessionStore; // Stores client sessions

    private static final String ALGORITHM = "RSA";
    private KeyPair keyPair = null;

    public Authentication() {
        this.users = new Users();
        this.sessionStore = new HashMap<>();

        // RESOURCE: Public<>private key encryption in Java
        // https://stackoverflow.com/a/39615507/8360465

        // Generate public<>private key pair
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(512, random);

            this.keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public VerificationResult verifyPassword(String clientId, String password) {
        // String storedPassword = passwordStore.get(clientId);
        // if (storedPassword != null && storedPassword.equals(password)) {
        // String sessionId = UUID.randomUUID().toString();
        // sessionStore.put(clientId, sessionId);
        // return new VerificationResult(true, sessionId);
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

    // TODO: Implement this function, and use it when login requests come in.
    public String encryptWithSymmetricKey(String symmetricKey, String data) {
        return "pass";
    }

    public String decryptWithPrivateKey(String data) throws Exception {
        PrivateKey key = KeyFactory.getInstance(ALGORITHM)
                .generatePrivate(new PKCS8EncodedKeySpec(this.keyPair.getPrivate().getEncoded()));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(data.getBytes());

        return decryptedBytes.toString();
    }

    public byte[] getPublicKey() {
        return this.keyPair.getPublic().getEncoded();
    }
}
