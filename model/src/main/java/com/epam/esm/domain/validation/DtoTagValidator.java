package com.epam.esm.domain.validation;

import com.epam.esm.domain.Tag;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DtoTagValidator implements ConstraintValidator<DtoTag, Tag> {
    @Override
    public boolean isValid(Tag tag, ConstraintValidatorContext context) {
        boolean valid = true;
        if (tag.getName() == null || tag.getName().length() < 45 || tag.getName().length() > 1) {
            context.buildConstraintViolationWithTemplate("{tag.dto.constraint.message}");
            valid = false;
        }
        return valid;
    }
}
