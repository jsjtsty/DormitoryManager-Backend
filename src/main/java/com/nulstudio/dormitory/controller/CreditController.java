package com.nulstudio.dormitory.controller;

import com.nulstudio.dormitory.common.NulResult;
import com.nulstudio.dormitory.service.controller.CreditControllerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/credit")
public class CreditController {
    @Resource
    private CreditControllerService creditControllerService;

    @GetMapping
    public NulResult<Long> getCredit() {
        return NulResult.response(creditControllerService.getCurrentUserCredit());
    }
}
