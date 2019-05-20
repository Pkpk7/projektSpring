package com.projekt.projekt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/Admin/*").hasRole("ADMIN")
                .antMatchers("/Admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/user/*").hasRole("USER")
                .antMatchers("/h2-console").hasRole("ADMIN")
                .antMatchers("/h2-console/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();


    }
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
