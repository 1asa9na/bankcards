package com.example.bankcards.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import com.example.bankcards.exception.ResourceNotFoundException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest)servletRequest).getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        if(bearerToken != null && tokenProvider.validateToken(bearerToken)) {
            try {
                String username = tokenProvider.getUsername(bearerToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());;
                if(authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ResourceNotFoundException e) {}
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
