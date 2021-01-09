package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private Integer userid;
    private String username;
    private String password;
    private String salt;
    private String firstname;
    private String lastname;

    public User(Integer userid, String username, String password, String salt, String firstname, String lastname) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
