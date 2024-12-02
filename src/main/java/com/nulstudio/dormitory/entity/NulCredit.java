package com.nulstudio.dormitory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nul_credit")
public class NulCredit {
    @Id
    @Column(name = "UID")
    private int uid;

    @Column(name = "Credit")
    private long credit;

    public int getUid() {
        return uid;
    }

    public void setUid(int id) {
        this.uid = id;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }
}
