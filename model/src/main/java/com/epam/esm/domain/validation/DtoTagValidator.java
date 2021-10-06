package com.epam.esm.domain.validation;

import com.epam.esm.domain.Tag;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DtoTagValidator implements ConstraintValidator<DtoTag, Tag> {
    @Override
    public boolean isValid(Tag tag, ConstraintValidatorContext context) {
        boolean valid = true;
        if (!((tag.getName() != null
                && tag.getName().length() < 45
                && tag.getName().length() > 1)
                || (tag.getId() != null))) {
            context.buildConstraintViolationWithTemplate("{tag.dto.constraint.message}");
            valid = false;
        }
        return valid;
    }
}
