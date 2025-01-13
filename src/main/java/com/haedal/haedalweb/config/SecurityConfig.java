package com.haedal.haedalweb.config;

import com.haedal.haedalweb.constants.LoginConstants;
import com.haedal.haedalweb.exception.FilterExceptionHandler;
import com.haedal.haedalweb.security.filter.CustomLogoutFilter;
import com.haedal.haedalweb.security.filter.JWTFilter;
import com.haedal.haedalweb.security.service.TokenAppService;
import com.haedal.haedalweb.security.filter.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final CustomLogoutFilter customLogoutFilter;
    private final TokenAppService tokenAppService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        LoginFilter loginFilter = new LoginFilter(authenticationManager, tokenAppService);
        return loginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, LoginFilter loginFilter) throws Exception {

        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://www.knu-haedal.com", "https://www.knu-haedal.com", "http://localhost:8080"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList(LoginConstants.ACCESS_TOKEN));

                        return configuration;
                    }
                })));


        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/admin/**").hasAnyRole("WEB_MASTER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/notices").hasAnyRole("WEB_MASTER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/notices/{postId}").hasAnyRole("WEB_MASTER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/notices/{postId}").hasAnyRole("WEB_MASTER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/activities/{activityId}/boards").hasAnyRole("WEB_MASTER", "ADMIN", "TEAM_LEADER")
                        .requestMatchers(HttpMethod.DELETE, "/activities/{activityId}/boards/{boardId}").hasAnyRole("WEB_MASTER", "ADMIN", "TEAM_LEADER")
                        .requestMatchers(HttpMethod.PUT, "/activities/{activityId}/boards/{boardId}/**").hasAnyRole("WEB_MASTER", "ADMIN", "TEAM_LEADER")

                        .requestMatchers(HttpMethod.POST, "/boards/{boardId}/posts").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/boards/{boardId}/posts/{postId}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/boards/{boardId}/posts/{postId}").authenticated()

                        .requestMatchers(HttpMethod.POST, "/posts/{postId}/comments").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/users/{userId}/profile/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}/profile/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users", "/users/{userId}").authenticated()

                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers("/login", "/join/**", "/reissue", "/error").permitAll()
                        .anyRequest().authenticated());

        //JWTFilter 등록
        http
                .addFilterBefore(jwtFilter, LoginFilter.class);

        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .addFilterBefore(new FilterExceptionHandler(), LogoutFilter.class);

        http
                .logout((auth) -> auth.disable());

        http
                .addFilterAfter(customLogoutFilter, LogoutFilter.class);


        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}