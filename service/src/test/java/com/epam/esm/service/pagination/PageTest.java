package com.epam.esm.service.pagination;

import com.epam.esm.model.dto.DataTransferObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {
    private static final List<DataTransferObject> emptyContent = Collections.emptyList();

    @Test
    void getTotalPagesTest() {
        Page<DataTransferObject> page = new Page<>(1, 2, 11, emptyContent);
        Assertions.assertEquals(6, page.getTotalPages());
    }

    @Test
    void hasNextWithNextPageTest() {
        Page<DataTransferObject> page = new Page<>(2, 2, 11, emptyContent);
        Assertions.assertTrue(page.hasNext());
    }

    @Test
    void hasNextWithNoNextPageTest() {
        Page<DataTransferObject> page = new Page<>(2, 6, 11, emptyContent);
        Assertions.assertFalse(page.hasNext());
    }

    @Test
    void hasPreviousWithPreviousPageTest() {
        Page<DataTransferObject> page = new Page<>(3, 2, 11, emptyContent);
        Assertions.assertTrue(page.hasPrevious());
    }

    @Test
    void hasPreviousWithNoPreviousPageTest() {
        Page<DataTransferObject> page = new Page<>(1, 2, 11, emptyContent);
        Assertions.assertFalse(page.hasPrevious());
    }

    @Test
    void isLastWhenLastTest() {
        Page<DataTransferObject> page = new Page<>(6, 2, 11, emptyContent);
        Assertions.assertTrue(page.isLast());
    }

    @Test
    void isLastWhenNotLastTest() {
        Page<DataTransferObject> page = new Page<>(3, 2, 11, emptyContent);
        Assertions.assertFalse(page.isLast());
    }

    @Test
    void getNextPageIndexTest() {
        Page<DataTransferObject> page = new Page<>(3, 2, 11, emptyContent);
        Assertions.assertEquals(4, page.getNextPageIndex());
    }

    @Test
    void getNextPageIndexWhenLastTest() {
        Page<DataTransferObject> page = new Page<>(6, 2, 11, emptyContent);
        Assertions.assertEquals(1, page.getNextPageIndex());
    }

    @Test
    void getPreviousPageIndexTest() {
        Page<DataTransferObject> page = new Page<>(4, 2, 11, emptyContent);
        Assertions.assertEquals(3, page.getPreviousPageIndex());
    }

    @Test
    void getPreviousPageIndexWhenFirstTest() {
        Page<DataTransferObject> page = new Page<>(1, 2, 11, emptyContent);
        Assertions.assertEquals(6, page.getPreviousPageIndex());
    }
}