package authentication;

import java.security.MessageDigest;

public class Hashing {
    String hash;

    public Hashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");
            messageDigest.update(password.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            this.hash = sb.toString();
        } catch (Exception e) {
            System.out.println("Error: Algorithm not found.");
            e.printStackTrace();
        }
    }

    public String getHash() {
        return this.hash;
    }
}
