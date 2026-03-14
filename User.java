package officialInternalAssessment;

import java.io.*;
import java.util.*;

public abstract class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Polymorphic method (overridden in subclasses)
    public abstract boolean isStudent();

    @Override
    public String toString() {
        return role + ": " + username;
    }

}


