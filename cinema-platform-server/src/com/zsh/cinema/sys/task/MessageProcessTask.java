package com.zsh.cinema.sys.task;


import com.zsh.cinema.sys.entity.User;
import com.zsh.cinema.sys.message.Message;
import com.zsh.cinema.sys.util.FileUtil;
import com.zsh.cinema.sys.util.SocketUtil;

import java.net.Socket;
import java.util.List;
import java.util.function.Predicate;

/*
* 消息处理任务
* */
public class MessageProcessTask implements Runnable {

    private Socket client;

    public MessageProcessTask(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        // 等待客户端链接（不会造成内存溢出）
        Message msg = SocketUtil.receiveMsg(client);
        System.out.println(msg);
        // 信息处理
        if (msg != null) {
            switch (msg.getAction()){
                case "register":
                    processRegister(msg);
                    break;
            }
        }
    }

    /*
    * 功能：处理注册请求
    * 参数：
    * 返回值：
    * */
    public void processRegister(Message msg){
        User registerUser = (User) msg.getData();
        // 比对文件内是否已经存在用户
        List<User> storageUsers = FileUtil.readData(FileUtil.USER_FILE);
        if (storageUsers.isEmpty()){
            // 如果说存档信息为空，表示没用如何数据
            User user = new User("admin","123456","ZSH");
            user.setManager(true);
            storageUsers.add(user);
        }

        /*boolean exists = storageUsers.stream().anyMatch(new Predicate<User>() {
                @Override
                public boolean test(User user) {
                    return user.getUsername().equals(registerUser.getUsername());
                }
            });*/
        boolean exists = storageUsers.stream().anyMatch(user-> user.getUsername().equals(registerUser.getUsername()));
        if (exists) {
            // 账号已被注册
            SocketUtil.sendBack(client,-1);
        }else {
            // 账号未被注册，注册账号
            storageUsers.add(registerUser);
            boolean success = FileUtil.saveData(storageUsers, FileUtil.USER_FILE);
            SocketUtil.sendBack(client, success ? 1 : 0);
        }
    }
}
