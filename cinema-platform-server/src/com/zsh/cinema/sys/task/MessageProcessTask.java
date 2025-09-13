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
                // 注册
                case "register":
                    processRegister(msg);
                    break;

                // 登录
                case "login":

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
            // 如果说存档信息为空，表明没有任何用户注册
            User user = new User("admin","123456","admin");
            user.setManager(true); // 设置为管理员
            storageUsers.add(user); // 添加至用户列表
        }
        int result;
        /*boolean exists = storageUsers.stream().anyMatch(new Predicate<User>() {
                @Override
                public boolean test(User user) {
                    return user.getUsername().equals(registerUser.getUsername());
                }
            });*/
        boolean exists = storageUsers.stream().anyMatch(user-> user.getUsername().equals(registerUser.getUsername()));
        if (exists) {
            // 账号已被注册
            result = -1;
//            SocketUtil.sendBack(client,-1);
            // 如果账号已经存在
        }else {
            // 账号未被注册，注册账号
            storageUsers.add(registerUser);
            // 用户存档
            boolean success = FileUtil.saveData(storageUsers, FileUtil.USER_FILE);
            result = success ? 1 : 0;
//            SocketUtil.sendBack(client, success ? 1 : 0);
        }
        // 向客户端反馈结果
        SocketUtil.sendBack(client,result);
    }
}
