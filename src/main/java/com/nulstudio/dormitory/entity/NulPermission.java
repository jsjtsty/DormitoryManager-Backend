package com.nulstudio.dormitory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "nul_permission")
public class NulPermission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "Name", nullable = false, length = 64)
    private String name;

    @Lob
    @Column(name = "Comment")
    private String comment;

    @Override
    public String getAuthority() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}