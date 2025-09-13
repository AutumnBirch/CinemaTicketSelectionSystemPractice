package com.zsh.cinema.sys.util;

/*
* 套接字工具类
* */

import com.zsh.cinema.sys.message.Message;

import java.io.*;
import java.net.Socket;

public class SocketUtil {


    public static <T> Message<T> receiveMsg(Socket client) {
        // 读取信息
        try {
            InputStream is = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Message<T> msg = (Message<T>) ois.readObject();
            client.shutdownInput();
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /*
    * 向客户端返回处理结果
    * client 客户端套接字
    * data 处理结果
    * <T> 因为不知道客户端发送的信息携带的是什么数据类型，因此使用泛型
    * */
    public static <V> void sendBack(Socket client,V data) {

        try {
            OutputStream os = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(data);
            oos.flush();
            client.shutdownOutput();
        } catch (IOException e) {
            // 修改过，可能与注册时无法连接服务端的bug有关
            throw new RuntimeException();
//            e.printStackTrace();
        }
    }






}
