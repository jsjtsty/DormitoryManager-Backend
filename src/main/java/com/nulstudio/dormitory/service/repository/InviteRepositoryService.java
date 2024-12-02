package com.nulstudio.dormitory.service.repository;

import com.nulstudio.dormitory.common.NulBloomConstants;
import com.nulstudio.dormitory.domain.cache.CachedInvite;
import com.nulstudio.dormitory.entity.NulInvite;
import com.nulstudio.dormitory.repository.InviteRepository;
import com.nulstudio.dormitory.service.filter.BloomFilterService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InviteRepositoryService {
    @Resource
    private InviteRepository inviteRepository;

    @Resource
    private BloomFilterService bloomFilterService;

    @Cacheable(value = "invite", key = "#inviteCode", unless = "#result == null")
    @NotNull
    public Optional<CachedInvite> findInvite(@NotNull String inviteCode) {
        if (!bloomFilterService.contains(NulBloomConstants.INVITE, inviteCode)) {
            return Optional.empty();
        }
        final Optional<NulInvite> optional = inviteRepository.findByInviteCode(inviteCode);
        if (optional.isPresent()) {
            bloomFilterService.add(NulBloomConstants.INVITE, inviteCode);
        }
        return optional.map(CachedInvite::new);
    }

    @CachePut(value = "invite", key = "#invite.inviteCode")
    @NotNull
    public CachedInvite saveInvite(@NotNull CachedInvite invite) {
        final NulInvite result = inviteRepository.save(invite.restore());
        bloomFilterService.add(NulBloomConstants.INVITE, invite.getInviteCode());
        return new CachedInvite(result);
    }

    @NotNull
    public List<NulInvite> findAll() {
        return inviteRepository.findAll();
    }
}
