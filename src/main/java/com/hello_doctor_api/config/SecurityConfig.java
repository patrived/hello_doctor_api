package com.hello_doctor_api.config;

import com.hello_doctor_api.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider provider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        System.out.println("PROVIDER");
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailService);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain configureAuth(HttpSecurity http) throws Exception {
        System.out.println("CONFIGUREAUTH");

        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers("/api/v1/Patient/registerPatient").permitAll() // Permit all requests under "/demo/**"
                .requestMatchers("/api/v1/Patient/login").permitAll() // Permit all requests under "/demo/**"
                .requestMatchers("/api/v1/Patient/verify").permitAll() // Permit all requests under "/demo/**"
                .requestMatchers("/api/v1/Admin/specialization").permitAll() // Permit all requests under "/demo/**"
                .requestMatchers("/api/v1/Patient/byemail/**").permitAll() // Permit all requests under "/demo/**"
//              .requestMatchers("/demo/set").permitAll() // Example of another matcher (commented out)
                .anyRequest().authenticated() // Authenticate all other requests
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) // Handle unauthorized access
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Add custom filter

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173", "http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}
