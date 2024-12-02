package com.nulstudio.dormitory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nulstudio.dormitory.common.NulResult;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Configuration for handling errors in authentication process.
 * @author nullsty
 * @since 2.0
 */
@Component
public class NulAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(NulAuthenticationEntryPoint.class);

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException authException) throws IOException {
        // Set the response to "403 Forbidden".
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Log error to console.
        log.error("Error", authException);

        // Return error message.
        response.getWriter().print(new ObjectMapper().writeValueAsString(
                NulResult.fail(NulExceptionConstants.INVALID_TOKEN)
        ));
    }
}
