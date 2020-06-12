package com.holmes.springboot.multidatasource.datasource;

import org.apache.ibatis.annotations.Select;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 通过AOP更换数据源
 *
 * @author Administrator
 */
@Aspect
@Order(0)
@Component
public class DataSourceInterceptor {

    @Pointcut("execution(* com.holmes.springboot.multidatasource.mapper.*.*(..))")
    public void point() {
    }

    @Before(value = "point()")
    public void before(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations) {
            // 查询语句切到从库
            if(annotation instanceof Select) {
                DataSourceManager.set(DataSourcesEnum.SLAVE.getValue());
                return;
            }
        }
        DataSourceManager.set(DataSourcesEnum.MASTER.getValue());
    }

    @AfterReturning(value = "point()")
    public void afterReturning() {

    }
}
