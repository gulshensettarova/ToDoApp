package com.company.toDoApp.security.jwt;

import com.company.toDoApp.security.UserPrincipal;

import io.jsonwebtoken.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;

import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;

@Component
public class JwtProvider {

    @Value("${authentication.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Value("${authentication.jwt.refresh-token-expiration-in-ms}")
    private Long JWT_REFRESH_EXPIRATION_IN_MS;

    @Value("${authentication.jwt.private-key}")
    private String jwtPrivateKeyStr;

    @Value("${authentication.jwt.public-key}")
    private String jwtPublicKeyStr;

    private static final String JWT_TOKEN_PREFIX = "Bearer";
    private static final String JWT_HEADER_STRING = "Authorization";
    private final PrivateKey jwtPrivateKey;
    private final PublicKey jwtPublicKey;
    private final Set<String> blacklistedTokens = new HashSet<>();

    public JwtProvider() {
        KeyFactory keyFactory = getKeyFactory();
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decoder.decode(jwtPrivateKeyStr));
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decoder.decode(jwtPublicKeyStr));

            this.jwtPrivateKey = keyFactory.generatePrivate(privateKeySpec);
            this.jwtPublicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Invalid key specification: " + e.getMessage(), e);
        }
    }

    private KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unknown key generation algorithm: " + e.getMessage(), e);
        }
    }

    public String generateToken(UserPrincipal authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return Jwts.builder()
                .setSubject(authentication.getUsername())
                .claim("userId", authentication.getId())
                .claim("roles", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(jwtPrivateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateRefreshToken(UserPrincipal authentication) {
        return Jwts.builder()
                .setSubject(authentication.getUsername())
                .claim("userId", authentication.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_EXPIRATION_IN_MS))
                .signWith(jwtPrivateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null) {
            return null;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtPublicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Long userId = claims.get("userId", Long.class);
            List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UserDetails userDetails = new UserPrincipal(Math.toIntExact(userId), username, authorities);
            return username != null ? new UsernamePasswordAuthenticationToken(userDetails, null, authorities) : null;
        }  catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token: " + e.getMessage(), e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("JWT parsing error: " + e.getMessage(), e);
        }
    }

    public boolean isTokenValid(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null || isTokenBlacklisted(token)) {
            return false;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtPublicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return !claims.getExpiration().before(new Date());
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String oldToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtPublicKey)
                    .build()
                    .parseClaimsJws(oldToken)
                    .getBody();

            String username = claims.getSubject();
            Long userId = claims.get("userId", Long.class);
            return generateToken(new UserPrincipal(Math.toIntExact(userId), username, null));
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Invalid JWT token for refresh: " + e.getMessage(), e);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(JWT_TOKEN_PREFIX.length() + 1);
        }
        return null;
    }

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
