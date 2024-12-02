package com.nulstudio.dormitory.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.RegisteredClaims;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nulstudio.dormitory.exception.NulJwtSecretException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Objects;

/**
 * JWT Token helper for authentication.
 * @param token JWT Token string, must be non-null
 * @author nullsty
 * @since 2.0
 */
public record NulJwtToken(@NonNull String token) implements Comparable<NulJwtToken> {
    /**
     * JWT Secret.
     */
    @Nullable
    private static String JWT_SECRET = null;

    /**
     * Key for JWT Secret in environment variables.
     */
    private static final String JWT_SECRET_ENV_KEY = "NUL_DORM_SECRET";

    /**
     * Valid time of token (7d).
     */
    private static final long JWT_VALID_TIME = 7 * 24 * 3600 * 1000;

    /**
     * Properties for JWT Token.
     * @param uid ID of the user
     */
    public record NulJwtTokenProperties(
            int uid
    ) {

    }

    /**
     * Validate and decode token.
     * @return properties if the token is valid, null otherwise
     */
    @Nullable
    public NulJwtTokenProperties validate() {
        final Algorithm algorithm = Algorithm.HMAC256(getJwtSecret());
        final JWTVerifier verifier = JWT.require(algorithm).build();
        final DecodedJWT decodedJWT;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }
        final int uid = decodedJWT.getClaim(RegisteredClaims.SUBJECT).asInt();
        return new NulJwtTokenProperties(uid);
    }

    /**
     * Generate a new {@code NulJwtToken} with properties.
     * @param properties properties for the token
     * @return generated JWT Token
     */
    @NonNull
    public static NulJwtToken generate(@NonNull NulJwtTokenProperties properties) {
        final Algorithm algorithm = Algorithm.HMAC256(getJwtSecret());
        final Date expireTime = new Date(System.currentTimeMillis() + JWT_VALID_TIME);
        final String token = JWT.create().withExpiresAt(expireTime)
                .withClaim(RegisteredClaims.SUBJECT, properties.uid)
                .sign(algorithm);
        return new NulJwtToken(token);
    }

    /**
     * Get JWT Secret and save it in memory.
     * @return JWT Secret string
     * @throws NulJwtSecretException when failed to read secret in environment variables
     */
    @NonNull
    private static String getJwtSecret() throws NulJwtSecretException {
        if (JWT_SECRET == null) {
            JWT_SECRET = System.getenv(JWT_SECRET_ENV_KEY);
            if (JWT_SECRET == null) {
                throw new NulJwtSecretException();
            }
        }
        return JWT_SECRET;
    }

    @Override
    public int compareTo(@NonNull NulJwtToken o) {
        return token.compareTo(o.token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NulJwtToken that = (NulJwtToken) o;
        return Objects.equals(token, that.token);
    }
}
