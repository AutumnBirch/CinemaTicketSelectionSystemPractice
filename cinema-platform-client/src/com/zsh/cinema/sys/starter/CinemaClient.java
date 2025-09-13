package com.zsh.cinema.sys.starter;

import com.zsh.cinema.sys.action.UserAction;
import com.zsh.cinema.sys.menu.Menu;
import com.zsh.cinema.sys.menu.MenuManager;
import com.zsh.cinema.sys.util.InputUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/*
* 影院客户端
* */
public class CinemaClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        showInterface(MenuManager.LOGIN_MENUS);
//        showLoginMenu();
    }
    /*
    * 展示界面
    * */
    private static void showInterface(Menu[] menus) {
        MenuManager.showMenu(menus);
        int number = InputUtil.getInputInteger("请选择菜单编号：",1,menus.length);
        Menu select = menus[number-1];
        switch (select.getAction()){
            case "showChildren":
                List<Menu> children = select.getChildren();
                Menu[] childMenus = children.toArray(new Menu[children.size()]);
                showInterface(childMenus);
                break;
            case "goBackLogin":
                showInterface(MenuManager.LOGIN_MENUS);
                break;
            case "login":
                UserAction.login();
                showInterface(MenuManager.USER_MENUS);
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
            case "goBackMain":
                showInterface(MenuManager.USER_MENUS);
                break;
            case "quit":
                UserAction.quit();
                break;
            default: // 其他子菜单操作，需要重新展示与该子菜单同级的
                Menu parent = select.getParent();
                List<Menu> menuList = parent.getChildren();
                Menu[] menus1 = menuList.toArray(new Menu[menuList.size()]);
                showInterface(menus1);
                break;
        }
    }



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
