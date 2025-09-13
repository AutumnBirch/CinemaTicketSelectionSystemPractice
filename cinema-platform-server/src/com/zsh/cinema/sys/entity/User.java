package com.zsh.cinema.sys.entity;


/*
ER图 Entity Relational

实体包常用包名：

    entity 实体
    pojo(plain ordinary java object) 简单java对象
    bo(business object) 业务对象
    vo(view object) 视图对象
    dto(data transferobject) 数据传输对象
    domain 领域模型
    model 数据模型

*/


import java.io.Serializable;

/*
*用户
* */
public class User implements Serializable {
    /*
    * 账号
    * */
    private String username;
    /*
    * 密码
    * */
    private String password;
    /*
    * 安全码（用于找回账户）
    * */
    private String securityCode;
    /*
    * 管理员
    * */
    private boolean manager;
    /*
    * 账号状态：-1-审核不通过，0-待审核，1-正常，2-冻结
    * */
    private int state = 1;

    public User() {
    }

    public User(String username, String password, String securityCode) {
        this.username = username;
        this.password = password;
        this.securityCode = securityCode;
    }

    public User(String username, String password, String securityCode, boolean manager, int state) {
        this.username = username;
        this.password = password;
        this.securityCode = securityCode;
        this.manager = manager;
        this.state = state;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return securityCode
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * 设置
     * @param securityCode
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * 获取
     * @return manager
     */
    public boolean isManager() {
        return manager;
    }

    /**
     * 设置
     * @param manager
     */
    public void setManager(boolean manager) {
        this.manager = manager;
    }

    /**
     * 获取
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * 设置
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    public String toString() {
        // 身份
        String identity = manager ? "管理员" : "普通用户";
        // 状态
        String s = state == 1 ? "正常" : "冻结";
        return username + "\t" + identity + "\t" + s;
    }
}
