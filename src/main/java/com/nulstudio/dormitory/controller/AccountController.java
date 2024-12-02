package com.nulstudio.dormitory.controller;

import com.nulstudio.dormitory.common.NulConstants;
import com.nulstudio.dormitory.common.NulResult;
import com.nulstudio.dormitory.domain.dto.AccountRegisterDto;
import com.nulstudio.dormitory.domain.vo.AccountProfileVo;
import com.nulstudio.dormitory.service.AccountService;
import com.nulstudio.dormitory.util.jwt.NulJwtToken;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<NulResult<Void>> login(
            @RequestParam @NotNull String userName,
            @RequestParam @NotNull String password
    ) {
        final NulJwtToken token = accountService.login(userName, password);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, NulConstants.BEARER_TOKEN_PREFIX + token.token());
        return ResponseEntity.ok().headers(headers).body(NulResult.response());
    }

    @PostMapping
    public ResponseEntity<NulResult<Void>> register(@RequestBody @NotNull AccountRegisterDto accountRegisterDto) {
        final NulJwtToken token = accountService.register(accountRegisterDto);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, NulConstants.BEARER_TOKEN_PREFIX + token.token());
        return ResponseEntity.ok().headers(headers).body(NulResult.response());
    }

    @GetMapping("/profile")
    public NulResult<AccountProfileVo> getProfile() {
        return NulResult.response(accountService.getCurrentProfile());
    }
}
