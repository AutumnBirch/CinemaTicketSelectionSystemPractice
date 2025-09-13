package com.zsh.cinema.sys.entity;

import java.util.Date;
/*
* 订单
* */
public class Order {
    /*
    * 编号
    * */
    private String id;
    /*
    * 影片名
    * */
    private String filmName;
    /*
    * 开始时间
    * */
    private Date begin;
    /*
    * 结束时间
    * */
    private Date end;
    /*
    * 座位信息
    * */
    private String seatInfo;
    /*
    * 状态：0-退订中，1-正常，2-已退订
    * */
    private int state = 1;
    /*
    * 所属用户
    * */
    private String owner;

    public Order() {
    }

    public Order(String id, String filmName, Date begin, Date end, String seatInfo, int state, String owner) {
        this.id = id;
        this.filmName = filmName;
        this.begin = begin;
        this.end = end;
        this.seatInfo = seatInfo;
        this.state = state;
        this.owner = owner;
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
     * @return filmName
     */
    public String getFilmName() {
        return filmName;
    }

    /**
     * 设置
     * @param filmName
     */
    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    /**
     * 获取
     * @return begin
     */
    public Date getBegin() {
        return begin;
    }

    /**
     * 设置
     * @param begin
     */
    public void setBegin(Date begin) {
        this.begin = begin;
    }

    /**
     * 获取
     * @return end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * 设置
     * @param end
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * 获取
     * @return seatInfo
     */
    public String getSeatInfo() {
        return seatInfo;
    }

    /**
     * 设置
     * @param seatInfo
     */
    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
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

    /**
     * 获取
     * @return owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 设置
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String toString() {
        return "Order{id = " + id + ", filmName = " + filmName + ", begin = " + begin + ", end = " + end + ", seatInfo = " + seatInfo + ", state = " + state + ", owner = " + owner + "}";
    }
}
