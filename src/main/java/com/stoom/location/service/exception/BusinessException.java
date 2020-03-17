package com.stoom.location.service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private final String code;
	private final HttpStatus status;

}
