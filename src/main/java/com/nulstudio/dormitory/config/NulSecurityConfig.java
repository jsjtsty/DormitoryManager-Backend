package com.nulstudio.dormitory.config;

import com.nulstudio.dormitory.domain.cache.CachedAccount;
import com.nulstudio.dormitory.service.AccountService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration for this application, using Spring Security.
 * Method security is enabled.
 * @author nullsty
 * @since 2.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class NulSecurityConfig {

    /**
     * Authentication exception handler for security module.
     */
    @Resource
    private NulAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccountService accountService;

    /**
     * Inject a new {@code NulJwtTokenFilter} for JWT authentication.
     * @return an instance of {@code NulJwtTokenFilter}
     */
    @Bean
    protected NulJwtTokenFilter jwtTokenFilter() {
        return new NulJwtTokenFilter();
    }

    /**
     * Configure Spring Security filter chain.
     * @param http http security configuration
     * @return an instance of {@code SecurityFilterChain}
     * @throws Exception when internal errors in Spring Security occurred
     */
    @Bean
    protected SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/account").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Provide authority list for Spring Security.
     * @param auth authentication manager to provide data
     * @throws Exception when internal errors in Spring Security occurred
     */
    protected void configureGlobal(@NotNull AuthenticationManagerBuilder auth) throws Exception {
        // Set user detail service for method authentication.
        auth.userDetailsService(accountService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Get CORS Configuration.
     * @return CORS Configuration
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * Get user detail of current user.
     * @return user detail of current user
     */
    @NotNull
    public static CachedAccount getContextAccount() {
        return (CachedAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
