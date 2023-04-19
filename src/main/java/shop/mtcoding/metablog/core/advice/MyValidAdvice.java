package shop.mtcoding.metablog.core.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shop.mtcoding.metablog.core.exception.csr.ExceptionApi400;
import shop.mtcoding.metablog.core.exception.ssr.Exception400;

@Aspect
@Component
public class MyValidAdvice {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {}

    @Pointcut("execution(* shop.mtcoding.metablog.controller.api.*Controller.*(..))")
    public void api(){}

    @Pointcut("execution(* shop.mtcoding.metablog.controller.*Controller.*(..))")
    public void view(){}

    @Before("postMapping() && view()")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                if (errors.hasErrors()) {
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getField(),
                            errors.getFieldErrors().get(0).getDefaultMessage()
                    );
                }
            }
        }
    }

    @Before("postMapping() && api()")
    public void validationApiAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                if (errors.hasErrors()) {
                    throw new ExceptionApi400(
                            errors.getFieldErrors().get(0).getField(),
                            errors.getFieldErrors().get(0).getDefaultMessage()
                    );
                }
            }
        }
    }
}