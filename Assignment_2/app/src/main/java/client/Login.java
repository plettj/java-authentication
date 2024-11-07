package client;

public class Login {
    String username;
    String password;
    String symmetricKey;

    public Login(String username, String password, String symmetricKey) {
        this.username = username;
        this.password = password;
        this.symmetricKey = symmetricKey;
    }

    public String getUsername() {
        return this.username;
    }  
    
    public String getPassword() {
        return this.password;
    }

    public String getSymmetricKey() {
        return this.symmetricKey;
    }

    public void print() {
        System.out.println(this.username + ", " + this.password + ", " + this.symmetricKey);
    }
}
 