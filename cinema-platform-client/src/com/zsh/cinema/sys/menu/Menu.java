package com.zsh.cinema.sys.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    /*
    * 序号
    * */
    private int order;
    /*
    * 名称
    * */
    private String name;
    /*
    * 触发的行为
    * */
    private String action;
    /*
    * 子菜单列表
    *
    * */
    private List<Menu> children = new ArrayList<>();
    /*
    * 父菜单
    * */
    private Menu parent;
    /*
    * 三参数构造方法，用于创建顶级菜单（没有父菜单的菜单，创建menu对象时传入三个参数即可）
    *
    * 这玩意是常见的构造方法重载设计模式：
    * 便携性：提供多个构造方法，方便在不同场景下使用，避免每次都传入所有参数，
    * 可读性：根据参数数量和类型，选择合适的构造方法，避免歧义和错误使用。
    * 向后兼容性：如果后续需要添加新的参数，不影响旧的代码使用，只需要添加新的构造方法即可。
    * */
    public Menu(int order, String name, String action) {
        this(order, name, action, null);
    }
    /*
     * 四参数构造方法，用于创建子菜单（有父菜单的菜单，创建menu对象时传入四个参数即可）
     * */
    public Menu(int order, String name, String action, Menu parent) {
        this.order = order;
        this.name = name;
        this.action = action;
        this.parent = parent;
    }
    // 无参构造方法（用于框架反射等场景）
    public Menu() {
    }
    // 全参构造方法（用于手动创建菜单对象）
    public Menu(int order, String name, String action, List<Menu> children, Menu parent) {
        this.order = order;
        this.name = name;
        this.action = action;
        this.children = children;
        this.parent = parent;
    }

    /*
    * 添加子菜单
    * */
    public void addChild(Menu child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return order+"."+name;
    }

    /**
     * 获取
     * @return order
     */
    public int getOrder() {
        return order;
    }

    /**
     * 设置
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * 设置
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 获取
     * @return children
     */
    public List<Menu> getChildren() {
        return children;
    }

    /**
     * 设置
     * @param children
     */
    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    /**
     * 获取
     * @return parent
     */
    public Menu getParent() {
        return parent;
    }

    /**
     * 设置
     * @param parent
     */
    public void setParent(Menu parent) {
        this.parent = parent;
    }
}
