package com.zsh.cinema.sys.entity;
/*
* 影厅
* */
public class FilmHall {
    /*
    * 影厅编号
    * */
    private String id;
    /*
    * 影厅名
    * */
    private String name;
    /*
    * 总排数
    * */
    private int totalRow;
    /*
    * 总列数
    * */
    private int totalCol;
    /*
    * 座位列表
    * */
    private Seat[][] seats;

    public FilmHall() {
    }

    public FilmHall(String id, String name, int totalRow, int totalCol, Seat[][] seats) {
        this.id = id;
        this.name = name;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.seats = seats;
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return totalRow
     */
    public int getTotalRow() {
        return totalRow;
    }

    /**
     * 设置
     * @param totalRow
     */
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    /**
     * 获取
     * @return totalCol
     */
    public int getTotalCol() {
        return totalCol;
    }

    /**
     * 设置
     * @param totalCol
     */
    public void setTotalCol(int totalCol) {
        this.totalCol = totalCol;
    }

    /**
     * 获取
     * @return seats
     */
    public Seat[][] getSeats() {
        return seats;
    }

    /**
     * 设置
     * @param seats
     */
    public void setSeats(Seat[][] seats) {
        this.seats = seats;
    }

    public String toString() {
        return "FilmHall{id = " + id + ", name = " + name + ", totalRow = " + totalRow + ", totalCol = " + totalCol + ", seats = " + seats + "}";
    }
}
