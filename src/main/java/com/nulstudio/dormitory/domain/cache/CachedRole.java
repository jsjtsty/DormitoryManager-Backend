package com.nulstudio.dormitory.domain.cache;

import com.nulstudio.dormitory.entity.NulPermission;
import com.nulstudio.dormitory.entity.NulRole;
import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CachedRole {
    private static final String ROLE_PREFIX = "ROLE_";

    private Integer id;
    private String name;
    private NulRole.Status status;
    private List<String> permissions;

    public CachedRole() {}

    public CachedRole(@NotNull NulRole role) {
        this.id = role.getId();
        this.name = role.getName();
        this.status = role.getStatus();

        final List<String> permissions = new ArrayList<>(role.getPermissions().size());
        for (final NulPermission permission : role.getPermissions()) {
            permissions.add(permission.getAuthority());
        }
        this.permissions = permissions;
    }

    @NotNull
    public Set<GrantedAuthority> toSpringAuthorities() {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        for (final String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + name));
        return authorities;
    }

    public void validate() {
        if (status == NulRole.Status.Blocked)
            throw new NulException(NulExceptionConstants.ROLE_BLOCKED);
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

    public NulRole.Status getStatus() {
        return status;
    }

    public void setStatus(NulRole.Status status) {
        this.status = status;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
