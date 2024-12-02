package com.nulstudio.dormitory.domain.cache;

import com.nulstudio.dormitory.entity.NulInvite;
import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class CachedInvite {
    private Integer id;
    private String inviteCode;
    private int remaining;
    private int role;
    private Timestamp creationTime;
    private Timestamp expireTime;
    private NulInvite.Status status;

    public CachedInvite() {}

    public CachedInvite(@NotNull NulInvite invite) {
        this.id = invite.getId();
        this.inviteCode = invite.getInviteCode();
        this.remaining = invite.getRemaining();
        this.role = invite.getRole();
        this.creationTime = invite.getCreationTime();
        this.expireTime = invite.getExpireTime();
        this.status = invite.getStatus();
    }

    public void validate() throws NulException {
        if (status == NulInvite.Status.Blocked)
            throw new NulException(NulExceptionConstants.INVITE_CODE_BLOCKED);
        if (remaining <= 0)
            throw new NulException(NulExceptionConstants.INVITE_CODE_USED_UP);
        if (new Timestamp(System.currentTimeMillis()).after(expireTime))
            throw new NulException(NulExceptionConstants.EXPIRED_INVITE_CODE);
    }

    @NotNull
    public NulInvite restore() {
        final NulInvite invite = new NulInvite();
        if (id != null) {
            invite.setId(id);
        }
        invite.setInviteCode(inviteCode);
        invite.setRemaining(remaining);
        invite.setRole(role);
        invite.setCreationTime(creationTime);
        invite.setExpireTime(expireTime);
        invite.setStatus(status);
        return invite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public NulInvite.Status getStatus() {
        return status;
    }

    public void setStatus(NulInvite.Status status) {
        this.status = status;
    }
}
