package server;

import java.util.HashMap;

import authentication.Hashing;
import server.Printer.Role;

public class Users {
    private HashMap<String, User> userMap = new HashMap<String, User>();

    public Users() {
        User[] exampleUsers = {
            new User("Alice", "alice123", Role.MANAGER),
            new User("Bob", "bob123", Role.SERVICE),
            new User("David", "david123", Role.USER),
        };

        for (User user : exampleUsers) {
            this.userMap.put(user.username, user);
        }
    }

    public Role getRoleByUsername(String username) {
        User user = userMap.get(username);
        return (user != null) ? user.role : null;
    }

    public Role authenticateUser(String username, String password) {
        User user = userMap.get(username);
        if (user != null && user.password.equals(password)) {
            return user.role;
        }

        return null;
    }

    public void print() {
        for (User user: userMap.values()) {
            System.out.println("Username: " + user.username + ", Password: " + user.password + ", Role: " + user.role);
        }
    }

    public class User {
        public String username;
        public String password;
        public Role role;
        
        User(String username, String password, Role role) {
            
            // Hash the password with SHA3-256
            Hashing hash = new Hashing(password);
            this.username = username;
            this.password = hash.getHash();
            this.role = role;
        }
    }
}
