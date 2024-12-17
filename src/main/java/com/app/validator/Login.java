package com.app.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.app.validator.impl.LoginImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {LoginImpl.class})
public @interface Login {

	/**
	 * @return the error message template
	 */
	String message() default "{app.validation.constraints.login.message}";

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default {};

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default {};
}
