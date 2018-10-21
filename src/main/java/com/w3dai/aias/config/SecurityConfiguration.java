package com.w3dai.aias.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("ADMIN");
    }
}

/**
 *     @Override
 *     protected void configure(HttpSecurity http) throws Exception{
 *         http.httpBasic()
 *                 .and()
 *                 .csrf().disable()
 *                 .authorizeRequests()
 *                 .antMatchers("/").permitAll()
 *                 .anyRequest().authenticated();
 *     }
 *                 .antMatchers(HttpMethod.POST,"/search/**").permitAll()
 *                 .antMatchers(HttpMethod.GET, "/searchAuthor/**").permitAll()
 *                 .antMatchers(HttpMethod.POST, "/searchAuthor/**").hasRole("ADMIN")
 */
