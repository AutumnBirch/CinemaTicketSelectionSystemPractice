package com.zsh.cinema.sys.action;

import com.zsh.cinema.sys.entity.Film;
import com.zsh.cinema.sys.entity.UnfrozenApply;
import com.zsh.cinema.sys.entity.User;
import com.zsh.cinema.sys.message.Message;
import com.zsh.cinema.sys.util.IdGenerator;
import com.zsh.cinema.sys.util.InputUtil;
import com.zsh.cinema.sys.util.SocketUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/*
* 用户行为
* */
public class UserAction {
    /*
    * 注册
    * */
    public static void register() {
        String username = InputUtil.getInputText("请输入账号：");
        String password = InputUtil.getInputText("请输入密码：");
        String securityCode = InputUtil.getInputText("请输入安全码：");

        User user = new User(username,password,securityCode);
        // 要用对象流把user对象传递过去，user对象就得实现序列化接口
        Message<User> msg = new Message<>("register",user);
        // 发送数据
        Integer result = SocketUtil.sendMessage(msg);
        if (result != null && result == 1) {
            System.out.println("注册成功！");
        }else if (result != null && result == -1){
            System.out.println("账号已被注册！");
        }else {
            System.out.println("注册失败，请稍后重试...");
        }

        /*try {
            Integer result = SocketUtil.sendMessage(msg);
            if (result != null && result == 1) {
                System.out.println("注册成功！");
            }else if (result != null && result == -1){
                System.out.println("账号已被注册！");
            }else {
                System.out.println("注册失败，请稍后重试...");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }


    /*
    * 登录
    * */
    public static Map<String,Object> login(){
        String username = InputUtil.getInputText("请输入账号：");
        String password = InputUtil.getInputText("请输入密码：");

        User user = new User(username,password,null);
        Message<User> msg = new Message<>("login",user);
        return SocketUtil.sendMessage(msg);
    }
    /*
    * 找回密码
    * */
    public static void getPasswordBack(){
        String username = InputUtil.getInputText("请输入账号：");
        String securityCode = InputUtil.getInputText("请输入安全码：");

        User user = new User(username, null, securityCode);

        Message<User> msg = new Message<>("getPasswordBack",user);
        String result = SocketUtil.sendMessage(msg);

        if (result == null) {
            System.out.println("安全码不正确，请重新尝试哦~");
        }else {
            System.out.println("您的密码是："+result);
        }
    }
    /*
    * 申请解冻
    * */
    public static void unfrozenApply(){
        String username = InputUtil.getInputText("请输入账号：");
        String reason = InputUtil.getInputText("请输入原因：");
        UnfrozenApply apply = new UnfrozenApply(IdGenerator.generatorId(10),username,reason);
        Message<UnfrozenApply> msg = new Message<>("unfrozenApply",apply);
        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("解冻申请失败，请稍后重试~");
        } else if (result == 1) {
            System.out.println("解冻申请发送成功！");
        }else {
            System.out.println("账号未被冻结，无需申请~");
        }
    }
    /*
    * 退出
    * */
    public static void quit(){
        System.out.println("感谢使用影院选票系统~");
        System.exit(0);
    }
    /*
    * 查看订单
    * */
    public static void getOrderList(){

    }
    /*
    * 修改订单
    * */
    public static void updateOrder(){

    }
    /*
    * 取消订单
    * */
    public static void cancelOrder(){

    }
    /*
    * 审核订单
    * */
    public static void auditOrder(){

    }
    /*
    * 查看影片
    * */
    public static void getFilmList(){

    }
    /*
    * 增加影片
    * */
    public static void addFilm(){
        String name = InputUtil.getInputText("请输入影片名称：");
        String producer = InputUtil.getInputText("请输入制片人：");
        String description = InputUtil.getInputText("亲输入影片简介：");

        Film film = new Film(IdGenerator.generatorId(10),name,producer,description);

        Message<Film> msg = new Message<>("addFilm",film);

        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("添加失败，请稍后重试......");
        }else {
            System.out.println("添加成功！");
        }
    }
    /*
    * 修改影片
    * */
    public static void updateFilm(){
        String id = InputUtil.getInputText("请输入影片编号：");
        String name = InputUtil.getInputText("请输入影片名称：");
        String producer = InputUtil.getInputText("请输入制片人：");
        String description = InputUtil.getInputText("亲输入影片简介：");
        Film film = new Film(id,name,producer,description);
        Message<Film> msg = new Message<>("updateFilm",film);
        // result接收结果并判断
        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("修改失败QWQ......请稍后再试吧！");
        }else if (result == 1){ // 修改成功
            System.out.println("修改成功！");
        }else {
            System.out.println("未找到与\""+id+"\"相关的影片信息QWQ......");
        }
    }
    /*
    * 删除影片
    * */
    public static void deleteFilm(){
        String id = InputUtil.getInputText("请输入影片编号：");
        Message<String> msg = new Message<>("deleteFilm",id);
        Integer result = SocketUtil.sendMessage(msg);
        if (result == null || result == 0) {
            System.out.println("删除失败QWQ......请稍后再试吧！");
        }else if (result == 1){ // 修改成功
            System.out.println("删除成功！");
        }else {
            System.out.println("未找到与\""+id+"\"相关的影片信息QWQ......");
        }
    }
    /*
    * 查看影厅
    * */
    public static void getFilmHallList(){

    }
    /*
    * 增加影厅
    * */
    public static void addFilmHall(){

    }
    /*
    * 修改影厅
    * */
    public static void updateFilmHall(){

    }
    /*
    * 删除影厅
    * */
    public static void deleteFilmHall(){

    }
    /*
    * 查看播放计划
    * */
    public static void getFilmPlanList(){

    }
    /*
    * 增加播放计划
    * */
    public static void addFilmPlan(){

    }
    /*
    * 修改播放计划
    * */
    public static void updateFilmPlan(){

    }
    /*
    * 删除播放计划
    * */
    public static void deleteFilmPlan(){

    }
    /*
    * 查看用户
    * */
    public static void getUserList(){

    }
    /*
    * 冻结用户
    * */
    public static void frozenUser(){

    }
    /*
    * 解冻用户
    * */
    public static void unfrozenUser(){

    }
    /*
    * 查看解冻申请
    * */
    public static void getUnfrozenApplyList(){

    }
}

