package com.app.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.app.error.exception.ConflictException;
import com.app.error.exception.NotFoundException;
import com.app.error.exception.ResourceAlreadyExistsException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Handle DataIntegrityViolationException, for a data violation constraints:
	 * unicity, ... specific and others
	 * 
	 * @param ex
	 * @param request
	 * @return ErrorResponse
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		Throwable cause = ex.getCause();
		if (cause instanceof ConstraintViolationException) {
			System.out.println("11111111111111111111111111");
			return ResponseEntity
					.status(HttpStatus.CONFLICT).body(
							buildResponse(ex, request, HttpStatus.CONFLICT,
									messageSource.getMessage("app.validation.resourceAlreadyExists",
											new Object[] { ex.getCause() }, LocaleContextHolder.getLocale()),
									ex.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(buildResponse(ex, request, HttpStatus.CONFLICT,
						messageSource.getMessage("app.validation.integrityViolation", new Object[] {},
								LocaleContextHolder.getLocale()),
						ex.getMessage()));
	}

	/**
	 * Handle ResourceAlreadyExistsException, it's a cusom exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<?> handleResourceAlreadyExists(ResourceAlreadyExistsException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(buildResponse(ex, request, HttpStatus.CONFLICT, ex.getMessage(), ex.getMessage()));
	}

	/**
	 * Handle NotFoundException, it's a cusom exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> handleNotFound(NotFoundException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildResponse(ex, request, HttpStatus.NOT_FOUND, ex.getMessage(), ex.getMessage()));
	}

	/**
	 * Handle MethodArgumentTypeMismatchException, when the argument type is not
	 * correct
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
		String message = messageSource.getMessage("app.validation.ArgumentTypeMismatch",
				new Object[] { ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName() },
				LocaleContextHolder.getLocale());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildResponse(ex, request, HttpStatus.BAD_REQUEST, message, ex.getMessage()));
	}

	/**
	 * Handle ConflictException, it's a custom exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<?> handleConflict(ConflictException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(buildResponse(ex, request, HttpStatus.CONFLICT, ex.getMessage(), ex.getMessage()));
	}

	/**
	 * Handle MissingServletRequestParameterException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildResponse(ex, request, HttpStatus.BAD_REQUEST,
						messageSource.getMessage("app.validation.paramIsMissing",
								new Object[] { ex.getParameterName() }, LocaleContextHolder.getLocale()),
						""));
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException, where the content type is not
	 * supported
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		ex.getSupportedMediaTypes().forEach(media -> builder.append(media).append(", "));
		String message = messageSource.getMessage("app.validation.paramUnsupportedMedia",
				new Object[] { ex.getContentType(), builder.substring(0, builder.length() - 2) },
				LocaleContextHolder.getLocale());
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(buildResponse(ex, request, HttpStatus.UNSUPPORTED_MEDIA_TYPE, message, message));
	}

	/**
	 * Handle MethodArgumentNotValidException, @Valid and @Validated.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.add("[" + error.getField() + "] " + error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildResponse(ex, request, HttpStatus.BAD_REQUEST, errors.toString(), ex.getMessage()));
	}

	/**
	 * Handle HttpMessageNotReadableException, where the json format is not correct
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildResponse(ex, request, HttpStatus.BAD_REQUEST,
						messageSource.getMessage("app.validation.messageNotReadable", new Object[] {},
								LocaleContextHolder.getLocale()),
						ex.getMessage()));
	}

	/**
	 * Handle HttpMessageNotWritableException, when the server can not format the
	 * response
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR,
						messageSource.getMessage("app.validation.messageNotWritable", new Object[] {},
								LocaleContextHolder.getLocale()),
						ex.getMessage()));
	}

	/**
	 * Handle NoResourceFoundException, the reosusrce is not fount
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildResponse(ex, request, HttpStatus.NOT_FOUND,
						messageSource.getMessage("app.validation.noResourceFound", new Object[] { path },
								LocaleContextHolder.getLocale()),
						ex.getMessage()));
	}

	/**
	 * Handle generic exception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex) {
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<------>>>>>>>>>>>>>>>>>>>>>><<>>>>>>");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildResponse(ex, null, HttpStatus.INTERNAL_SERVER_ERROR,
						messageSource.getMessage("app.validation.ServerError", new Object[] { ex.getCause() },
								LocaleContextHolder.getLocale()),
						ex.getMessage()));
	}

	/**
	 * Build a response
	 * 
	 * @param ex
	 * @param request
	 * @param status
	 * @param message
	 * @param detail
	 * @return
	 */
	private ErrorResponse buildResponse(Exception ex, WebRequest request, HttpStatus status, String message,
			String detail) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		ErrorResponse errorResponse = ErrorResponse.builder().message(message).dateTime(LocalDateTime.now()).path(path)
				.locale(request.getLocale().toString()).success(Boolean.FALSE.toString()).status(status.value() + "")
				.detail(detail).build();
		return errorResponse;
	}

}
