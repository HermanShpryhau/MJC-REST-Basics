package com.epam.esm.domain.validation;

public class ValidationErrorCode {
    public static final String CERTIFICATE_NAME_NOT_NULL = "40001";
    public static final String INVALID_CERTIFICATE_NAME_LENGTH = "40002";
    public static final String CERTIFICATE_DESCRIPTION_NOT_NULL = "40003";
    public static final String INVALID_CERTIFICATE_DESCRIPTION_LENGTH = "40004";
    public static final String CERTIFICATE_PRICE_NOT_NULL = "40005";
    public static final String INVALID_CERTIFICATE_PRICE = "40006";
    public static final String CERTIFICATE_DURATION_NOT_NULL = "40007";
    public static final String INVALID_CERTIFICATE_DURATION = "40008";
    public static final String DTO_TAG_LIST_NOT_NULL = "40009";
    public static final String INVALID_DTO_TAG = "40010";

    private ValidationErrorCode() {
    }
}