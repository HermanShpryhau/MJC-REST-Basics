package com.epam.esm.service.pagination;

import com.epam.esm.model.dto.DataTransferObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a container for DTOs fetched by service and information about the page.
 *
 * @param <T> DTO class.
 */
public final class Page<T extends DataTransferObject> {
    /**
     * Index of the first page.
     */
    public static final int FIRST_PAGE = 1;

    /**
     * Index of this page.
     */
    private final int index;

    /**
     * Size of this page.
     */
    private final int size;

    /**
     * Total count of elements fetched by the operation.
     */
    private final int totalElementsCount;

    /**
     * Content of the page.
     */
    private final List<T> content;

    public Page(int index, int size, int totalElementsCount, List<T> content) {
        this.index = index;
        this.size = size;
        this.totalElementsCount = totalElementsCount;
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }

    public int getTotalElementsCount() {
        return totalElementsCount;
    }

    public List<T> getContent() {
        return new ArrayList<>(content);
    }

    /**
     *
     * @return Total number of pages with specified size
     */
    public int getTotalPages() {
        return size == 0 ? 1 : (int) Math.ceil((double) totalElementsCount / (double) size);
    }

    /**
     *
     * @return True if there is a next page
     */
    public boolean hasNext() {
        return index + 1 <= getTotalPages();
    }

    /**
     *
     * @return True there is a previous page
     */
    public boolean hasPrevious() {
        return index - 1 > 0;
    }

    /**
     *
     * @return True if current page index is the last one.
     */
    public boolean isLast() {
        return !hasNext();
    }

    /**
     *
     * @return Index of next page or 1 if there is no next page.
     */
    public int getNextPageIndex() {
        return hasNext() ? index + 1 : FIRST_PAGE;
    }

    /**
     *
     * @return Index of previous page or index of last page if there is no previous page.
     */
    public int getPreviousPageIndex() {
        return hasPrevious() ? index - 1 : getTotalPages();
    }
}
