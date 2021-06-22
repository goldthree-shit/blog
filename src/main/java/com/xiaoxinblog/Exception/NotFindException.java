package com.xiaoxinblog.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 这个注解springboot会去返回404页面
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFindException extends RuntimeException{
	public NotFindException() {
	}

	public NotFindException(String message) {
		super(message);
	}

	public NotFindException(String message, Throwable cause) {
		super(message, cause);
	}
}
