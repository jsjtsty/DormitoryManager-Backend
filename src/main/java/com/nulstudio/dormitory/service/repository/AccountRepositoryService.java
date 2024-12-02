package com.nulstudio.dormitory.service.repository;

import com.nulstudio.dormitory.common.NulBloomConstants;
import com.nulstudio.dormitory.domain.cache.CachedAccount;
import com.nulstudio.dormitory.entity.NulAccount;
import com.nulstudio.dormitory.repository.AccountRepository;
import com.nulstudio.dormitory.service.filter.BloomFilterService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountRepositoryService {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private BloomFilterService bloomFilterService;

    @Cacheable(value = "account", key = "#uid", unless = "#result == null")
    @NotNull
    public Optional<CachedAccount> getAccountByUid(int uid) {
        if (!bloomFilterService.contains(NulBloomConstants.UID, uid)) {
            return Optional.empty();
        }
        final Optional<NulAccount> optional = accountRepository.findById(uid);
        if (optional.isPresent()) {
            bloomFilterService.add(NulBloomConstants.UID, uid);
        }
        return optional.map(CachedAccount::new);
    }

    @Cacheable(value = "userName", key = "#userName", unless = "#result == null")
    @NotNull
    public Optional<Integer> getUidByUserName(@NotNull String userName) {
        if (!bloomFilterService.contains(NulBloomConstants.USER_NAME, userName)) {
            return Optional.empty();
        }
        final Optional<NulAccount> optional = accountRepository.findByUserName(userName);
        final Optional<Integer> result;
        if (optional.isPresent()) {
            bloomFilterService.add(NulBloomConstants.USER_NAME, userName);
            result = Optional.of(optional.get().getUid());
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public NulAccount saveAccount(@NotNull NulAccount account) {
        final NulAccount result = accountRepository.save(account);
        final Cache accountCache = cacheManager.getCache("account"),
                userNameCache = cacheManager.getCache("userName");
        if (accountCache != null) {
            accountCache.put(result.getUid(), result);
        }
        if (userNameCache != null) {
            userNameCache.put(result.getUserName(), account.getUid());
        }
        return result;
    }

    @NotNull
    public Page<NulAccount> getPagedAccounts(int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAll(pageable);
    }
}
