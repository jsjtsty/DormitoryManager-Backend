package com.nulstudio.dormitory.domain.cache;

import com.nulstudio.dormitory.entity.NulAccount;
import org.jetbrains.annotations.NotNull;

public class CachedAccount {
    private Integer uid;
    private String userName;
    private String nickName;
    private String password;
    private int role;

    public CachedAccount() {}

    public CachedAccount(@NotNull NulAccount account) {
        this.uid = account.getUid();
        this.userName = account.getUserName();
        this.nickName = account.getNickName();
        this.password = account.getPassword();
        this.role = account.getRole();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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
