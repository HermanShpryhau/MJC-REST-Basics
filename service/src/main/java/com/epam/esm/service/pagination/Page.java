package com.epam.esm.service.pagination;

import com.epam.esm.model.dto.DataTransferObject;

import java.util.List;

public final class Page<T extends DataTransferObject> {
    public static final int FIRST_PAGE = 1;
    private final int index;
    private final int size;
    private final int totalElementsCount;
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
        return content;
    }

    public int getTotalPages() {
        return size == 0 ? 1 : (int) Math.ceil((double) totalElementsCount / (double) size);
    }

    public boolean hasNext() {
        return index + 1 <= getTotalPages();
    }

    public boolean hasPrevious() {
        return index - 1 > 0;
    }

    public boolean isLast() {
        return !hasNext();
    }

    public int getNextPageIndex() {
        return hasNext() ? index + 1 : FIRST_PAGE;
    }

    public int getPreviousPageIndex() {
        return hasPrevious() ? index - 1 : getTotalPages();
    }
}
