package com.nulstudio.dormitory.service;

import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import com.nulstudio.dormitory.service.repository.CreditRepositoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CreditService {
    @Resource
    private CreditRepositoryService creditRepositoryService;

    public long getCreditByUid(int uid) {
        return creditRepositoryService.getCreditByUid(uid)
                .map(Long::parseLong)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
    }

}
