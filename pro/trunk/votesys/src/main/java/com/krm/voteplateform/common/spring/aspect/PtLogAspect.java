package com.krm.voteplateform.common.spring.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.krm.voteplateform.common.spring.annotation.Log;
import com.krm.voteplateform.common.utils.IPUtils;
import com.krm.voteplateform.web.log.model.PtLog;
import com.krm.voteplateform.web.log.service.PtLogService;
import com.krm.voteplateform.web.util.SysUserUtils;

@Aspect
@Component
@Order(0)
public class PtLogAspect {

	@Resource
	private PtLogService<PtLog> ptLogService;

	// 本地异常日志记录对象
	private final static Logger LOGGER = LoggerFactory.getLogger(PtLogAspect.class);

	@Pointcut("@annotation(com.krm.voteplateform.common.spring.annotation.Log)")
	public void accessAspect() {
	}

	@Pointcut("execution(* com.krm.voteplateform.web..*Service.*(..))")
	public void throwingAspect() {
	}

	@AfterReturning(value = "accessAspect()", returning = "rtv")
	public void doAfterReturning(JoinPoint joinPoint, Object rtv) {
		saveLog(joinPoint, null);
	}

	@AfterThrowing(value = "throwingAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		saveLog(joinPoint, e);
	}

	protected void saveLog(JoinPoint joinPoint, Throwable e) {
		try {
			HttpServletRequest request = SysUserUtils.getCurRequest();
			PtLog log = new PtLog();
			// 判断参数
			if (joinPoint.getArgs() != null) {
				StringBuffer rs = new StringBuffer();
				for (int i = 0, len = joinPoint.getArgs().length; i < len; i++) {
					Object info = joinPoint.getArgs()[i];
					if (info != null) {
						String paramType = info.getClass().getSimpleName();
						rs.append("[参数" + (i + 1) + "，类型:" + paramType + "，值:" + info.toString() + "]");
					} else {
						rs.append("[参数" + (i + 1) + "，值:null]");
					}
				}
				log.setParams(rs.toString());
			}
			log.setRemoteAddr(IPUtils.getClientAddress(request));
			log.setRequestUri(request.getRequestURI());
			log.setMethod(request.getMethod());
			log.setUserAgent(request.getHeader("user-agent"));
			log.setException(e == null ? null : e.toString());
			log.setType(e == null ? PtLog.TYPE_ACCESS : PtLog.TYPE_EXCEPTION);
			Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();
			Log sclog = m.getAnnotation(Log.class);
			if (sclog != null)
				log.setDescription(sclog.description());
			// log保存到数据库
//			ptLogService.saveLog(log);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

}
