package com.eco.pub.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.eco.pub.aspect.annotation.DataSource;
import com.eco.pub.multidatasource.MultipleDataSource;


@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

    @Around(value = "com.eco.pub.aspect.pointcut.SystemArchitecture.anyPublicMethod() "
            + "&& @annotation(dataSource)")
    public Object setDataSource(ProceedingJoinPoint jp, DataSource dataSource) throws Throwable {

        String ds = dataSource.value();
        if (ds != null && !ds.equals("")) {
            MultipleDataSource.setDataSourceKey(ds);
        }
        Object o = null;
        try {
            o = jp.proceed();
        } catch (Throwable t) {
            throw t;
        } finally {
            MultipleDataSource.setDataSourceKey("");
        }

        return o;

    }

}
