package com.zsh.cinema.sys.entity;

import java.io.Serializable;

/*
 * 解冻申请类
 * */
public class UnfrozenApply implements Serializable {
    // 编号
    private String id;
    // 账号
    private String username;
    // 原因
    private String reason;
    // 处理状态：0-已处理，1-已通过，2-驳回
    private int state;
    // 空参构造
    public UnfrozenApply() {
    }
    // 三参构造
    public UnfrozenApply(String id, String username, String reason) {
        this.id = id;
        this.username = username;
        this.reason = reason;
    }
    // 全参构造
    public UnfrozenApply(String id, String username, String reason, int state) {
        this.id = id;
        this.username = username;
        this.reason = reason;
        this.state = state;
    }

    /**
     * 获取
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    public String toString() {
        String stateStr = state == 0 ? "待处理" : state == 1 ? "已处理" : "已驳回";
        return  id + "\t" + username + "\t" + reason + "\t" + stateStr;
    }
}
