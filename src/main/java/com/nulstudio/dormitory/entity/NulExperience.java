package com.nulstudio.dormitory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "nul_experience")
public class NulExperience {
    @Id
    @Column(name = "UID", nullable = false)
    private Integer id;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "Experience", nullable = false)
    private Long experience;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

}