package com.darknightcoder.ems.config;

import com.darknightcoder.ems.security.CustomUserDetailsService;
import com.darknightcoder.ems.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private CustomUserDetailsService userDetailsService;

    // Spring basically get the web request have a filter chain
    // like basic auth or Jwt authenticate the request
    // call the Authentication manager
    // which call the User Details
    // now user get authenticated
    // stored in Spring Context available for entire application
    // authorization comes in and see if the authenticated user is authorized for the resource

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Now Spring do 2 things
        // 1. Authentication or checking the identity
        // 2. Authorization or checking what can be accessed
        httpSecurity
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated())//this tell wt to authorize
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                //.httpBasic(Customizer.withDefaults());// here we tell wt type of authentication to be done
        return httpSecurity.build();
    }

    // will provide the User Details Service  as Authentication manager is called by Spring
//    @Bean
//    public InMemoryUserDetailsManager userDetails(){
//        UserDetails userDetails = User.builder()
//                .username("Gaurav")
//                .password(passwordEncoder().encode("password"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
