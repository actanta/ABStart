	/*
	  betahouse.us
	  CopyRight (c) 2012 - 2018
	 */
	package cc.abing.abstart.api.aop;

	import cc.abing.abstart.api.util.IpUtil;
	import cc.abing.abstart.suite.system.constant.SystemConstant;
	import cc.abing.abstart.suite.system.exception.BizException;
	import cc.abing.abstart.suite.system.response.CodeMsg;
	import cc.abing.abstart.suite.system.result.Result;
	import com.baomidou.mybatisplus.core.toolkit.StringUtils;
	import com.fasterxml.jackson.annotation.JsonInclude;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import org.aspectj.lang.ProceedingJoinPoint;
	import org.aspectj.lang.annotation.Around;
	import org.aspectj.lang.annotation.Aspect;
	import org.aspectj.lang.annotation.Pointcut;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.slf4j.MDC;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.core.annotation.Order;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.stereotype.Component;
	import org.springframework.web.context.request.RequestAttributes;
	import org.springframework.web.context.request.RequestContextHolder;

	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jakarta.servlet.http.HttpSession;
	import java.util.*;
	import java.util.stream.Collectors;

	/**
	 * @author ABing
	 * @since 2026-08-25
	 */
	@Order(-1)
	@Aspect
	@Component
	public class LogAspect {

		/**
		 * 日志模板
		 */
		private final static String REQUEST_TEMPLATE = "[{}|{}|{}|{}] userId=[{}] ip=[{}] method=[{}] consume=[{}ms] param=[{}] body={} result=[{}]";

		/**
		 * 异常日志模板
		 */
		private final static String EXCEPTION_TEMPLATE = "[{}|{}|{}|{}] userId=[{}] ip=[{}] method=[{}] param=[{}] body={} exception=[{}]";

		@Autowired
		private ObjectMapper objectMapper;

		@Pointcut("execution(* cc.abing.abstart.api.controller..*(..))")
		public void doLog() {
		}

		@Around("doLog()")
		public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
			String traceId = UUID.randomUUID().toString().replace("-","");
			MDC.put("traceId", traceId);
			try {
				// 获取日志实体
				Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
				// 获取方法名称
				String methodName = joinPoint.getSignature().getName();

				// 获取RequestAttributes
				RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
				// 从获取RequestAttributes中获取HttpServletRequest的信息
				HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes)
						.resolveReference(RequestAttributes.REFERENCE_REQUEST);
				if (request == null) {
					throw new BizException(CodeMsg.BAD_REQUEST);
				}

				// 获取请求信息
				String ip = parseIp(request);
				String httpMethod = request.getMethod();
				String uri = request.getRequestURI();
				String timestamp = Optional.ofNullable(request.getHeader("timestamp")).orElse(SystemConstant.HYPHEN);

				// 获取Session
				// 获取Session
				HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
				String userId = null;
				if(session != null){
					userId = (String) session.getAttribute("user_id");
				}
				// 获取请求参数 TODO 可能同时打印param和body，待完善
				String param = getParam(request);
				Object arg = Arrays.stream(joinPoint.getArgs()).filter(Objects::nonNull)
						.filter(i -> !(i instanceof HttpServletRequest)).filter(i -> !(i instanceof HttpServletResponse))
						.collect(Collectors.toList());
				ObjectMapper copyObjectMapper = objectMapper.copy().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				String body = copyObjectMapper.writeValueAsString(arg);body = body.replaceAll("\"password\":\"[^\"]+\"","\"password\":\"******\"");
				body = body.replaceAll("\"slat\":\"[^\"]+\"","\"slat\":\"******\"");

				long start = System.currentTimeMillis();Object result;
				try {
					result = joinPoint.proceed();
				} catch (Throwable e) {
					logger.error(EXCEPTION_TEMPLATE,
							SystemConstant.FAIL, httpMethod, uri, timestamp,
							userId, ip, methodName, param, body, e.getMessage());
					throw e; // 继续向上抛出，交给全局异常处理器处理业务返回
				}
				long end = System.currentTimeMillis();
				// TODO 0.ObjectMapper单例化 1.序列化返回结果，去除null值。 2.可配置是否打印返回结果。
				String resultJson = copyObjectMapper.writeValueAsString(result);
				String logResult = truncate(resultJson, 1024);
				// 脱敏
				logResult = logResult.replaceAll("\"password\":\"[^\"]+\"","\"password\":\"******\"");
				logResult = logResult.replaceAll("\"slat\":\"[^\"]+\"","\"slat\":\"******\"");
				logger.info(REQUEST_TEMPLATE, parseResult(result), httpMethod, uri, timestamp, userId, ip, methodName,
						end - start, param, body,
						logResult);
				return result;
			} finally {
				MDC.clear();
			}
		}

		/**
		 * 解析结果
		 * @param result
		 * @return
		 */
		private String parseResult(Object result) {
			Boolean status = null;
			if (result instanceof ResponseEntity) {
				status = HttpStatus.OK.equals(((ResponseEntity) result).getStatusCode());
			} else if (result instanceof Result) {
				status = CodeMsg.SYSTEM_OK.getCode().equals(((Result<?>) result).getCode());
			}

			if (status != null){
				return status ? SystemConstant.SUCCESS : SystemConstant.FAIL;
			}
			return SystemConstant.HYPHEN;
		}

		/**
		 * 解析ip
		 * @param request
		 * @return
		 */
		public static String parseIp(HttpServletRequest request) {
			String ip = IpUtil.getIpAddr(request);
			if (StringUtils.isBlank(ip)) {
				return SystemConstant.HYPHEN;
			}
			return ip;
		}

		private String getParam(HttpServletRequest request) {
			Enumeration<String> parameterNames = request.getParameterNames();
			Map<String, String> parameterMap = new HashMap<>(8);
			while (parameterNames.hasMoreElements()) {
				String parameter = parameterNames.nextElement();
				parameterMap.put(parameter, request.getParameter(parameter));
			}
			List<String> kvList = new ArrayList<>();
			for (Map.Entry<String,String> entry : parameterMap.entrySet()) {
				kvList.add(entry.getKey() + SystemConstant.EQUAL + entry.getValue());
			}
			return SystemConstant.LEFT_ANGLE_BRACKETS + String.join(SystemConstant.COMMA, kvList) + SystemConstant.RIGHT_ANGLE_BRACKETS;
		}

		/**
		 * 日志序列化截断，防止大对象撑爆日志
		 * @param str 原始字符串
		 * @param maxLen 最大输出长度
		 * @return 截断后字符串
		 */
		private String truncate(String str, int maxLen) {
			if (str == null) {
				return "";
			}
			if (str.length() <= maxLen) {
				return str;
			}
			return str.substring(0, maxLen) + "...[truncated,total=" + str.length() + "]";
		}

	}
