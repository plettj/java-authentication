package server;

import java.util.ArrayList;

import server.Printer.Role;
import java.security.MessageDigest;

public class Users {
    private ArrayList<User> users = new ArrayList<User>();

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
            this.users.add(user);
        }
    }

    public Role getRoleByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user.role;
            }
        }

        return null; // User wasn't found.
    }

    public Role authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user.role;
            }
        }

        return null; // User wasn't found.
    }

    public void print() {
        for (User user : users) {
            System.out.println("Username: " + user.username + ", Password: " + user.password + ", Role: " + user.role);
        }
    }

    public class User {
        public String username;
        public String password;
        public Role role;
        
        User(String username, String password, Role role) {
            
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");
                messageDigest.update(password.getBytes());
                byte[] digest = messageDigest.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                password = sb.toString();
            } catch (Exception e) {
                System.out.println("Error: Algorithm not found.");
                e.printStackTrace();
            }

            this.username = username;
            this.password = password;
            this.role = role;
        }
    }
}
