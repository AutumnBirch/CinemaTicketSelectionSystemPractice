package com.zsh.cinema.sys.starter;

import com.zsh.cinema.sys.action.UserAction;
import com.zsh.cinema.sys.entity.FilmHall;
import com.zsh.cinema.sys.entity.User;
import com.zsh.cinema.sys.menu.Menu;
import com.zsh.cinema.sys.menu.MenuManager;
import com.zsh.cinema.sys.util.InputUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
* 影院客户端
* */
public class CinemaClient {
    // 当前登录用户是否为管理员
    private static User currentUser;

    public static void main(String[] args) {
        showInterface(MenuManager.LOGIN_MENUS);
    }
    /*
    * 展示界面
    * */
    private static void showInterface(Menu[] menus) {
        MenuManager.showMenu(menus);
        int number = InputUtil.getInputInteger("请选择菜单编号：",1,menus.length);
        Menu select = menus[number-1];
        switch (select.getAction()){
            case "login":
                Map<String, Object> result = UserAction.login();
                if (result == null) { // 登录失败（IO异常/网络不通/登录失败）
                    System.out.println("账号或密码错误，请稍后重试......");
                    showInterface(MenuManager.LOGIN_MENUS);
                }else {
                    int process = (int)result.get("process");
                    if (process == 1) { // 登录成功
                        // 记录当前用户
                        currentUser = (User) result.get("manager");
                        Menu[] mainMenu = currentUser.isManager() ? MenuManager.MANAGER_MENUS : MenuManager.USER_MENUS;
                        showInterface(mainMenu);
                    } else {
                        String msg;
                        if (process == 0) { // 账号或密码错误
                            msg = "账号或密码错误，请稍后重试......";
                        } else if (process == -1) { // 账号不存在
                            msg = "账号不存在，请先注册哦~~~";
                        }else { // 账号已被冻结
                            msg = "账号已被冻结，请申请解冻哦~";
                        }
                        System.out.println(msg);
                        showInterface(MenuManager.LOGIN_MENUS);
                    }
                }
                break;
            case "register":
                UserAction.register();
                showInterface(MenuManager.LOGIN_MENUS);
                break;
            case "getPasswordBack":
                UserAction.getPasswordBack();
                showInterface(MenuManager.LOGIN_MENUS);
                break;
            case "unfrozenApply":
                UserAction.unfrozenApply();
                showInterface(MenuManager.LOGIN_MENUS);
                break;
            case "showChildren":
                List<Menu> children = select.getChildren();
                Menu[] childMenus = children.toArray(new Menu[children.size()]);
                showInterface(childMenus);
                break;
            case "goBackLogin":
                showInterface(MenuManager.LOGIN_MENUS);
                break;
            case "goBackMain":
                showInterface(currentUser.isManager() ? MenuManager.MANAGER_MENUS : MenuManager.USER_MENUS);
                break;
                // 增加影片
            case "addFilm":
                UserAction.addFilm();
                showSiblingMenus(select);
                break;
                // 删除影片
            case "deleteFilm":
                UserAction.deleteFilm();
                showSiblingMenus(select);
                break;
                // 修改影片
            case "updateFilm":
                UserAction.updateFilm();
                showSiblingMenus(select);
                break;
                // 查看影片
            case "getFilmList":
                UserAction.getFilmList();
                showSiblingMenus(select);
                break;
                // 增加影厅
            case "addFilmHall":
                UserAction.addFilmHall();
                showSiblingMenus(select);
                break;
                // 删除影厅
            case "deleteFilmHall":
                UserAction.deleteFilmHall();
                showSiblingMenus(select);
                break;
                // 修改影厅
            case "updateFilmHall":
                UserAction.updateFilmHall();
                showSiblingMenus(select);
                break;
                // 查看影厅
            case "getFilmHallList":
                UserAction.getFilmHallList();
                showSiblingMenus(select);
                break;
                // 增加播放计划
            case "addFilmPlan":
                UserAction.addFilmPlan();
                showSiblingMenus(select);
                break;
                // 删除播放计划
            case "deleteFilmPlan":
                UserAction.deleteFilmPlan();
                showSiblingMenus(select);
                break;
                // 修改播放计划
            case "updateFilmPlan":
                UserAction.updateFilmPlan();
                showSiblingMenus(select);
                break;
                // 查看播放计划
            case "getFilmPlanList":
                UserAction.getFilmPlanList();
                showSiblingMenus(select);
                break;
                // 获取用户列表
            case "getUserList":
                UserAction.getUserList();
                showSiblingMenus(select);
                break;
                // 冻结用户
            case "frozenUser":
                UserAction.frozenUser();
                showSiblingMenus(select);
                break;
                // 解冻用户
            case "unfrozenUser":
                UserAction.unfrozenUser();
                showSiblingMenus(select);
                break;
                // 查看用户解冻申请
            case "getUnfrozenApplyList":
                UserAction.getUnfrozenApplyList();
                showSiblingMenus(select);
                break;
                // 查看用户订单（管理员）
            case "getOrderList":
                UserAction.getOrderList();
                showSiblingMenus(select);
                break;
                // 查看用户订单（用户）
            case "getUserOrderList":
                UserAction.getUserOrderList();
                showSiblingMenus(select);
                break;
                // 修改订单
            case "updateOrder":
                UserAction.updateOrder();
                showSiblingMenus(select);
                break;
                // 取消订单
            case "cancelOrder":
                UserAction.cancelOrder();
                showSiblingMenus(select);
                break;
                // 审核订单
            case "auditOrder":
                UserAction.auditOrder();
                showSiblingMenus(select);
                break;
                // 在线订座
            case "orderSeatOnline":
                UserAction.orderSeatOnline(currentUser.getUsername());
                showSiblingMenus(select);
                break;
                // 退出系统
            case "quit":
                UserAction.quit();
                break;
/*            default: // 其他子菜单操作，需要重新展示与该子菜单同级的子菜单列表
                showSiblingMenus(select);*/

/*                Menu parent = select.getParent();
                List<Menu> menuList = parent.getChildren();
                Menu[] menus1 = menuList.toArray(new Menu[menuList.size()]);
                showInterface(menus1);
                break;*/
        }
    }
    /*
    * 功能：展示与所选菜单同级的子菜单列表
    *   通过参数获取select的父菜单，再通过父菜单，获取它的子菜单，即与select同级的菜单
    * 参数：select
    * 返回值：void
    * */
    private static void showSiblingMenus(Menu select) {
        Menu parent = select.getParent();
        List<Menu> children = parent.getChildren();
        Menu[] menus = children.toArray(new Menu[children.size()]);
        showInterface(menus);
    }




