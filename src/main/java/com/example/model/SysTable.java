package com.example.model;

import java.io.Serializable;

public class SysTable implements Serializable {
    private Integer tableId;

    private String tableName;

    private String tableDescription;

    private static final long serialVersionUID = 1L;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getTableDescription() {
        return tableDescription;
    }

    public void setTableDescription(String tableDescription) {
        this.tableDescription = tableDescription == null ? null : tableDescription.trim();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", tableId=" + tableId +
                ", tableName=" + tableName +
                ", tableDescription=" + tableDescription +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}