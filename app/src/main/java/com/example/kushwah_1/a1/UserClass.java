package com.example.kushwah_1.a1;

public class UserClass {
    String Uid;
    String username;
    String Email;

    public UserClass(String uid, String username, String email) {
        Uid = uid;
        this.username = username;
        Email = email;
    }

    public UserClass(String username, String email) {
        this.username = username;
        Email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
