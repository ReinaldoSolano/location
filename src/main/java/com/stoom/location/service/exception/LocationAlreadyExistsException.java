package com.stoom.location.service.exception;

import org.springframework.http.HttpStatus;

public class LocationAlreadyExistsException extends BusinessException {

	public LocationAlreadyExistsException() {
		super("locations-8", HttpStatus.BAD_REQUEST);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
