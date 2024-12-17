package com.app.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Order(1)
@Component
@Slf4j
public class LogAspect {

	@Around(value = "execution(* com.app.service..*(..))")
	public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder("KPI: ");
		sb.append("[").append(joinPoint.getKind()).append("]")
		.append("  For: [").append(joinPoint.getSignature()).append("]")
		.append("  Args: [").append(StringUtils.join(joinPoint.getArgs(), ",")).append("]")
		.append("  Took: [");
		Object returnValue = joinPoint.proceed();
		sb.append(System.currentTimeMillis()-startTime).append(" ms.]");
		log.info(sb.toString());
		return returnValue;
	}

}
