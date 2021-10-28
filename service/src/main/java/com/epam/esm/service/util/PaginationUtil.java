package com.epam.esm.service.util;

import java.util.function.Supplier;

/**
 * Utility class with methods to help with pagination implementation.
 */
public class PaginationUtil {
    /**
     * Corrects page number to be consistent with total elements count.
     *
     * @param page Index of page
     * @param size Size of page
     * @param elementCountSupplier Supplier of elements' count.
     * @return First page index if provided page index is less than 1. Last page index if provided one exceeds maximum
     * elements count. Provided page index otherwise.
     */
    public static int correctPage(int page, int size, Supplier<Integer> elementCountSupplier) {
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
}
