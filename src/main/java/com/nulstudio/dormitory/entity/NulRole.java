package com.nulstudio.dormitory.entity;

import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nul_role")
public class NulRole {

    private static final String ROLE_PREFIX = "ROLE_";

    public enum Status {
        Normal, Blocked
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "Name", nullable = false, length = 64)
    private String name;

    @NotNull
    @ColumnDefault("'Normal'")
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "nul_role_permission",
            joinColumns = @JoinColumn(name = "RoleID"),
            inverseJoinColumns = @JoinColumn(name = "PermissionID")
    )
    private Set<NulPermission> permissions = new HashSet<>();

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<NulPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<NulPermission> permissions) {
        this.permissions = permissions;
    }

}