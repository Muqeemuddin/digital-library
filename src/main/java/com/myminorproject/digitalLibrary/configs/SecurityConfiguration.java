package com.myminorproject.digitalLibrary.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user1 = User.withDefaultPasswordEncoder()
                .username("Abdul")
                .password("Abd123@@")
                .roles("USER")
                .build();
        UserDetails admin1 = User.withDefaultPasswordEncoder()
                .username("Muqeem")
                .password("Muq123@@")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, admin1);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/getAdmins").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults());

        return http.build();

    }


}
