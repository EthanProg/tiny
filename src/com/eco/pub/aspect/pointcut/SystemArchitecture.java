package com.eco.pub.aspect.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class SystemArchitecture {
	
	@Pointcut("execution(* *(..))")
	public void anyMethod(){
		
	}
	
	@Pointcut("execution(public * *(..))")
	public void anyPublicMethod(){
		
	}
	
	@Pointcut("execution(* com.eco.*.service..*(..))")
	public void globalServiceLayer(){
		
	}
	
	@Pointcut("execution(* com.eco.*.controller..*(..))")
	public void globalControllerLayer(){
		
	}
}
