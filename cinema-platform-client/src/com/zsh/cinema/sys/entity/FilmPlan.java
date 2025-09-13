package com.zsh.cinema.sys.entity;

import java.util.Date;

/*
* 影片播放计划
* */
public class FilmPlan {
    /*
    * 编号
    * */
    private String id;
    /*
    * 影片
    * */
    private Film film;
    /*
    * 影厅
    * */
    private FilmHall filmHall;
    /*
    * 开始时间
    * */
    private Date begin;
    /*
    * 结束时间
    * */
    private Date end;


    public FilmPlan() {
    }

    public FilmPlan(String id, Film film, FilmHall filmHall, Date begin, Date end) {
        this.id = id;
        this.film = film;
        this.filmHall = filmHall;
        this.begin = begin;
        this.end = end;
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
     * @return film
     */
    public Film getFilm() {
        return film;
    }

    /**
     * 设置
     * @param film
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * 获取
     * @return filmHall
     */
    public FilmHall getFilmHall() {
        return filmHall;
    }

    /**
     * 设置
     * @param filmHall
     */
    public void setFilmHall(FilmHall filmHall) {
        this.filmHall = filmHall;
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

    public String toString() {
        return "FilmPlan{id = " + id + ", film = " + film + ", filmHall = " + filmHall + ", begin = " + begin + ", end = " + end + "}";
    }
}
