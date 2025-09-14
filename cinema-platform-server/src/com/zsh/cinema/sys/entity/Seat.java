package com.zsh.cinema.sys.entity;

import java.io.Serializable;

/**
* 座位类
*/
public class Seat implements Serializable {
    // 排号
    private int row;
    // 列号
    private int col;
    // 所有者
    private String owner;

    public Seat() {
    }

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Seat(int row, int col, String owner) {
        this.row = row;
        this.col = col;
        this.owner = owner;
    }

    /**
     * 获取
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * 设置
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * 获取
     * @return col
     */
    public int getCol() {
        return col;
    }

    /**
     * 设置
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
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
        return " ■ ";
    }
}
