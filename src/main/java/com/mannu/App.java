package com.mannu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import login.Login;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
        Login login = (Login) context.getBean("login");
        login.setArgs(args);
	}

}
