package com.epam.esm.persistence.repository;

public final class RepositoryErrorCode {
    public static final String OPERATION_NOT_SUPPORTED = "40301";
    public static final String DELETION_FORBIDDEN = "40302";
    public static final String CERTIFICATE_NOT_FOUND = "40401";
    public static final String TAG_NOT_FOUND = "40402";
    public static final String ORDER_NOT_FOUND = "40404";

    private RepositoryErrorCode() {
    }
}
