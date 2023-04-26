package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmTagList;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmTagListValidator implements ConstraintValidator<NdmTagList, List<String>> {

    private boolean nullAllowed;
    private int maxListLength;
    private int maxListContentLength;
    private String listContentRegex;

    @Override
    public void initialize(final NdmTagList constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        maxListLength = constraintAnnotation.maxListLength();
        maxListContentLength = constraintAnnotation.maxListItemLength();
        listContentRegex = constraintAnnotation.listItemRegex();
    }

    @Override
    public boolean isValid(final List<String> tags, final ConstraintValidatorContext constraintContext) {

        if (tags == null) {
            return nullAllowed;
        }

        /*
         Tags when set cannot be empty, number of tags cannot exceed maxListLength (default 100)
         Each Tag Item in the Tags list cannot be empty, and cannot exceed maxListContentLength (default 100) in size,
         and each Tag Item must be in a correct format default allowed format is (tagkey:tagvalue)
         */

        return checkTagList(tags);
    }


    private boolean checkTagList(final List<String> tags) {

        return isTagListValid(tags) && isTagListItemsValid(tags);

    }

    private boolean isTagListValid(final List<String> tags) {
        return !tags.isEmpty() && tags.size() <= maxListLength;
    }

    private boolean isTagListItemsValid(final List<String> tags) {
        for (final String tag : tags) {
            if (isTagNotValid(tag)) {
                return false;
            }
        }

        return true;
    }

    private boolean isTagNotValid(final String tag) {

        return tag == null || tag.length() >= maxListContentLength || !tag.matches(listContentRegex);
    }
}
