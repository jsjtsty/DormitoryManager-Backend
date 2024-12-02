package com.nulstudio.dormitory.domain.dto;

import org.jetbrains.annotations.NotNull;

public record AccountRegisterDto(
        @NotNull String userName,
        @NotNull String password,
        @NotNull String nickName,
        @NotNull String inviteCode
) {
}
