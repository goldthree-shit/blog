package com.xiaoxinblog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception {
		if(request.getSession().getAttribute("user") == null) {
//			response.sendRedirect("/admin");
//			return false;

			request.setAttribute("message","没有权限,请先登录");
//			request.getSession().setAttribute("message","没有权限,请先登录");
//			System.out.println("===>filter");
			request.getRequestDispatcher("/admin").forward(request, response);
			return false;

		}
		return true;
	}
}
