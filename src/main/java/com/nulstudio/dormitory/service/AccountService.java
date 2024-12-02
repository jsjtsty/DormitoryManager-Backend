package com.nulstudio.dormitory.service;

import com.nulstudio.dormitory.config.NulSecurityConfig;
import com.nulstudio.dormitory.domain.cache.CachedAccount;
import com.nulstudio.dormitory.domain.cache.CachedRole;
import com.nulstudio.dormitory.domain.dto.AccountRegisterDto;
import com.nulstudio.dormitory.domain.vo.AccountProfileVo;
import com.nulstudio.dormitory.entity.NulAccount;
import com.nulstudio.dormitory.entity.NulRole;
import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import com.nulstudio.dormitory.service.repository.AccountRepositoryService;
import com.nulstudio.dormitory.util.jwt.NulJwtToken;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Resource
    private AccountRepositoryService accountRepositoryService;

    @Resource
    private AuthorityService authorityService;

    @Resource
    private InviteService inviteService;

    @NotNull
    public NulJwtToken login(@NotNull String userName, @NotNull String password) {
        final Integer uid = accountRepositoryService.getUidByUserName(userName)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
        final CachedAccount account = accountRepositoryService.getAccountByUid(uid)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
        final CachedRole role = authorityService.getRoleById(account.getRole());
        role.validate();
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, account.getPassword())) {
            throw new NulException(NulExceptionConstants.WRONG_PASSWORD);
        }
        return NulJwtToken.generate(new NulJwtToken.NulJwtTokenProperties(account.getUid()));
    }

    @NotNull
    @Transactional
    public NulJwtToken register(@NotNull AccountRegisterDto accountRegisterDto) {
        if (accountRepositoryService.getUidByUserName(accountRegisterDto.userName()).isPresent()) {
            throw new NulException(NulExceptionConstants.USER_ALREADY_EXIST);
        }

        final int role = inviteService.registerInvite(accountRegisterDto.inviteCode());
        final String encryptedPassword = new BCryptPasswordEncoder().encode(accountRegisterDto.password());

        NulAccount account = new NulAccount();
        account.setUserName(accountRegisterDto.userName());
        account.setPassword(encryptedPassword);
        account.setNickName(accountRegisterDto.nickName());
        account.setRole(role);
        account = accountRepositoryService.saveAccount(account);

        return NulJwtToken.generate(new NulJwtToken.NulJwtTokenProperties(account.getUid()));
    }

    @NotNull
    public AccountProfileVo getCurrentProfile() {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        final String roleName = authorityService.getRoleById(account.getRole()).getName();
        return new AccountProfileVo(account.getUid(), account.getUserName(), account.getNickName(), roleName);
    }

    @NotNull
    public CachedAccount getAccountByUid(int uid) {
        return accountRepositoryService.getAccountByUid(uid)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
    }

    public int getUidByUserName(@NotNull String userName) {
        return accountRepositoryService.getUidByUserName(userName)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final int uid = getUidByUserName(username);
        final CachedAccount account = this.getAccountByUid(uid);
        final CachedRole role = authorityService.getRoleById(account.getRole());
        return new User(account.getUserName(), account.getPassword(), role.toSpringAuthorities());
    }
}
