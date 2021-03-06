package com.w3dai.aias.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.csrf().disable()
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/index", "result/**").permitAll()
            .antMatchers("/author/**").hasRole("USER")
            .and()
            .formLogin()
            .loginPage("/login").failureUrl("/login-error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user").password(new BCryptPasswordEncoder().encode("password")).roles("USER");

    }
}