    // 下面的代码已经抽象为上方的 showInterface() 方法，留着方便回顾思路

    /*private static void showLoginMenu(){
        MenuManager.showMenu(MenuManager.LOGIN_MENUS);
        int number = InputUtil.getInputInteger("请选择菜单编号：",1,MenuManager.LOGIN_MENUS.length);
        Menu select = MenuManager.LOGIN_MENUS[number-1];
        switch (select.getAction()){
            case "register":
                UserAction.register();
                showLoginMenu();
                break;
            case "login":
                UserAction.login();
                showMainMenu();
                break;
            case "getPasswordBack":
                UserAction.getPasswordBack();
                showLoginMenu();
                break;
            case "unfrozenApply":
                UserAction.unfrozenApply();
                showLoginMenu();
                break;
            case "quit":
                UserAction.quit();
                break;
        }
    }

    public static void showMainMenu(){
        MenuManager.showMenu(MenuManager.USER_MENUS);
        int number = InputUtil.getInputInteger("请选择菜单编号：",1,MenuManager.USER_MENUS.length);
        Menu select = MenuManager.USER_MENUS[number-1];
        switch (select.getAction()){
            // 显示菜单
            case "showChildren":
                showChildren(select);
                break;
            case "goBackLogin":
                showLoginMenu();
                break;
        }
    }

    public static void showChildren(Menu parent) {
        List<Menu> children = parent.getChildren();
        Menu[] menus = children.toArray(new Menu[children.size()]);
        MenuManager.showMenu(menus);
        int number = InputUtil.getInputInteger("请选择菜单编号：",1,menus.length);
        Menu select = menus[number-1];
        switch (select.getAction()){
            case "goBackMain":
                showMainMenu();
                break;
            default:
                showChildren(parent);
                break;
        }
    }*/
}
