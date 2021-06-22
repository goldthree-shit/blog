package com.xiaoxinblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常解析，不是登录拦截，在错误页面右键查看网页源代码即可查看报错日志，会自动进入error页面
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	final private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
		logger.error("Request URL : {}, Exception : {}", request.getRequestURI(), e);
		if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("url", request.getRequestURL());
		modelAndView.addObject("exception", e);
		modelAndView.setViewName("error/error");
		return modelAndView;
	}

}
