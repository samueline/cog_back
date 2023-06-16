package com.example.cognito.mercado.security;


import com.example.cognito.mercado.security.jwt.JwtEntryPoint;
import com.example.cognito.mercado.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {
    @Autowired
    public JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().configurationSource( request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("null","http://localhost:4200","http://localhost:8080"));
                    corsConfiguration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.addExposedHeader("Message");
                    corsConfiguration.setAllowedHeaders(List.of("Authorization","Cache-control", "Content-Type","Access-Control-Allow-Headers"));
                    return corsConfiguration;
                }).and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/sign-up",
                        "/auth/sign-in"

                )
                .permitAll()
                .antMatchers(HttpMethod.GET,"/api/subreddit")
                .permitAll()
                .antMatchers("/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui",
                        "/swagger-ui/index.html",
                        "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().disable();
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
