package com.zsh.cinema.sys.task;


import com.zsh.cinema.sys.entity.Film;
import com.zsh.cinema.sys.entity.UnfrozenApply;
import com.zsh.cinema.sys.entity.User;
import com.zsh.cinema.sys.message.Message;
import com.zsh.cinema.sys.util.FileUtil;
import com.zsh.cinema.sys.util.SocketUtil;

import java.net.Socket;
import java.util.*;
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
                    processLogin(msg);
                    break;
                // 找回密码
                case "getPasswordBack":
                    processGetPasswordBack(msg);
                    break;
                // 解冻申请
                case "unfrozenApply":
                    processUnfrozenApply(msg);
                    break;
                // 增加影片
                case "addFilm":
                    processAddFilm(msg);
                    break;
                // 删除影片
                case "deleteFilm":
                    processDeleteFilm(msg);
                    break;
                // 修改影片
                case "updateFilm":
                    processUpdateFilm(msg);
                    break;
                // 查看影片
                case "getFilmList":

                    break;
            }
        }
    }
    /*
     * 功能：处理删除影片请求
     * 参数：
     * 返回值：
     * */
    private void processDeleteFilm(Message msg) {
        String id = (String) msg.getData();
        List<Film> films = FileUtil.readData(FileUtil.FILM_FILE);

        int index = -1;
        for (int i = 0; i < films.size(); i++) {
            Film film = films.get(i);
            if (id.equals(film.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) { // 说明删除的影片信息不存在
            SocketUtil.sendBack(client,-1);
        }else {
            films.remove(index);
            boolean success = FileUtil.saveData(films,FileUtil.FILM_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }
    }

    /*
     * 功能：处理修改影片请求
     * 参数：
     * 返回值：
     * */
    private void processUpdateFilm(Message msg) {
        Film updateFilm = (Film) msg.getData();
        List<Film> films = FileUtil.readData(FileUtil.FILM_FILE);

        int index = -1;
        for (int i = 0; i < films.size(); i++) {
            Film film = films.get(i);
            if (updateFilm.getId().equals(film.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) { // 说明修改的影片信息不存在
            SocketUtil.sendBack(client,-1);
        }else {
            films.set(index,updateFilm);
            boolean success = FileUtil.saveData(films,FileUtil.FILM_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }

    }

    /*
     * 功能：处理添加影片请求
     * 参数：
     * 返回值：
     * */
    private void processAddFilm(Message msg) {
        // 从客户端获取管理员添加的影片信息
        Film film = (Film) msg.getData();
        // 读取存储在data文件夹里的影片信息
        List<Film> films = FileUtil.readData(FileUtil.FILM_FILE);
        // 将影片添加到影片列表
        films.add(film);
        // 保存修改后的影片列表
        boolean success = FileUtil.saveData(films,FileUtil.FILM_FILE);
        SocketUtil.sendBack(client,success ? 1 : 0 );
    }

    /*
     * 功能：处理解冻申请请求
     * 参数：
     * 返回值：
     * */
    private void processUnfrozenApply(Message msg) {
        UnfrozenApply apply = (UnfrozenApply) msg.getData();
        // 读取存档的用户信息
        List<User> storageUsers = FileUtil.readData(FileUtil.USER_FILE);
        if (storageUsers.isEmpty()) {
            User user = new User("admin","123456","admin");
            user.setManager(true); // 设置为管理员
            storageUsers.add(user); // 添加至用户列表
            FileUtil.saveData(storageUsers,FileUtil.USER_FILE);
        }
        int result;
        Optional<User> optionalUser = storageUsers.stream().filter(user -> user.getUsername().equals(apply.getUsername())).findFirst();
        if (optionalUser.isPresent()) { // 账号存在
            User user = optionalUser.get();
            if (user.getState() == 1) { // 账号正常
                result = -1;
            }else { // 账号被冻结
                List<UnfrozenApply> applies = FileUtil.readData(FileUtil.UNFROZEN_APPLY_FILE);
                applies.add(apply);
                boolean success = FileUtil.saveData(applies,FileUtil.UNFROZEN_APPLY_FILE);
                result = success ? 1 : 0;
            }
        }else { // 账号不存在
            result = -1;
        }
        SocketUtil.sendBack(client,result);
    }

    /*
     * 功能：处理找回密码请求请求
     * 参数：
     * 返回值：
     * */
    private void processGetPasswordBack(Message msg) {
        User getBackUser = (User) msg.getData();
        // 读取存档的用户信息
        List<User> storageUsers = FileUtil.readData(FileUtil.USER_FILE);
        if (storageUsers.isEmpty()) {
            User user = new User("admin","123456","admin");
            user.setManager(true); // 设置为管理员
            storageUsers.add(user); // 添加至用户列表
            FileUtil.saveData(storageUsers,FileUtil.USER_FILE);
        }
        String result = null;
        Optional<User> optionalUser = storageUsers.stream().filter(user -> user.getUsername().equals(getBackUser.getUsername())).findFirst();
        if (optionalUser.isPresent()) { // isPresent用于检查optionalUser里面是否有东西
            // 如果有
            User user = optionalUser.get();
            // 安全码匹配
            if (user.getSecurityCode().equals(getBackUser.getSecurityCode())){
                result = user.getPassword();
            }
        }
        SocketUtil.sendBack(client,result);
    }

    /*
     * 功能：处理登录请求
     * 参数：
     * 返回值：
     * */
    private void processLogin(Message msg) {
        User loginUser = (User)msg.getData();
        // 读取存档的用户信息
        List<User> storageUsers = FileUtil.readData(FileUtil.USER_FILE);
        if (storageUsers.isEmpty()) {
            User user = new User("admin","123456","admin");
            user.setManager(true); // 设置为管理员
            storageUsers.add(user); // 添加至用户列表
        }
        //
        Map<String, Object> result = new HashMap<>();
        // 查找用户名与登录用户的用户名匹配的账号
        Optional<User> optionalUser = storageUsers.stream().filter(user -> user.getUsername().equals(loginUser.getUsername())).findFirst();
        // 如果找到了
        if (optionalUser.isPresent()){
            User user = optionalUser.get();// 取出来
            int state = user.getState(); // 获取用户状态
            if (state == 1){ // 正常
                // 密码匹配
                if (loginUser.getPassword().equals(user.getPassword())) {
                    result.put("process", 1);
                    result.put("manager", user.isManager());
                }else { // 密码不匹配
                    result.put("process",0);
                }
            } else { // 账号冻结
                result.put("process",-2);
            }
        }else { // 账号不存在
            result.put("process",-1);
        }

        SocketUtil.sendBack(client,result);
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
