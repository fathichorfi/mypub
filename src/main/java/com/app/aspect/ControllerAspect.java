package com.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Order(0)
@Component
@Slf4j
public class ControllerAspect {

	@Pointcut(value = "execution(* com.app.controller.v1..*(..))")
	public void forAuthController() {
	}

	@Pointcut(value = "execution(* com.app.controller.FileUploadController..*(..))")
	public void forFileController() {
	}

	@Pointcut(value = "forAuthController() || forFileController()")
	public void forController() {
	}

	@Before(value = "forController()")
	public void displayArgs(JoinPoint joinpoint) {
		Object[] args = joinpoint.getArgs();
		for (Object arg : args) {
			log.info("arg={}", arg);
		}
	}
}
