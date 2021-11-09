package com.epam.esm.model.validation;

import com.epam.esm.model.dto.TagDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DtoTagValidator implements ConstraintValidator<DtoTag, TagDto> {
    @Override
    public boolean isValid(TagDto tag, ConstraintValidatorContext context) {
        boolean valid = true;
        if (tag.getName() == null || tag.getName().length() > 45 || tag.getName().length() < 1) {
            context.buildConstraintViolationWithTemplate("{tag.dto.constraint.message}");
            valid = false;
        }
        return valid;
    }
}
