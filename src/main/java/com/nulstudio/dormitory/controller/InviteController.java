package com.nulstudio.dormitory.controller;

import com.nulstudio.dormitory.common.NulResult;
import com.nulstudio.dormitory.domain.vo.InviteVo;
import com.nulstudio.dormitory.service.controller.InviteControllerService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/account/invite")
public class InviteController {
    @Resource
    private InviteControllerService inviteControllerService;

    @PostMapping
    public NulResult<InviteVo> generate(
            @RequestParam int roleId,
            @RequestParam(defaultValue = "1") int remaining,
            @RequestParam(required = false) @Nullable Timestamp expireTime,
            @RequestParam(defaultValue = "false") boolean blocked
    ) {
        return NulResult.response(inviteControllerService.generate(roleId, remaining, expireTime, blocked));
    }
}
