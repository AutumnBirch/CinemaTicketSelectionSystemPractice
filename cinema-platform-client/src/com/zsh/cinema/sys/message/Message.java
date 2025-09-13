package com.zsh.cinema.sys.message;

import java.io.Serializable;

/*
* 传输的信息
* Message<T>：因为传输的信息是多种多样的，所以就选择可以改变的泛型
* 发送信息选择的是对向流，故需先实现序列化接口
* */
public class Message<T> implements Serializable {
    // 动作、行为
    private String action;
    // 数据
    private T data;

    public Message() {
    }

    public Message(String action, T data) {
        this.action = action;
        this.data = data;
    }

    /**
     * 获取
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * 设置
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 获取
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return action + "=>" + data;
    }
}
