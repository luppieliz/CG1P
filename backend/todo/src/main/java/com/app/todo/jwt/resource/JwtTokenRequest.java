package com.app.todo.jwt.resource;

import java.io.Serializable;

public class  JwtTokenRequest implements Serializable {

    private static final long serialVersionUID = -5616176897013108345L;

    private String username;
    private String password;

//    eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTYzMTY4MTM4MywiaWF0IjoxNjMxMDc2NTgzfQ.ZK41AOaYg5EQWe6p-lgw6XkkJaDNIxGbb0pZWxnDRD7Th73TprPWGD-nzfzUxKt40J1GpI_f1e6927ln4viRsg

    public JwtTokenRequest() {
        super();
    }

    public JwtTokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
