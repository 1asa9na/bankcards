package com.example.bankcards.config;

// import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bankcards.security.jwt.JwtTokenFilter;
import com.example.bankcards.service.props.PanSecurityProperties;
import com.example.bankcards.util.PanEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationConfig {
    
    // private final ApplicationContext applicationContext;
    private final JwtTokenFilter jwtTokenFilter;
    private final PanSecurityProperties panSecurityProperties;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // 12 rounds encryption
    }

    @Bean
    PanEncoder panEncoder() {
        return new PanEncoder(panSecurityProperties.getAesKey(), panSecurityProperties.getHmacKey());
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
            .csrf((csrf) -> csrf.disable())
            .cors(Customizer.withDefaults())
            .httpBasic((basic) -> basic.disable())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(((request, response, authException) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized");
            })).accessDeniedHandler(((request, response, accessDeniedException) -> {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Unauthorized");
            })))
            .authorizeHttpRequests((request) -> request.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated())
            .anonymous((anonymous) -> anonymous.disable())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
            return httpSecurity.build();
    }
}
