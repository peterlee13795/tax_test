package com.tax.test.core.aop;

import com.tax.test.core.exception.LogicException;
import com.tax.test.domain.BaseResponse;
import com.tax.test.type.StatusType;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(LogicException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse<Void> handleLogicException(LogicException exception) {
		log.warn("handlerException: handleLogicException {}", exception.getMessage());
		return new BaseResponse<>(exception.getStatusType(), exception.getViewMessage());
	}

	@ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse<Void> handleAuthException(Exception exception) {
		log.warn("handlerException: handleAuthException {}", exception.getMessage());
		return new BaseResponse<>(StatusType.MEMBER_NOT_LOGIN_ABLE);
	}

	@ExceptionHandler(FeignException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseResponse<Void> handleExternalApiException(FeignException exception) {
		log.warn("handlerException: handleExternalApiException {}", exception.getMessage());
		return new BaseResponse<>(StatusType.MEMBER_NOT_LOGIN_ABLE);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BaseResponse<Void> handleArgumentException(MethodArgumentNotValidException exception) {
		log.warn("handlerException: handleArgumentException {}", exception.getMessage());
		Object[] messages = Optional.ofNullable(exception.getDetailMessageArguments()).orElseGet(() -> new Object[]{});
		String message = Arrays.stream(messages).map(String::valueOf).collect(Collectors.joining(","));
		return new BaseResponse<>(StatusType.UNKNOWN, message);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseResponse<Void> handleException(Exception exception) {
		log.error("handlerException: handleException {}", exception);
		return new BaseResponse<>(StatusType.UNKNOWN);
	}

}
