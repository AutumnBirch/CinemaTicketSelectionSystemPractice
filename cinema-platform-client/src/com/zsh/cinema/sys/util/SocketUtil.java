package com.zsh.cinema.sys.util;

import com.zsh.cinema.sys.message.Message;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/*
* 套接字工具类
* */
public class SocketUtil {

    private static final String IP = "localhost";

    private static final int PORT = 8888;

    /*
        向服务器端发送消息
        <T>因为不确定发送消息携带的数据类型，故使用泛型
        <V>因为不确定接收的数据类型，故也选择使用泛型
     */
    public static <T,V> V sendMessage(Message<T> msg) {

        /*Socket client = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            client = new Socket(IP, PORT);
            client.setSoTimeout(TIMEOUT); // 设置超时时间

            // 获取输出流并发送消息
            OutputStream os = client.getOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(msg);
            oos.flush();
            client.shutdownOutput();

            // 获取输入流并读取响应
            InputStream is = client.getInputStream();
            ois = new ObjectInputStream(is);
            V result = (V) ois.readObject();
            return result;
        } catch (SocketTimeoutException e) {
            System.err.println("服务器响应超时，请检查服务器是否正常运行");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // 确保资源正确关闭
            try {
                if (ois != null) ois.close();
                if (oos != null) oos.close();
                if (client != null && !client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/


        try {
            Socket client = new Socket(IP,PORT);
            // 获取输出流
            OutputStream os = client.getOutputStream();
            // 将输出流包装为对象流
            ObjectOutputStream oos = new ObjectOutputStream(os);
            // 对象流写对象，也就是信息传输
            oos.writeObject(msg);
            // 强制将通道中的信息刷出到服务端中
            oos.flush();
            // 告诉服务器端信息传输已经完毕,关闭输出流
            client.shutdownOutput();
            // 获取输入流
            InputStream is = client.getInputStream();
            // 将输入流包装为对象输入流
            ObjectInputStream ois = new ObjectInputStream(is);
            V result = (V) ois.readObject();
            // 告诉服务器端信息读取已经完毕
            client.shutdownInput();
            return result;
        }catch (Exception e) {
            // 修改过，可能与注册时无法连接服务端的bug有关
            // 改回去试试
            // 程序又报错了，报错里又有这一行，再改成带return null的两句试试。
            // 已经确认bug来源了，是服务端FIleUtil工具类的readData方法没有返回给客户端数据导致的程序卡死，客户端接受不到数据，自然无法继续运行，最后就会输出服务端链接超时的报错信息
            // 但是，由于这里也可能需要返回数据null，所以之后出现相关问题重点关注这里，现在还是选择抛出异常
//            throw new RuntimeException();
            // 好家伙，还真出问题了，在影片增删改查功能里面有需要接收空数据的地方，这里如果直接抛出异常，不传个null回去，就会直接抛出RuntimeException()然后结束程序
            e.printStackTrace();
            return null;
        }


    }
}
