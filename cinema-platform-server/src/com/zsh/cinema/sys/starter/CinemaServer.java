package com.zsh.cinema.sys.starter;

import com.zsh.cinema.sys.message.Message;
import com.zsh.cinema.sys.task.MessageProcessTask;
import com.zsh.cinema.sys.util.SocketUtil;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
* 影院服务器
* */
public class CinemaServer {

    private ServerSocket serverSocket;

    public CinemaServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }
    /*
    * 启动时应该是死循环，不能终止
    * 抛异常也不能终止，只能在循环中处理（也就是用try catch）
    *
    * */
    public void start(){
        while (true){
            try {
                // 等待客户端链接（不会造成内存溢出）
                Socket client = serverSocket.accept();
                // 来一个客户开一个线程
                new Thread(new MessageProcessTask(client)).start();
/*                Message msg = SocketUtil.receiveMsg(client);
                System.out.println(msg);

                // 信息处理
                if (msg != null) {
                    System.out.println(msg.getAction());
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            CinemaServer server = new CinemaServer(8888);
            server.start();
        } catch (IOException e) {
            // 修改过，可能与注册时无法连接服务端的bug有关
            throw new RuntimeException();
//            e.printStackTrace();
        }
    }

}
