package com.nulstudio.dormitory.service.controller;

import com.nulstudio.dormitory.config.NulSecurityConfig;
import com.nulstudio.dormitory.service.CreditService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CreditControllerService {
    @Resource
    private CreditService creditService;

    public long getCurrentUserCredit() {
        return creditService.getCreditByUid(NulSecurityConfig.getContextAccount().getUid());
    }
}
