package com.example.response;

public class RolePermitResponse {
    private Integer permitRoleId;

    private Integer permitId;

    private Integer roleId;

    private String roleName;

    private String roleDescription;

    private Integer roleRank;

    private String permitDescription;

    private String permitUrl;

    private Integer permitTable;

    private Integer permitRank;

    private String permitMethod;

    private String tableName;

    private String tableDescription;

    public Integer getPermitRoleId() {
        return permitRoleId;
    }

    public void setPermitRoleId(Integer permitRoleId) {
        this.permitRoleId = permitRoleId;
    }

    public Integer getPermitId() {
        return permitId;
    }

    public void setPermitId(Integer permitId) {
        this.permitId = permitId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getRoleRank() {
        return roleRank;
    }

    public void setRoleRank(Integer roleRank) {
        this.roleRank = roleRank;
    }

    public String getPermitDescription() {
        return permitDescription;
    }

    public void setPermitDescription(String permitDescription) {
        this.permitDescription = permitDescription;
    }

    public String getPermitUrl() {
        return permitUrl;
    }

    public void setPermitUrl(String permitUrl) {
        this.permitUrl = permitUrl;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDescription() {
        return tableDescription;
    }

    public void setTableDescription(String tableDescription) {
        this.tableDescription = tableDescription;
    }

    @Override
    public String toString() {
        return "RolePermitResponse{" +
                "permitRoleId=" + permitRoleId +
                ", permitId=" + permitId +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", roleRank=" + roleRank +
                ", permitDescription='" + permitDescription + '\'' +
                ", permitUrl='" + permitUrl + '\'' +
                ", permitTable=" + permitTable +
                ", permitRank=" + permitRank +
                ", permitMethod='" + permitMethod + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableDescription='" + tableDescription + '\'' +
                '}';
    }
}
