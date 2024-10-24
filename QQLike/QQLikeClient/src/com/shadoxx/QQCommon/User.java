package com.shadoxx.QQCommon;

import java.io.Serializable;
import java.math.BigInteger;

public class User implements Serializable {
    private static final long serialVersionUID = 430932690523565945L;


    private BigInteger uid;
    private String UserName;
    private String password;

    public User() {}

    public User(String password, BigInteger uid) {
        this.password = password;
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getUserId() {
        return uid;
    }

    public void setUserId(BigInteger userId) {
        this.uid = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
