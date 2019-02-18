package com.example.model;

import java.io.Serializable;

public class SysPermit implements Serializable {
    private Integer permitId;

    private String permitName;

    private String permitDescription;

    private String permitUrl;

    private Integer permitTable;

    private Integer permitRank;

    private String permitMethod;

    private static final long serialVersionUID = 1L;

    public Integer getPermitId() {
        return permitId;
    }

    public void setPermitId(Integer permitId) {
        this.permitId = permitId;
    }

    public String getPermitName() {
        return permitName;
    }

    public void setPermitName(String permitName) {
        this.permitName = permitName == null ? null : permitName.trim();
    }

    public String getPermitDescription() {
        return permitDescription;
    }

    public void setPermitDescription(String permitDescription) {
        this.permitDescription = permitDescription == null ? null : permitDescription.trim();
    }

    public String getPermitUrl() {
        return permitUrl;
    }

    public void setPermitUrl(String permitUrl) {
        this.permitUrl = permitUrl == null ? null : permitUrl.trim();
    }

    public Integer getPermitTable() {
        return permitTable;
    }

    public void setPermitTable(Integer permitTable) {
        this.permitTable = permitTable;
    }

    public Integer getPermitRank() {
        return permitRank;
    }

    public void setPermitRank(Integer permitRank) {
        this.permitRank = permitRank;
    }

    public String getPermitMethod() {
        return permitMethod;
    }

    public void setPermitMethod(String permitMethod) {
        this.permitMethod = permitMethod;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", permitId=" + permitId +
                ", permitName=" + permitName +
                ", permitDescription=" + permitDescription +
                ", permitUrl=" + permitUrl +
                ", permitTable=" + permitTable +
                ", permitRank=" + permitRank +
                ", permitMethod=" + permitMethod +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}