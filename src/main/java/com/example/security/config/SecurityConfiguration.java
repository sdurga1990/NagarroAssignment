package com.example.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = 
		          PasswordEncoderFactories.createDelegatingPasswordEncoder();
		 auth.inMemoryAuthentication()
		 .withUser("admin")
		 .password(encoder.encode("admin"))
		 .roles("ADMIN")
		 .and().withUser("user")
		 .password(encoder.encode("user"))
		 .roles("USER");
	}
	
	@Override
	public void configure(HttpSecurity httpSec) throws Exception{
		httpSec.csrf().disable();
		httpSec.headers().disable();
	    
		httpSec.authorizeRequests()
		.antMatchers("/statement").hasRole("ADMIN")
		.antMatchers("/last").hasAnyRole("USER","ADMIN").and().httpBasic();
	}
}
