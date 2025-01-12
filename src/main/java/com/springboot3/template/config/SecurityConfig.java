package com.springboot3.template.config;

import com.springboot3.template.filter.ApiLoggingFilter;
import com.springboot3.template.filter.JwtAuthorizationFilter;
import com.springboot3.template.security.AuthEntryPoint;
import com.springboot3.template.security.AuthFailureHandler;
import com.springboot3.template.security.AuthSuccessHandler;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.MessageUtil;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String SESSION_ID = "JSESSIONID";
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ApiLoggingFilter apiLoggingFilter;
    private final AuthEntryPoint authEntryPoint;

    private final Environment env;

    private String projectUrl;

    private String authLoginUrl;

    private String authLogoutUrl;

    private String authEntryPointUri;

    private String authSuccessPointUri;


    @PostConstruct
    private void init() {
        projectUrl = env.getProperty("project.path");
        authEntryPointUri =  "/" + env.getProperty("template.entry-point.auth");
        authSuccessPointUri = authEntryPointUri.concat("/dashboard");
        authLoginUrl = authEntryPointUri + "/login";
        authLogoutUrl = authEntryPointUri + "/logout";
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(authLoginUrl).permitAll()
                        .requestMatchers("/api/**",authEntryPointUri + "/**").authenticated()
                        .anyRequest().permitAll()
                ).formLogin(formLogin -> formLogin
                        .loginPage(authLoginUrl)
                        .loginProcessingUrl(authLoginUrl)
                        .successHandler(authSuccessHandler(projectUrl + authSuccessPointUri))
                        .failureHandler(authFailureHandler(projectUrl +  authLoginUrl))
                )

                .logout(logout -> logout
                        .logoutUrl(authLogoutUrl)
                        .logoutSuccessUrl(projectUrl + authLoginUrl + "?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies(SESSION_ID)
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .invalidSessionUrl(projectUrl + authLogoutUrl)
                );


        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiLoggingFilter,JwtAuthorizationFilter.class)  ;
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authEntryPoint)
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    private AuthFailureHandler authFailureHandler(String defaultFailureUrl) {
        return new AuthFailureHandler(defaultFailureUrl);
    }

    private AuthSuccessHandler authSuccessHandler(String defaultSuccessUrl) {
        return new AuthSuccessHandler(defaultSuccessUrl);
    }

}
