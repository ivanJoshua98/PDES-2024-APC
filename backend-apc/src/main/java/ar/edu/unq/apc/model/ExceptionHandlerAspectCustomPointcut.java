package ar.edu.unq.apc.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class ExceptionHandlerAspectCustomPointcut {

	private Logger log = LoggerFactory.getLogger(ExceptionHandlerAspectCustomPointcut.class);
	
	@Pointcut("execution(* ar.edu.unq.apc.webService.*.*(..))")
	public void methodsStarterServicePointcut() {
	}
	
	@Around("methodsStarterServicePointcut()")
	public Object exceptionHandler(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Exception Handler Aspect Pointcut - AROUND START");
		try {
			return joinPoint.proceed();
		} 
		catch (HttpException e) {
			log.error("/////// ExceptionHandlerAspectCustomPointcut - AROUND POINTCUT - Exception caught: AppException ///////", e);
			return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
		}
		catch (BadCredentialsException e){
			log.error("/////// ExceptionHandlerAspectCustomPointcut - AROUND POINTCUT - Exception caught: BadCredentialsException ///////", e);
			return new ResponseEntity<>("The password is incorrect", HttpStatusCode.valueOf(400));
		}
		catch (Exception e){
			log.error("/////// ExceptionHandlerAspectCustomPointcut - AROUND POINTCUT - Exception caught: Non Api Exception ///////", e);
			return new ResponseEntity<>("Internal server error", HttpStatusCode.valueOf(500));
		}
	}
}
