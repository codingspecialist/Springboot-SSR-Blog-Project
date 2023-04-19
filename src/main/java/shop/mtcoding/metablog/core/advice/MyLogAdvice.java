package shop.mtcoding.metablog.core.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class MyLogAdvice {
    @Pointcut("@annotation(shop.mtcoding.metablog.core.annotation.MyLog)")
    public void myLog(){}

    // After : 정상, 실패 모두 로그 남김
    // AfterReturning : 정상일때만 로그 남김
    @AfterReturning("myLog()")
    public void logAdvice(JoinPoint jp) throws HttpMessageNotReadableException {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        log.debug("디버그 : "+method.getName()+" 성공");
    }
}
