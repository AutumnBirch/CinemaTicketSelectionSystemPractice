package com.zsh.cinema.sys.entity;

import java.io.Serializable;

/*
* 影片
* 选择对象流来实现信息传输，传输的对象必须实现序列化接口
* */
public class Film implements Serializable {
    /*
    * 编号
    * */
    private String id;
    /*
    * 名称
    * */
    private String name;
    /*
    * 制片人
    * */
    private String producer;
    /*
    * 描述
    * */
    private String description;;

    public Film() {
    }

    public Film(String id, String name, String producer, String description) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.description = description;
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
     * @return producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * 设置
     * @param producer
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * 获取
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return id + "\t" + name + "\t" + producer + "\t" + description;
    }
}
