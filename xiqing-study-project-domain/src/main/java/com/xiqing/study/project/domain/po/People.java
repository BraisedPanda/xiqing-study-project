package com.xiqing.study.project.domain.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {

    private static final long serialVersionUID = 5553645142587258329L;

    private Integer id;

    private String userName;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}