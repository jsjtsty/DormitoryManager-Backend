package com.nulstudio.dormitory.service.repository;

import com.nulstudio.dormitory.common.NulBloomConstants;
import com.nulstudio.dormitory.domain.cache.CachedRole;
import com.nulstudio.dormitory.entity.NulRole;
import com.nulstudio.dormitory.repository.RoleRepository;
import com.nulstudio.dormitory.service.filter.BloomFilterService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityRepositoryService {

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private BloomFilterService bloomFilterService;

    @Cacheable(value = "role", key = "#id", unless = "#result == null")
    @Transactional
    public Optional<CachedRole> findByRoleId(int id) {
        if (!bloomFilterService.contains(NulBloomConstants.ROLE, id)) {
            return Optional.empty();
        }
        final Optional<NulRole> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            bloomFilterService.add(NulBloomConstants.ROLE, id);
        }
        return optional.map(CachedRole::new);
    }

}
