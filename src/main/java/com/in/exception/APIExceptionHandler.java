package com.in.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@RestController
public class APIExceptionHandler {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

	@ExceptionHandler(value = { ValidationException.class })
	public final ResponseEntity<ErrorDetails> handleValidationException(ValidationException ex, WebRequest request) {
		logger.error("exception Occurred,", ex);
		ErrorDetails errorDetails = new ErrorDetails(ExceptionUtility.getCode(ex.getMessageCode()),
				ExceptionUtility.getCode(ex.getMessageDesc()), ExceptionUtility.getCode(ex.getSuccess()));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.OK);
	}

	@ExceptionHandler(value = { Exception.class })
	ResponseEntity<ErrorDetails> handleBadRequest(Exception ex, WebRequest request) {
		logger.error("exception Occurred,", ex);
		ErrorDetails errorDetails = new ErrorDetails(ExceptionUtility.getCode(ex.getMessage()), ex.getMessage(),
				ExceptionUtility.getCode("successBooleanFalse"));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ErrorDetails> handleNotFoundRequest(final NoHandlerFoundException ex) {
		logger.error("exception Occurred,", ex);
		ErrorDetails errorDetails = new ErrorDetails(ExceptionUtility.getCode("dataNotFoundCode"), ex.getMessage(),
				ExceptionUtility.getCode("successBooleanFalse"));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ServletException.class)
	public ResponseEntity<ErrorDetails> handleUnauthorizedRequest(ServletException ex, HttpServletResponse response) {
		ErrorDetails errorDetails = new ErrorDetails(ExceptionUtility.getCode("Unauthorized Request"), ex.getMessage(),
				ExceptionUtility.getCode("successBooleanFalse"));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

}
