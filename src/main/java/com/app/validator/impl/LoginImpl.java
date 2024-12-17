package com.app.validator.impl;

import com.app.validator.Login;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LoginImpl implements ConstraintValidator<Login, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.equals("admin"))
			return false;
		return true;
	}

}
