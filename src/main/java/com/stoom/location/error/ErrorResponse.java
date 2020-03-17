package com.stoom.location.error;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor(access = PRIVATE)
public class ErrorResponse {
	private final int statusCode;
	private final List<ApiError> errors;

	static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
		return new ErrorResponse(status.value(), errors);
	}

	static ErrorResponse of(HttpStatus status, ApiError error) {
		return of(status, Collections.singletonList(error));
	}

	@Data
	@JsonAutoDetect(fieldVisibility = ANY)
	@RequiredArgsConstructor
	static class ApiError {

		private final String code;
		private final String message;

	}

}
