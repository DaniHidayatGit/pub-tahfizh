package com.tugasakhir.util.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugasakhir.configuration.DBQueryHandler;
import com.tugasakhir.util.Helpers;
import com.tugasakhir.util.SQLOperation;
import com.tugasakhir.util.SQLWhereObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil extends DBQueryHandler {
    //    Set expirate 1 tahun
    private static final long JWT_TOKEN_VALIDITY = 24 * 365;

    @Value("${jwt.secret}")
    private String secret;

    private final ObjectMapper objectMapper;

    JwtTokenUtil(DataSource dataSource, ObjectMapper objectMapper) {
        this.setDataSource(dataSource);
        this.objectMapper = objectMapper;
    }

    public String doGenerateToken(Map<String, Object> claims, JwtTokenResponse jwtTokenDto) {
        try {
            return Jwts.builder().setClaims(claims)
                    .setSubject(objectMapper.writeValueAsString(jwtTokenDto))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 3600000))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String generateToken(JwtTokenResponse jwtTokenDto) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, jwtTokenDto);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) throws JsonProcessingException {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isTokenInvalidate(String token, String userName) {
        Connection con = Connect();
        try {
            List<LinkedHashMap<String, Object>> listTokenValidate = Select(con, "master_token_active", new String[]{"token_jwt"}, new ArrayList<SQLWhereObject>() {{
                add(new SQLWhereObject("user_name", SQLOperation.EQUAL, userName));
            }}, new com.tugasakhir.util.ObjectMapper());
            List<String> tokenValidates = new ArrayList<>();
            listTokenValidate.forEach((tokenValidate) -> {
                tokenValidates.add(Helpers.getString(tokenValidate.get("token_jwt")));
            });
            boolean isTokenInvalidate = true;
            if (listTokenValidate.size() > 0) {
                if (tokenValidates.contains(token)) {
                    isTokenInvalidate = false;
                }
            }
            Commit(con);
            return isTokenInvalidate;
        } catch (Exception e) {
            e.printStackTrace();
            Rollback(con);
            return true;
        } finally {
            Close(con);
        }
    }

    private Boolean isAccessNotAllowed(JwtTokenResponse jwtResponse, HttpServletRequest request) {
        Connection con = Connect();
        try {
            List<LinkedHashMap<String, Object>> listPathByRole = Select(con, "master_mapping_role_path", new String[]{"*"}, new ArrayList<SQLWhereObject>() {{
                add(new SQLWhereObject("role_id", SQLOperation.EQUAL, jwtResponse.getUser_role()));
            }}, new com.tugasakhir.util.ObjectMapper());
            List<String> paths = listPathByRole.stream()
                    .map(path -> Helpers.getString(path.get("request_uri")))
                    .collect(Collectors.toList());
            boolean isAccessNotAllowed = true;
            if (paths.size() > 0) {
                if (paths.contains(request.getRequestURI())) {
                    isAccessNotAllowed = false;
                }
            }
            Commit(con);
            return isAccessNotAllowed;
        } catch (Exception e) {
            e.printStackTrace();
            Rollback(con);
            return true;
        } finally {
            Close(con);
        }

    }

    public JwtTokenResponse extractToken(String token) {
        Connection con = Connect();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (new Date().after(claims.getBody().getExpiration())) {
                return null;
            }
            JwtTokenResponse jwtTokenDto = objectMapper.readValue(claims.getBody().getSubject(), JwtTokenResponse.class);
            Commit(con);
            return jwtTokenDto;
        } catch (Exception e) {
            e.printStackTrace();
            Rollback(con);
            return null;
        } finally {
            Close(con);
        }
    }

    public Boolean validationToken(String token, UserDetails userDetails, JwtTokenResponse jwtResponse, HttpServletRequest request) throws JsonProcessingException {
        String userName = "";

        JwtTokenResponse jwtTokenDto = extractToken(token);
        userName = jwtTokenDto.getUser_name();

        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenInvalidate(token, userName) && !isAccessNotAllowed(jwtResponse, request));
    }
}
