package com.nulstudio.dormitory.config;

import com.nulstudio.dormitory.common.NulBloomConstants;
import com.nulstudio.dormitory.entity.NulInvite;
import com.nulstudio.dormitory.entity.NulRole;
import com.nulstudio.dormitory.repository.AccountRepository;
import com.nulstudio.dormitory.repository.InviteRepository;
import com.nulstudio.dormitory.repository.RoleRepository;
import com.nulstudio.dormitory.service.filter.BloomFilterService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NulBloomFilterLoader {

    private static final int BATCH_SIZE = 10000;

    @Resource
    private InviteRepository inviteRepository;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private BloomFilterService bloomFilterService;

    private interface NulBatchedProvider<T> {
        @NotNull
        Page<T> provide(@NotNull Pageable pageable);
    }

    private interface NulBatchedCallback<T> {
        void apply(@NotNull List<T> list);
    }

    private <T> void iterateBatchedInterface(
            @NotNull NulBatchedProvider<T> batchedInterface,
            @NotNull NulBatchedCallback<T> callback
    ) {
        int page = 0;
        Pageable pageable = PageRequest.of(0, BATCH_SIZE);
        Page<T> result;
        do {
            result = batchedInterface.provide(pageable);
            if (result.hasContent())
                callback.apply(result.getContent());
            ++page;
            pageable = PageRequest.of(page, BATCH_SIZE);
        } while (result.hasNext());
    }

    @PostConstruct
    public void initInvite() {
        this.iterateBatchedInterface(
                (pageable) -> inviteRepository.findAll(pageable),
                (invites) -> bloomFilterService.addStrings(
                        NulBloomConstants.INVITE,
                        invites.stream().map(NulInvite::getInviteCode).toList()
                )
        );
    }

    @PostConstruct
    public void initAccount() {
        this.iterateBatchedInterface(
                (pageable) -> accountRepository.findAll(pageable),
                (accounts) -> {
                    final List<Integer> uidList = new ArrayList<>(accounts.size());
                    final List<String> userNameList = new ArrayList<>(accounts.size());
                    accounts.forEach((account) -> {
                        uidList.add(account.getUid());
                        userNameList.add(account.getUserName());
                    });
                    bloomFilterService.addIntegers(NulBloomConstants.UID, uidList);
                    bloomFilterService.addStrings(NulBloomConstants.USER_NAME, userNameList);
                }
        );
    }

    @PostConstruct
    public void initRole() {
        this.iterateBatchedInterface(
                (pageable) -> roleRepository.findAll(pageable),
                (roles) -> bloomFilterService.addIntegers(
                        NulBloomConstants.ROLE,
                        roles.stream().map(NulRole::getId).toList()
                )
        );
    }
}
