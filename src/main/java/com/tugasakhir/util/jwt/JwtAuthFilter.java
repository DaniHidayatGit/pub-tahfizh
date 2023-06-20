package com.tugasakhir.util.jwt;

import com.tugasakhir.configuration.UserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String token = null;
        JwtTokenResponse jwtTokenDto = new JwtTokenResponse();

        if (header != null && header.startsWith("Bearer ")) {
            token = header.replace("Bearer ", "");

            try {
                jwtTokenDto = jwtTokenUtil.extractToken(token);
                username = jwtTokenDto.getUser_name();
            } catch (IllegalArgumentException e) {
                logger.error("Username tidak ditemukan");
            } catch (ExpiredJwtException e) {
                logger.warn("Token Expired");
            } catch (SignatureException e) {
                logger.error("Username atau password tidak sesuai");
            }
        } else {
            logger.warn("Header tidak di set / tidak menemukan kata Bearer ");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validationToken(token, userDetails, jwtTokenDto, request)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        request.setAttribute("User-Data", jwtTokenDto);
        filterChain.doFilter(request, response);
    }
}
