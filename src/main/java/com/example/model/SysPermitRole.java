package com.example.model;

import java.io.Serializable;

public class SysPermitRole implements Serializable {
    private Integer permitRoleId;

    private Integer permitId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permitRoleId=").append(permitRoleId);
        sb.append(", permitId=").append(permitId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}