package org.acme.shared.dto;

import java.util.List;

public class DataTableResponse<T> {
    private List<T> data;
    private long totalRecords;
    private long filteredRecords;

    public DataTableResponse() {
    }

    public DataTableResponse(List<T> data, long totalRecords, long filteredRecords) {
        this.data = data;
        this.totalRecords = totalRecords;
        this.filteredRecords = filteredRecords;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getFilteredRecords() {
        return filteredRecords;
    }

    public void setFilteredRecords(long filteredRecords) {
        this.filteredRecords = filteredRecords;
    }
}
