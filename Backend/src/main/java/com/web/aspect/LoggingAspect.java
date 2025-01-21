package com.web.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;




@Slf4j
@Component
@Aspect
public class LoggingAspect {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@AfterThrowing(pointcut = "execution(* com.web.controller.*.*(..))", throwing = "exception")
	public void logServiceException(Exception exception) throws Exception {
//		logger.error(exception.getMessage(), exception);
		log.error(exception.getMessage(), exception);
	}
	
	@Before("execution(* com.web.controller.*.*(..))")
	public void logServiceBefore(JoinPoint joinPoint) throws Exception {


		log.info("Method "+ joinPoint.getSignature().getName()+" executed");
	}


}