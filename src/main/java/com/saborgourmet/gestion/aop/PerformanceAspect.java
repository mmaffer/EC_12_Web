package com.saborgourmet.gestion.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("@annotation(monitored)")
    public Object medirTiempoEjecucion(ProceedingJoinPoint joinPoint, Monitored monitored) throws Throwable {
        long inicio = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long fin = System.currentTimeMillis();
            log.debug("Monitoreo {} -> {} ms", monitored.value().isBlank() ? joinPoint.getSignature().toShortString() : monitored.value(), (fin - inicio));
        }
    }
}

