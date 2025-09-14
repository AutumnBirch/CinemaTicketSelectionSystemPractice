package com.zsh.cinema.sys.util;

import com.zsh.cinema.sys.message.Message;

import java.io.*;
import java.net.Socket;

/**
 * 套接字（Socket）通信工具类，封装客户端与服务端之间的消息接收、结果传回逻辑
 * <p>
 * 核心功能：
 * 1. 从客户端Socket读取序列化的{@link Message}对象（消息接收）
 * 2. 向客户端Socket写入序列化数据（处理结果发送）
 * </p>
 * 注意事项：
 * - 本类所有方法均为静态方法，无需创建工具类实例，可直接通过类名调用
 * - 本方法内部已处理Socket输入流/输出流的关闭（如{@link Socket#shutdownInput()}），外部无需重复操作
 * - 异常处理中包含关键错误日志打印（如接收消息失败）或运行时异常抛出（如回传结果失败），需关注调用时的异常捕获
 * 即：调用工具类的方法时，要先看这个方法是怎么处理异常的（是返回 null 还是抛异常），然后针对性地写代码处理（判断 null 或 try-catch），否则可能引入新的 bug。
 */
public class SocketUtil {

    /**
     * 从客户端Socket接收序列化的消息对象（{@link Message}）。
     * <p>
     * 处理流程：
     * 1. 获取客户端Socket的输入流
     * 2. 通过{@link ObjectInputStream}反序列化流数据为{@link Message}泛型对象
     * 3. 关闭Socket输入流（调用{@link Socket#shutdownInput()}，避免流资源泄漏）
     * 4. 返回序列化后的消息对象：若过程中发生一次，打印栈堆日志并返回null
     * </p>
     * @param client 客户端与服务端建立连接后的{@link Socket}实例，必须是已成功的有效对象
     * @param <T> 消息对象{@link Message}中携带的数据类型（泛型），支持任意可序列化类型（需实现{@link Serializable}）
     * @return {@link Message}<{@link T}> - 反序列化成功的消息对象，包含客户端发送的业务数据；若发生IO异常、反序列化异常等，返回null
     */
    public static <T> Message<T> receiveMsg(Socket client) {
        try {
            InputStream is = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Message<T> msg = (Message<T>) ois.readObject();
            client.shutdownInput(); // 关闭输入流，防止资源泄露
            return msg;
        } catch (Exception e) {
            // 此处有可能出bug，保留一条注释用于提醒
            // 打印异常堆栈，便于定位问题
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 功能：向客户端返回处理结果
     * @param client 客户端套接字
     * @param data 处理结果
     * @param <V> 返回的消息，因为不知道客户端发送的信息携带的是什么数据类型，因此使用泛型
     */
    public static <V> void sendBack(Socket client,V data) {
        try {
            OutputStream os = client.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(data);
            oos.flush();
            client.shutdownOutput();
        } catch (IOException e) {
            throw new RuntimeException();
            // 修改过，可能与注册时无法连接服务端的bug有关，此处注释暂时保留
            // e.printStackTrace();
        }
    }
}
