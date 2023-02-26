package com.campers.camfp.config.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public abstract class AbstractValidator<T> implements Validator{
	
	public boolean supports(Class<?> clazz) { //이게 오타인가
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object target, Errors errors) { 
		try {
			doValidate((T) target, errors);//유효성 검증로직
		} catch(IllegalStateException e) {
			log.error("중복 검증 에러 ",e);
			throw e;
		}
	}

	protected abstract void doValidate( T dto, Errors errors);
	
}
