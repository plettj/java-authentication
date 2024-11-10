package client;

public class Login {
    String username;
    String passwordOrToken;
    String symmetricKey;

    public Login(String username, String passwordOrToken, String symmetricKey) {
        this.username = username;
        this.passwordOrToken = passwordOrToken;
        this.symmetricKey = symmetricKey;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPasswordOrToken() {
        return this.passwordOrToken;
    }

    public String getSymmetricKey() {
        return this.symmetricKey;
    }

    public void print() {
        System.out.println(
                "Client login data: {" + this.username + ", " + this.passwordOrToken + ", " + this.symmetricKey + "}");
    }

    public String toString() {
        return this.username + " " + this.passwordOrToken + " " + this.symmetricKey;
    }
}
