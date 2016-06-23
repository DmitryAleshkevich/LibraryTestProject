package Utils;

import Rest.AppController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by aldm on 23.06.2016.
 */
@Component
@Aspect
public class MethodsProcessingLogger {
    private static final Logger logger = LogManager.getLogger(AppController.class);

    @Around("execution(* Rest.AppController.*(..))")
    public Object logData(ProceedingJoinPoint joinPoint) throws Throwable {

        Object retVal = joinPoint.proceed();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }

        logMessage.append(")");
        logMessage.append(", returns: " + retVal.toString());
        logger.info(logMessage.toString());
        return retVal;
    }
}
