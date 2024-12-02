package com.nulstudio.dormitory.service;

import com.nulstudio.dormitory.domain.cache.CachedRole;
import com.nulstudio.dormitory.entity.NulRole;
import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import com.nulstudio.dormitory.service.repository.AuthorityRepositoryService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {

    @Resource
    private AuthorityRepositoryService authorityRepositoryService;

    @NotNull
    public CachedRole getRoleById(int id) {
        final Optional<CachedRole> optional = authorityRepositoryService.findByRoleId(id);
        if (optional.isEmpty()) {
            throw new NulException(NulExceptionConstants.ROLE_NOT_EXIST);
        }
        return optional.get();
    }
}
