package com.nulstudio.dormitory.domain.vo;

import java.sql.Timestamp;

public final class InviteVo {
    private String inviteCode;
    private int remaining;
    private Timestamp expireTime;
    private String status;
    private int roleId;

    public InviteVo() {}

    public InviteVo(String inviteCode, int remaining, Timestamp expireTime, String status, int roleId) {
        this.inviteCode = inviteCode;
        this.remaining = remaining;
        this.expireTime = expireTime;
        this.status = status;
        this.roleId = roleId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
