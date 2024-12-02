package com.nulstudio.dormitory.domain.vo;

import com.nulstudio.dormitory.entity.NulAccount;
import org.jetbrains.annotations.NotNull;

public final class AccountProfileVo {
    private int uid;
    private String userName;
    private String nickName;
    private String role;

    public AccountProfileVo() {}

    public AccountProfileVo(int uid, @NotNull String userName, @NotNull String nickName, @NotNull String role) {
        this.uid = uid;
        this.userName = userName;
        this.nickName = nickName;
        this.role = role;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
