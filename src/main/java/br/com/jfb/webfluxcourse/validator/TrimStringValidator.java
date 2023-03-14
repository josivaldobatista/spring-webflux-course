package br.com.jfb.webfluxcourse.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimStringValidator implements ConstraintValidator<TrimString, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.trim().length() == value.length();
  }

//  @Override
//  public boolean isValid(String value, ConstraintValidatorContext context) {
//    if (value == null) {
//      return true;
//    }
//    return value.trim().length() == value.length();
//  }


}
