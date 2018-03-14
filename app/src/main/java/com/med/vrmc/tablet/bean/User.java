package com.med.vrmc.tablet.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2018/1/2.
 */

public class User implements Serializable {
    private String username;
    private String password;
    private boolean firstLogin = true;
    private String token;
    public User() {
    }

    public User(String username, String password, boolean firstLogin, String token) {
        this.username = username;
        this.password = password;
        this.firstLogin = firstLogin;
        this.token = token;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
