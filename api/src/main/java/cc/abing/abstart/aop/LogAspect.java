/*
  betahouse.us
  CopyRight (c) 2012 - 2018
 */
package cc.abing.abstart.aop;


import cc.abing.abstart.support.system.constant.SystemConstant;
import cc.abing.abstart.support.system.exception.ABException;
import cc.abing.abstart.support.system.response.CodeMsg;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author userz
 */
@Order(-1)
@Aspect
@Component
public class LogAspect {

    /**
     * 日志模板
     */
    private final static String REQUEST_TEMPLATE = "[{}|{}|{}|{}] userId=[{}] ip=[{}] method=[{}] consume=[{}ms] param=[{}] body=[{}] result=[{}]";

    /**
     * 异常日志模板
     */
    private final static String EXCEPTION_TEMPLATE = "[{}|{}|{}|{}] userId=[{}] ip=[{}] method=[{}] param=[{}] body=[{}] exception=[{}]";




    @Pointcut("execution(* cc.abing.abstart.api..*(..))")
    public void doLog() {
    }


    @Around("doLog()")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取日志实体
        Logger logger = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        // 获取方法名称
        String methodName = proceedingJoinPoint.getSignature().getName();

        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if(request == null){
            throw new ABException(CodeMsg.BAD_REQUEST);
        }

        // 获取请求信息
        String ip = parseIp(request);
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();
        String timestamp = request.getHeader("timestamp");

        //获取Session
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        String userId = (String) session.getAttribute("user_id");
        //获取所有get请求参数
        String param = getParam(request);

        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        //TODO 0.ObjectMapper单例化  1.序列化返回结果，去除null值。 2.可配置是否打印返回结果。
        logger.info(REQUEST_TEMPLATE, parseResult(result), httpMethod, uri, timestamp, userId, ip, methodName,
                end - start, param, read(request),
                new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY).writeValueAsString(result));
        return result;
    }

    @AfterThrowing(pointcut="doLog()",throwing="e")
    public void doLog(JoinPoint joinPoint, Throwable e) throws Throwable {
        //获取日志实体
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();

        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if(request == null){
            throw new ABException(CodeMsg.BAD_REQUEST);
        }

        // 获取请求信息
        String ip = parseIp(request);
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();
        String timestamp = request.getHeader("timestamp");


        //获取Session
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        String userId = (String) session.getAttribute("user_id");
        //获取所有get请求参数
        String param = getParam(request);

        //TODO 0.ObjectMapper单例化  1.序列化返回结果，去除null值。 2.可配置是否打印返回结果。
        logger.info(EXCEPTION_TEMPLATE, SystemConstant.FAIL, httpMethod, uri, timestamp, userId, ip, methodName, param, read(request),e);
    }




    public static String read(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = request.getReader();
        StringWriter stringWriter = new StringWriter();
        long total = 0L;

        int read;
        for(char[] buf = new char[1024 * 10]; (read = bufferedReader.read(buf)) != -1; total += (long)read) {
            stringWriter.write(buf, 0, read);
        }
        return stringWriter.getBuffer().toString();
    }

    /**
     * 解析结果
     *
     * @param result
     * @return
     */
    private String parseResult(Object result) {
        if(result instanceof ResponseEntity) {
            return HttpStatus.OK.equals(((ResponseEntity)result).getStatusCode()) ? SystemConstant.SUCCESS : SystemConstant.FAIL;
        }else{
            return SystemConstant.HYPHEN;
        }
    }

    /**
     * 解析ip
     *
     * @param request
     * @return
     */
    public static String parseIp(HttpServletRequest request) {
        String ip = getIpAddr(request);
        if (StringUtils.isBlank(ip)) {
            return SystemConstant.HYPHEN;
        }
        return ip;
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !SystemConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            return ip.split(",")[0];
        }

        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !SystemConstant.UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


    private String getParam(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String,String> parameterMap = new HashMap<>(8);
        while (parameterNames.hasMoreElements()){
            String parameter = parameterNames.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        StringBuilder stringBuilder = new StringBuilder(SystemConstant.LEFT_ANGLE_BRACKETS);
        for (String key : parameterMap.keySet()) {
            stringBuilder.append(key)
                    .append(SystemConstant.EQUAL)
                    .append(parameterMap.get(key))
                    .append(SystemConstant.COMMA);
        }
        return stringBuilder.append(SystemConstant.RIGHT_ANGLE_BRACKETS).toString();
    }


}
