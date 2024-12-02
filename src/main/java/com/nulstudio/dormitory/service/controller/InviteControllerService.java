package com.nulstudio.dormitory.service.controller;

import com.nulstudio.dormitory.common.NulConstants;
import com.nulstudio.dormitory.domain.cache.CachedInvite;
import com.nulstudio.dormitory.domain.cache.CachedRole;
import com.nulstudio.dormitory.domain.vo.InviteVo;
import com.nulstudio.dormitory.entity.NulInvite;
import com.nulstudio.dormitory.entity.NulRole;
import com.nulstudio.dormitory.service.AuthorityService;
import com.nulstudio.dormitory.service.InviteService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class InviteControllerService {
    @Resource
    private InviteService inviteService;

    @Resource
    private AuthorityService authorityService;

    @NotNull
    public InviteVo generate(int roleId, int remaining, @Nullable Timestamp expireTime, boolean blocked) {
        if (expireTime == null) {
            expireTime = new Timestamp(System.currentTimeMillis() + NulConstants.INVITE_CODE_DEFAULT_EXPIRATION_TIME);
        }
        final CachedRole role = authorityService.getRoleById(roleId);
        final CachedInvite invite = inviteService.generate(role, remaining, expireTime, blocked);
        return new InviteVo(invite.getInviteCode(), invite.getRemaining(),
                invite.getExpireTime(), invite.getStatus().toString(), invite.getRole());
    }
}
