package com.github.paohaijiao.test.xml.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long pageNum;

    private long pageSize;

    private long total;

    private long pages;

    private List<T> records;

    private boolean hasPrevious;

    private boolean hasNext;

    public Page() {
    }

    public Page(long pageNum, long pageSize, long total, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records == null ? Collections.<T>emptyList() : records;
        this.pages = pageSize > 0 ? (total + pageSize - 1) / pageSize : 0;
        this.hasPrevious = pageNum > 1;
        this.hasNext = pageNum < this.pages;
    }

    public static <T> Page<T> of(long pageNum, long pageSize, long total, List<T> records) {
        return new Page<T>(pageNum, pageSize, total, records);
    }

    public static <T> Page<T> of(long pageNum, long pageSize, List<T> allRecords) {
        List<T> source = allRecords == null ? Collections.<T>emptyList() : allRecords;
        long total = source.size();
        long fromIndex = Math.max(0, (pageNum - 1) * pageSize);
        if (fromIndex >= total) {
            return new Page<T>(pageNum, pageSize, total, Collections.<T>emptyList());
        }
        long toIndex = Math.min(total, fromIndex + pageSize);
        List<T> pageData = source.subList((int) fromIndex, (int) toIndex);
        return new Page<T>(pageNum, pageSize, total, pageData);
    }

    public static <T> Page<T> empty(long pageNum, long pageSize) {
        return new Page<T>(pageNum, pageSize, 0, Collections.<T>emptyList());
    }
}
