package com.compono.ibackend.common.validator;

import com.compono.ibackend.common.annotation.EnumClass;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumClass, Enum<?>> {

    private EnumClass annotation;

    @Override
    public void initialize(EnumClass constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if (this.annotation.value() == null) {
            return this.annotation.nullable();
        }

        for (Object enumValue : this.annotation.value().getEnumConstants()) {
            if (value == enumValue) {
                return true;
            }
        }

        return false;
    }
}
