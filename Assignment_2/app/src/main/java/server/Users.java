package server;

import java.util.HashMap;

import authentication.Hashing;
import server.Printer.Role;

public class Users {
    private HashMap<String, User> userMap = new HashMap<String, User>();

    public Users() {
        User[] exampleUsers = {
            new User("admin", "unHASHED&^*86pa55", Role.ADMIN),
            new User("manager_jane", "m@n@g3r_2024", Role.MANAGER),
            new User("user_john", "johndoepass123", Role.USER),
            new User("superadmin", "super$ecret2023", Role.ADMIN),
            new User("manager_bob", "bob1234!secure", Role.MANAGER),
            new User("user_mary", "m@rySecure001", Role.USER),
            new User("admin_tech", "techAdmin#456", Role.ADMIN),
            new User("manager_alice", "AliceM@nag3r", Role.MANAGER),
            new User("user_charlie", "charliePass!@#", Role.USER),
            new User("admin_root", "RootPass*99", Role.ADMIN)
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
