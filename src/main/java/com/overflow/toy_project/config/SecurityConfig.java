package com.overflow.toy_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.overflow.toy_project.security.CustomAuthFailureHandler;
import com.overflow.toy_project.security.CustomInvalidSessionStrategy;
import com.overflow.toy_project.security.CustomLogoutSuccessHandler;
import com.overflow.toy_project.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
    
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .disable()
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/member/sign-in", "/member/sign-up")
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .formLogin(form -> form
                .loginPage("/member/sign-in")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/main/index", true)
                .failureHandler(new CustomAuthFailureHandler("/member/sign-in"))
                .permitAll()
            )
            .sessionManagement(session -> session
                .invalidSessionStrategy(new CustomInvalidSessionStrategy("/member/sign-in"))
            )
            .logout(logout -> logout
                .logoutUrl("/member/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler("/member/sign-in"))
                .permitAll()
            );

        return http.build();
    }
}