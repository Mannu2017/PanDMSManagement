package com.mannu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import login.Login;

public class SpringContext {
	
	@Bean(name = "login")
    public Login createLogin() {
        return new Login();
    }
 
 @Bean
    public static PropertySourcesPlaceholderConfigurer setUp() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
