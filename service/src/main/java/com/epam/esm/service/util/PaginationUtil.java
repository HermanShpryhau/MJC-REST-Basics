package com.epam.esm.service.util;

import java.util.function.Supplier;

/**
 * Utility class with methods to help with pagination implementation.
 */
public class PaginationUtil {

    /**
     * Index of first page.
     */
    public static final int FIRST_PAGE = 1;

    /**
     * Index of last page
     */
    public static final int LAST_PAGE = -1;

    /**
     * Corrects page number to be consistent with total elements count.
     *
     * @param page                 Index of page
     * @param size                 Size of page
     * @param elementCountSupplier Supplier of elements' count.
     * @return First page index if provided page index is less than 1. Last page index if provided one exceeds maximum
     * elements count. Provided page index otherwise.
     */
    public static int correctPageIndex(int page, int size, Supplier<Integer> elementCountSupplier) {
        int maxPage = elementCountSupplier.get() / size;
        maxPage = maxPage == 0 ? 1 : maxPage;
        int correctedPage = page;
        if (correctedPage - 1 > maxPage) {
            correctedPage = 1;
        } else if (correctedPage < 1) {
            correctedPage = maxPage;
        }
        return correctedPage;
    }

    public static int nextPage(int currentPage, int size, Supplier<Integer> elementCountSupplier) {
        return correctPageIndex(currentPage + 1, size, elementCountSupplier);
    }

    public static int previousPage(int currentPage, int size, Supplier<Integer> elementCountSupplier) {
        return correctPageIndex(currentPage - 1, size, elementCountSupplier);
    }
}
