package server;

import authentication.VerificationResult;
import server.Printer.Role;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;

public class Authentication {

    private Users users;
    private Sessions sessions;

    private static final String ALGORITHM = "RSA";
    private KeyPair keyPair = null;

    public Authentication() {
        this.users = new Users();
        this.sessions = new Sessions();

        // RESOURCE: Public<>private key encryption in Java
        // https://stackoverflow.com/a/39615507/8360465

        // Generate public<>private key pair
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);

            this.keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void print() {
        System.out.println("All Users in Server Authentication:");
        this.users.print();
    }

    // TODO: Implement this function, and use it when login requests come in.
    public String encryptWithSymmetricKey(String symmetricKey, String data) {
        return "pass";
    }

    public String authenticate(byte[] data) throws Exception {
        String decryptedLogin = decryptWithPrivateKey(data);
        
        int usernameIndex = 0;
        int passwordIndex = decryptedLogin.indexOf(" ") + 1;
        int symmetricKeyIndex = decryptedLogin.lastIndexOf(" ") + 1;

        String username = decryptedLogin.substring(usernameIndex, passwordIndex - 1);
        String password = decryptedLogin.substring(passwordIndex, symmetricKeyIndex - 1);
        String symmetricKey = decryptedLogin.substring(symmetricKeyIndex);
        Role role = this.users.authenticateUser(username, password);
        boolean correctPassword = role != null;
        String sessionToken = UUID.randomUUID().toString();
        System.out.println("Session token: " + sessionToken);
        if (correctPassword) {
            this.sessions.createSession(username, sessionToken, symmetricKey);
        } else {
            // Password is the sessionToken!!!
            boolean valid = this.sessions.validateSession(username, password);
            if (valid) {
                this.sessions.updateTime(sessionToken);
                role = this.users.getRoleByUsername(username);
                sessionToken = password;
            } else {
                role = null;
                sessionToken = "INVALIDSESSION";
            }  
        }
        String r = role != null ? role.toString() : "INVALID";
        return r + " " + sessionToken;
    }

    public String decryptWithPrivateKey(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, this.keyPair.getPrivate());

        byte[] decryptedBytes = cipher.doFinal(data);

        String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

        return decryptedString;
    }

    public PublicKey getPublicKey() {
        return this.keyPair.getPublic();
    }

    public Role getRoleByUsername(String username) {
        return users.getRoleByUsername(username);
    }
    
    public boolean validateSession(String username, String sessionToken) {
        return this.sessions.validateSession(username, sessionToken);
        
    }
}
