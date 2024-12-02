package com.nulstudio.dormitory.entity;

import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Table(name = "nul_invite")
public class NulInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 6)
    @NotNull
    @Column(name = "InviteCode", nullable = false, length = 6)
    private String inviteCode;

    @NotNull
    @Column(name = "Remaining", nullable = false)
    private int remaining;

    @NotNull
    @Column(name = "Role", nullable = false)
    private int role;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CreationTime", nullable = false)
    private Timestamp creationTime;

    @NotNull
    @Column(name = "ExpireTime", nullable = false)
    private Timestamp expireTime;

    @NotNull
    @ColumnDefault("'Normal'")
    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;

    public enum Status {
        Normal,
        Blocked
    }

    @PrePersist
    public void prePersist() {
        if (creationTime == null) {
            creationTime = new Timestamp(System.currentTimeMillis());
        }
        if (status == null) {
            status = Status.Normal;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
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

}