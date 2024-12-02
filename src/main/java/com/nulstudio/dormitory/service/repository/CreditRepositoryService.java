package com.nulstudio.dormitory.service.repository;

import com.nulstudio.dormitory.common.CreditChangeAction;
import com.nulstudio.dormitory.common.NulBloomConstants;
import com.nulstudio.dormitory.entity.NulCredit;
import com.nulstudio.dormitory.entity.NulCreditLog;
import com.nulstudio.dormitory.repository.CreditLogRepository;
import com.nulstudio.dormitory.repository.CreditRepository;
import com.nulstudio.dormitory.service.filter.BloomFilterService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CreditRepositoryService {
    @Resource
    private CreditRepository creditRepository;

    @Resource
    private CreditLogRepository creditLogRepository;

    @Lazy
    @Resource
    private CreditRepositoryService creditRepositoryService;

    @Resource
    private BloomFilterService bloomFilterService;

    @Cacheable(value = "credit", key = "#uid", unless = "#result == null")
    @NotNull
    public Optional<String> getCreditByUid(int uid) {
        if (!bloomFilterService.contains(NulBloomConstants.UID, uid)) {
            throw new NoSuchElementException();
        }
        final Optional<NulCredit> optional = creditRepository.findById(uid);
        if (optional.isPresent()) {
            bloomFilterService.add(NulBloomConstants.UID, uid);
        }
        return optional.map(obj -> String.valueOf(obj.getCredit()));
    }

    @CacheEvict(value = "credit", key = "#uid")
    @Transactional
    public boolean addCreditByUid(int uid, int operator, long modification, @NotNull CreditChangeAction action) {
        if (!bloomFilterService.contains(NulBloomConstants.UID, uid) ||
                !bloomFilterService.contains(NulBloomConstants.UID, operator)) {
            return false;
        }

        final Optional<String> optionalTarget = creditRepositoryService.getCreditByUid(uid),
                optionalOperator = creditRepositoryService.getCreditByUid(operator);
        if (optionalTarget.isEmpty() || optionalOperator.isEmpty()) {
            return false;
        }

        final NulCredit credit = new NulCredit();
        credit.setUid(uid);
        credit.setCredit(Long.parseLong(optionalTarget.get()));

        final long previous = credit.getCredit();
        final long current = credit.getCredit() + modification;
        credit.setCredit(current);
        creditRepository.save(credit);

        final NulCreditLog creditLog = new NulCreditLog();
        creditLog.setUid(uid);
        creditLog.setOperator(operator);
        creditLog.setPrevious(previous);
        creditLog.setCurrent(current);
        creditLog.setAction(action.getAction());

        creditLogRepository.save(creditLog);

        bloomFilterService.add(NulBloomConstants.UID, uid);
        bloomFilterService.add(NulBloomConstants.UID, operator);
        return true;
    }

}
