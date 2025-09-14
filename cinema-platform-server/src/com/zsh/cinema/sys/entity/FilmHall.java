package com.zsh.cinema.sys.entity;

import java.io.Serializable;

/**
 * 影厅类
 */
public class FilmHall implements Serializable {
    // 影厅编号
    private String id;
    // 影厅名
    private String name;
    // 总排数
    private int totalRow;
    // 总列数
    private int totalCol;
    // 座位列表
    private Seat[][] seats;
    // 空参构造
    public FilmHall() {}
    // 四参构造
    public FilmHall(String id, String name, int totalRow, int totalCol) {
        this.id = id;
        this.name = name;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.seats = new Seat[totalRow][totalCol];
        for (int i = 0;i < totalRow;i++) {
            for (int j = 0; j < totalCol; j++) {
                this.seats[i][j] = new Seat(i,j);
            }
        }
        this.seats[0][1].setOwner("zhangsan");
        this.seats[6][5].setOwner("lisi");
    }
    // 全参构造
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
     * 展示座位信息
     */
    public void showSeats(){
        System.out.print("# ");
        for (int i = 0; i < totalCol; i++) {
            System.out.print(" "+i+" ");
        }
        System.out.println();
        for (int i = 0;i < totalRow;i++) {
            System.out.print(i+" ");
            for (int j = 0; j < totalCol; j++) {
                Seat seat = seats[i][j];
                if (seat.getOwner() == null) {
                    System.out.print(seat);
                }else {
                    // 使用System.err（标准错误流）打印已选座位（红色），
                    // 由于out和err是两个不同的流，执行有先后，
                    // 故为保证座位表打印正确，
                    // 需要添加休眠代码让程序在每打印一个座位后休眠等待一段时间，
                    // PS:这方法效率可太低了，直接改控制台的字体颜色多方便
                    // System.err.print(seat);

                    // 直接使用ANSI 转义序列控制终端字体颜色，"\033[31m"是让字体变红，"\033[0m"是让字体回复默认颜色
                    System.out.print("\033[31m"+seat+"\033[0m");
                }
//                try {
//                    Thread.sleep(8L);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
            System.out.println();
        }
    }

    public String toString() {
        return id + "\t" + name + "\t" + totalRow * totalCol;
    }
}
