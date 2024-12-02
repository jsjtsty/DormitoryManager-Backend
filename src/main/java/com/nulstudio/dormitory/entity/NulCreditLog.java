package com.nulstudio.dormitory.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "nul_credit_log")
public class NulCreditLog {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "UID")
    private int uid;

    @Column(name = "Operator")
    private int operator;

    @Column(name = "Previous")
    private long previous;

    @Column(name = "Current")
    private long current;

    @Column(name = "Modification")
    private long modification;

    @Column(name = "Action")
    private int action;

    @Column(name = "Time")
    private Timestamp time;

    @Column(name = "Comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getPrevious() {
        return previous;
    }

    public void setPrevious(long previous) {
        this.previous = previous;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getModification() {
        return modification;
    }

    public void setModification(long modification) {
        this.modification = modification;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
