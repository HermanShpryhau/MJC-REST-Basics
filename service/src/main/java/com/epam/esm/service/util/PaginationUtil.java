package com.epam.esm.service.util;

import java.util.function.Supplier;

public class PaginationUtil {
    public static int correctPage(int page, int size, Supplier<Integer> entityCountSupplier) {
        int maxPage = entityCountSupplier.get() / size;
        int correctedPage = page;
        if (correctedPage - 1 > maxPage) {
            correctedPage = 1;
        } else if (correctedPage < 1) {
            correctedPage = maxPage;
        }
        return correctedPage;
    }
}
