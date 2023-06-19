package com.bikkadit.elcetronicstore.utility;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("Message from isValid :{}", value);
        //logic to create a annotation
        //false means  property not validate
        //true means property validate
        return !value.isBlank();

    }
}
