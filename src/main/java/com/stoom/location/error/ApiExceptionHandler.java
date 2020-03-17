package com.stoom.location.error;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.stoom.location.error.ErrorResponse.ApiError;
import com.stoom.location.service.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

	private final MessageSource apiErrorMessageSource;

	private static final String NO_MESSAGE_AVAILABLE = "No message available";

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException exception,
			Locale locale) {
		Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
		List<ApiError> apiErrors = errors.map(ObjectError::getDefaultMessage).map(code -> toApiError(code, locale))
				.collect(toList());

		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception, Locale locale) {
		final String errorCode = "generic-2";
		final HttpStatus status = HttpStatus.BAD_REQUEST;

		final ErrorResponse errorResponse = ErrorResponse.of(status,
				toApiError(errorCode, locale));

		return ResponseEntity.badRequest().body(errorResponse);
	}

	public ApiError toApiError(String code, Locale locale, Object... args) {
		String message;
		try {
			message = apiErrorMessageSource.getMessage(code, args, locale);
		} catch (NoSuchMessageException e) {
			LOGGER.error("Could not find any message for {} code under {} locale", code, locale);
			message = NO_MESSAGE_AVAILABLE;
		}
		return new ApiError(code, message);

	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale) {
		final String errorCode = "generic-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;

		final ErrorResponse errorResponse = ErrorResponse.of(status,
				toApiError(errorCode, locale, exception.getValue()));

		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	
	
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale) {
		final String errorCode = exception.getCode();
		final HttpStatus status = exception.getStatus();

		final ErrorResponse errorResponse = ErrorResponse.of(status,
				toApiError(errorCode, locale));

		return ResponseEntity.badRequest().body(errorResponse);
	}

}
