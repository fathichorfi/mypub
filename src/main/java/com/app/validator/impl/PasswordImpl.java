package com.app.validator.impl;

import com.app.validator.Password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordImpl implements ConstraintValidator<Password, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.length() < 6)
			return false;
		return true;
	}

}
