package com.nulstudio.dormitory.config;

import com.nulstudio.dormitory.domain.cache.CachedAccount;
import com.nulstudio.dormitory.domain.cache.CachedRole;
import com.nulstudio.dormitory.service.AccountService;
import com.nulstudio.dormitory.service.AuthorityService;
import com.nulstudio.dormitory.util.jwt.NulJwtToken;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for JWT Token.
 * @author nullsty
 * @since 2.0
 */
@Component
public final class NulJwtTokenFilter extends OncePerRequestFilter {

    /**
     * HTTP Header for authentication.
     */
    private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

    /**
     * Prefix for Bearer Token.
     */
    private static final String HTTP_HEADER_TOKEN_PREFIX = "Bearer ";

    @Resource
    private AccountService accountService;

    @Resource
    private AuthorityService authorityService;


    private void checkJwtToken(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        // 1. Get authentication information from HTTP Header.
        final String requestTokenHeader = request.getHeader(HTTP_HEADER_AUTHORIZATION);

        // 2. Check and remove prefix from Bearer Token.
        if (requestTokenHeader == null || !requestTokenHeader.startsWith(HTTP_HEADER_TOKEN_PREFIX))
            return;
        final String token = requestTokenHeader.substring(HTTP_HEADER_TOKEN_PREFIX.length());

        // 3. Validate and decode JWT Token.
        final NulJwtToken jwtToken = new NulJwtToken(token);
        final NulJwtToken.NulJwtTokenProperties properties = jwtToken.validate();
        if (properties == null)
            return;

        // 4. Query user information and set context.
        final CachedAccount account = accountService.getAccountByUid(properties.uid());
        final CachedRole role = authorityService.getRoleById(account.getRole());
        role.validate();

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                account, null, role.toSpringAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        checkJwtToken(request, response);
        filterChain.doFilter(request, response);
    }
}
