package com.holmes.springboot.atomikos.datasource;

import com.alibaba.druid.util.StringUtils;
import com.holmes.springboot.atomikos.entity.BasePo;
import org.apache.ibatis.annotations.Param;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 通过AOP更换数据源标识
 *
 * @author Administrator
 */
@Aspect
@Order(0)
@Component
public class DataSourceInterceptor {

    @Pointcut("execution(* com.holmes.springboot.atomikos.mapper.*.*(..))")
    public void point() {
    }

    @Before(value = "point()")
    public void before(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Annotation[][] annotationTd = method.getParameterAnnotations();

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {

                if (args[i] instanceof BasePo) {
                    // 通过对象字段值判断数据源
                    BasePo basePo = (BasePo) args[i];
                    if (!StringUtils.isEmpty(basePo.getDataSourceFlag())) {
                        DataSourceManager.set(basePo.getDataSourceFlag());
                        return;
                    }
                } else if (args[i] instanceof Map) {
                    // 通过map值判断数据源
                    HashMap map = (HashMap) args[i];
                    if (map.get("dataSourceFlag") != null && !StringUtils.isEmpty(map.get("dataSourceFlag").toString())) {
                        DataSourceManager.set(map.get("dataSourceFlag").toString());
                        return;
                    }
                } else if (args[i] instanceof String) {
                    // 通过@Param注解判断数据源
                    String arg = (String) args[i];
                    Annotation[] annotations = annotationTd[i];
                    if (annotations.length != 0) {
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof Param) {
                                String param = ((Param) annotation).value();
                                if ("dataSourceFlag".equals(param) && !StringUtils.isEmpty(arg)) {
                                    DataSourceManager.set(arg);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @AfterReturning(value = "point()")
    public void afterReturning() {

    }
}
