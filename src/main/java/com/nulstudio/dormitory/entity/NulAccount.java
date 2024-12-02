package com.nulstudio.dormitory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "nul_account")
public class NulAccount {
    @Id
    @GeneratedValue
    @Column(name = "UID", nullable = false)
    private Integer uid;

    @Size(max = 256)
    @NotNull
    @Column(name = "UserName", nullable = false, length = 256)
    private String userName;

    @Size(max = 1024)
    @NotNull
    @Column(name = "NickName", nullable = false, length = 1024)
    private String nickName;

    @Size(max = 60)
    @NotNull
    @Column(name = "Password", nullable = false, length = 60)
    private String password;

    @Column(name = "Role")
    private int role;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer id) {
        this.uid = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}