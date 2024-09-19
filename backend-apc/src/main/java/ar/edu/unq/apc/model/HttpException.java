package ar.edu.unq.apc.model;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final HttpStatus httpStatus;

	public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
	
    public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
