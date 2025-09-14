package com.zsh.cinema.sys.task;


import com.zsh.cinema.sys.entity.*;
import com.zsh.cinema.sys.message.Message;
import com.zsh.cinema.sys.util.DateUtil;
import com.zsh.cinema.sys.util.FileUtil;
import com.zsh.cinema.sys.util.IdGenerator;
import com.zsh.cinema.sys.util.SocketUtil;

import java.net.Socket;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                    processGetFilmList(msg);
                    break;
                // 增加影厅
                case "addFilmHall":
                    processAddFilmHall(msg);
                    break;
                // 删除影厅
                case "deleteFilmHall":
                    processDeleteFilmHall(msg);
                    break;
                // 修改影厅
                case "updateFilmHall":
                    processUpdateFilmHall(msg);
                    break;
                // 查看影厅
                case "getFilmHallList":
                    processGetFilmHallList();
                    break;
                // 增加播放计划
                case "addFilmPlan":
                    processAddFilmPlan(msg);
                    break;
                // 删除播放计划
                case "deleteFilmPlan":
                    processDeleteFilmPlan(msg);
                    break;
                // 修改播放计划
                case "updateFilmPlan":
                    processUpdateFilmPlan(msg);
                    break;
                // 查看播放计划
                case "getFilmPlanList":
                    processGetFilmPlanList(msg);
                    break;
                // 获取用户列表
                case "getUserList":
                    processGetUserList();
                    break;
                // 冻结用户
                case "frozenUser":
                    processFrozenUser(msg);
                    break;
                // 解冻用户
                case "unfrozenUser":
                    processUnfrozenUser(msg);
                    break;
                // 查看用户解冻申请
                case "getUnfrozenApplyList":
                    processGetUnfrozenList();
                    break;
                // 查看用户订单（管理员）
                case "getOrderList":
                    processGetOrderList(msg);
                    break;
                // 查看用户订单（用户）
                case "getUserOrderList":
                    processGetUserOrderList(msg);
                    break;
                // 修改订单
                case "updateOrder":
                    processUpdateOrder(msg);
                    break;
                // 取消订单
                case "cancelOrder":
                    processCancelOrder(msg);
                    break;
                // 审核订单
                case "auditOrder":
                    processAuditOrder(msg);
                    break;
                // 在线订座
                case "orderSeatOnline":
                    processOrderSeatOnline(msg);
                    break;
            }
        }
    }

    /**
     * 功能：处理审核订单请求
     * @param msg
     */
    private void processAuditOrder(Message msg) {
        String orderId = (String) msg.getData();
        List<Order> orders = FileUtil.readData(FileUtil.ORDER_FILE);
        int index = -1;
        for (int i = 0; i < orders.size(); i++) {
            // 把每一个order拿出来看
            Order order = orders.get(i);
            if (order.getId().equals(orderId)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            SocketUtil.sendBack(client,-1);
        }else {
            // 找到了，改变订单状态
            Order order = orders.get(index);
            // 2-已退订
            order.setState(2);
            // 放回文件中
            orders.set(index,order);
            boolean success = FileUtil.saveData(orders,FileUtil.ORDER_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }
    }

    /**
     * 功能：处理用户取消订单
     * @param msg
     */
    private void processCancelOrder(Message msg) {
        String orderId = (String) msg.getData();
        List<Order> orders = FileUtil.readData(FileUtil.ORDER_FILE);
        int index = -1;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getId().equals(orderId)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            SocketUtil.sendBack(client,-1);
        }else {
            Order order = orders.get(index);
            if (order.getState() == 1){
                // 订单状态正常
                order.setState(0); // 更改订单状态为取消中
                orders.set(index,order);
                boolean success = FileUtil.saveData(orders,FileUtil.ORDER_FILE);
                SocketUtil.sendBack(client,success ? 1 : 0);
            }else { // 表示订单处于取消中或已退订
                SocketUtil.sendBack(client,-2);
            }
        }
    }

    /**
     * 功能：处理更新订单请求
     * @param msg
     */
    private void processUpdateOrder(Message msg) {

    }
    /**
     * 功能：处理查看用户订单请求
     */
    private void processGetUserOrderList(Message msg) {
        String username = (String) msg.getData();
        List<Order> orders = FileUtil.readData(FileUtil.ORDER_FILE);
        List<Order> result = orders.stream().filter(o->o.getOwner().equals(username)).toList();
        SocketUtil.sendBack(client,result);
    }

    /**
     * 功能：处理查看订单请求
     */
    private void processGetOrderList(Message msg) {
        Object data = msg.getData();
        List<Order> orders = FileUtil.readData(FileUtil.ORDER_FILE);
        if (data == null) {
            SocketUtil.sendBack(client,orders);
        }else {
            int state = (int) data;
            List<Order> orderList = orders.stream().filter(o->o.getState() == state).toList();
            SocketUtil.sendBack(client,orderList);
        }
    }

    /**
     * 功能：处理在线订座请求
     * @param msg
     */
    private void processOrderSeatOnline(Message msg) {
        Map<String,Object> data = (Map<String, Object>) msg.getData();
        String planId = (String) data.get("planId");
        int row = (int) data.get("row");
        int col = (int) data.get("col");
        String username = (String) data.get("username");
        List<FilmPlan> plans = FileUtil.readData(FileUtil.FILM_PLAN_FILE);
        int index = -1;
        for (int i = 0; i < plans.size(); i++) {
            FilmPlan plan = plans.get(i);
            if (plan.getId().equals(planId)) {
                index = i;
                break;
            }
        }
        if (index == -1){
            SocketUtil.sendBack(client,-1);
        }else {
            FilmPlan plan = plans.get(index);
            FilmHall hall = plan.getFilmHall();
            hall.setOwner(row,col,username);
            plan.setFilmHall(hall);
            plans.set(index,plan);
            boolean success = FileUtil.saveData(plans,FileUtil.FILM_PLAN_FILE);
            // 生成订单
            Order order = new Order();
            order.setId(IdGenerator.generatorId(10));
            order.setFilmName(plan.getFilm().getName());
            order.setOwner(username);
            order.setBegin(plan.getBegin());
            order.setEnd(plan.getEnd());
            String seatInfo = hall.getName() + "第"+row+"排第"+col+"列";
            order.setSeatInfo(seatInfo);
            // 保存订单
            // 先读取
            List<Order> orders = FileUtil.readData(FileUtil.ORDER_FILE);
            // 再写入
            orders.add(order);
            FileUtil.saveData(orders,FileUtil.ORDER_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }


/*
        Optional<FilmPlan> opt = plans.stream().filter(fp->fp.getId().equals(planId)).findFirst();
        // 其他一些情况不考虑，因为已经在客户端部分做了校验
        if (opt.isPresent()){
            FilmPlan plan = opt.get();
        }*/
    }

    /**
     * 功能：查看解冻申请
     */
    private void processGetUnfrozenList() {
        List<UnfrozenApply> applies = FileUtil.readData(FileUtil.UNFROZEN_APPLY_FILE);
        SocketUtil.sendBack(client,applies);
    }

    /**
     * 处理用户解冻申请
     * @param msg
     */
    private void processUnfrozenUser(Message msg) {
        Map<String,Object> data = (Map<String, Object>) msg.getData();
        String id = (String) data.get("id");
        int number = (Integer) data.get("number");
        List<UnfrozenApply> applies = FileUtil.readData(FileUtil.UNFROZEN_APPLY_FILE);
        int index = -1;
        for (int i = 0; i < applies.size(); i++) {
            UnfrozenApply apply = applies.get(i);
            if (apply.getId().equals(id)){
                index = i;
                break;
            }
        }
        if (index == -1) {
            // -2 解冻申请编号不存在
            SocketUtil.sendBack(client,-2);
        }else {
            UnfrozenApply apply = applies.get(index);
            int state = apply.getState();
            if (state == 0){ // 待处理
                apply.setState(number);
                // 拿出已处理的账号
                List<User> users = FileUtil.readData(FileUtil.USER_FILE);
                int userIndex = -1;
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    // 如果该用户账号存在
                    if (user.getUsername().equals(apply.getUsername())) {
                        userIndex = i;
                        break;
                    }
                    // 如果不存在，操作比较麻烦，先不写（
                }
                if (number == 1) {
                    User user = users.get(userIndex);
                    user.setState(1);
                    users.set(userIndex , user);
                    FileUtil.saveData(users,FileUtil.USER_FILE);
                }
                applies.set(index,apply);
                boolean success = FileUtil.saveData(applies,FileUtil.UNFROZEN_APPLY_FILE);
                SocketUtil.sendBack(client,success ? 1 : 0);
            }else { // -1 已经处理过
                SocketUtil.sendBack(client,-1);
            }
        }
        Optional<UnfrozenApply> opt = applies.stream().filter(unfrozenApply -> unfrozenApply.getId().equals(id)).findFirst();
        if (opt.isPresent()) {
            UnfrozenApply apply = opt.get();

        }else {
        }
    }

    /**
     * 功能：冻结用户
     * @param msg
     */
    private void processFrozenUser(Message msg) {
        String username = (String) msg.getData();
        List<User> users = FileUtil.readData(FileUtil.USER_FILE);
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (username.equals(user.getUsername())) {
                index = i;
                break;
            }
        }
        if (index == -1) { // 账号不存在
            SocketUtil.sendBack(client,-1);
        }else {
            User user = users.get(index);
            if (user.getState() == 1) {
                user.setState(0); // 设置账号为冻结状态
                users.set(index,user); // 把改为冻结账号的账号放回存储文件中
                boolean success = FileUtil.saveData(users,FileUtil.USER_FILE);
                SocketUtil.sendBack(client,success ? 1:0);
            }else { // 被冻结
                SocketUtil.sendBack(client,-2);
            }
        }
    }

    /**
     * 功能：查询用户列表
     */
    private void processGetUserList() {
        List<User> users = FileUtil.readData(FileUtil.USER_FILE);
        SocketUtil.sendBack(client,users);
    }

    /**
     * 功能：处理查看播放计划请求
     * @param msg
     */
    private void processGetFilmPlanList(Message msg) {
        String filmName = (String) msg.getData();
        List<FilmPlan> plans = FileUtil.readData(FileUtil.FILM_PLAN_FILE);
        if(filmName == null || "".equals(filmName)){
            SocketUtil.sendBack(client, plans);
        } else {
            List<FilmPlan> result = plans.stream().filter(fp->{
                Film film = fp.getFilm();
                return film.getName().contains(filmName) || filmName.contains(film.getName());
            }).collect(Collectors.toList());
            SocketUtil.sendBack(client, result);
        }
    }
    /**
     * 功能：处理更新播放计划请求
     * @param msg
     */
    private void processUpdateFilmPlan(Message msg) {
        FilmPlan plan = (FilmPlan) msg.getData();
        List<FilmPlan> plans = FileUtil.readData(FileUtil.FILM_PLAN_FILE);
        int index = -1;
        for (int i = 0; i < plans.size(); i++) {
            FilmPlan fp = plans.get(i);
            if (plan.getId().equals(fp.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) { // 更新的播放计划不存在
            SocketUtil.sendBack(client,-2);
        }else {
            //先将原来的播放计划移除，然后再检测时间是否冲突
            FilmPlan remove = plans.remove(index);
            boolean conflict = plans.stream().anyMatch(filmPlan -> DateUtil.isConflictPlan(plan,filmPlan));
            if (conflict) {
                SocketUtil.sendBack(client,-1);
            }else {
                plan.setFilm(remove.getFilm());
                plan.setFilmHall(remove.getFilmHall());
                plans.add(plan);
                boolean success = FileUtil.saveData(plans,FileUtil.FILM_PLAN_FILE);
                SocketUtil.sendBack(client,success ? 1 : 0);
            }
        }
    }
    /**
     * 功能：处理删除播放计划请求
     * @param msg
     */
    private void processDeleteFilmPlan(Message msg) {
        String planId = (String) msg.getData();
        List<FilmPlan> plans = FileUtil.readData(FileUtil.FILM_PLAN_FILE);
        int index = -1;
        for(int i=0; i<plans.size(); i++){
            FilmPlan fp = plans.get(i);
            if(planId.equals(fp.getId())){
                index = i;
                break;
            }
        }
        if(index == -1){
            SocketUtil.sendBack(client, -1);
        } else {
            plans.remove(index);
            boolean success = FileUtil.saveData(plans, FileUtil.FILM_PLAN_FILE);
            SocketUtil.sendBack(client, success ? 1 : 0);
        }
    }

    /**
     * 功能：处理添加播放计划请求
     * @param msg
     */
    private void processAddFilmPlan(Message msg) {
        FilmPlan plan = (FilmPlan) msg.getData();
        List<FilmPlan> plans = FileUtil.readData(FileUtil.FILM_PLAN_FILE);
        boolean conflict = plans.stream().anyMatch(fp-> DateUtil.isConflictPlan(plan,fp));
        if (conflict) { // 播放时间冲突
            SocketUtil.sendBack(client,-1);
        }else {
            plans.add(plan);
            boolean success = FileUtil.saveData(plans,FileUtil.FILM_PLAN_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }
    }

    /**
     * 功能：处理查看影厅请求
     */
    private void processGetFilmHallList() {
        List<FilmHall> halls = FileUtil.readData(FileUtil.FILM_HALL_FILE);
        SocketUtil.sendBack(client,halls);
    }
    /**
     * 功能：处理修改影厅请求
     * @param msg
     */
    private void processUpdateFilmHall(Message msg) {
        FilmHall updateFilmHall = (FilmHall) msg.getData();
        List<FilmHall> halls = FileUtil.readData(FileUtil.FILM_HALL_FILE);

        int index = -1;
        for (int i = 0; i < halls.size(); i++) {
            FilmHall hall = halls.get(i);
            if (updateFilmHall.getId().equals(hall.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) { // 说明修改的影厅信息不存在
            SocketUtil.sendBack(client,-1);
        }else {
            halls.set(index,updateFilmHall);
            boolean success = FileUtil.saveData(halls,FileUtil.FILM_HALL_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }
    }
    /**
     * 功能：处理删除影厅请求
     * @param msg
     */
    private void processDeleteFilmHall(Message msg) {
        String id = (String) msg.getData();
        List<FilmHall> halls = FileUtil.readData(FileUtil.FILM_HALL_FILE);

        int index = -1;
        for (int i = 0; i < halls.size(); i++) {
            FilmHall hall = halls.get(i);
            if (id.equals(hall.getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) { // 说明删除的影厅信息不存在
            SocketUtil.sendBack(client,-1);
        }else {
            halls.remove(index);
            boolean success = FileUtil.saveData(halls,FileUtil.FILM_HALL_FILE);
            SocketUtil.sendBack(client,success ? 1 : 0);
        }
    }

    /**
     * 功能：处理添加影厅请求
     * @param msg
     */
    private void processAddFilmHall(Message msg) {
        // 从客户端获取管理员添加的影厅影厅信息
        FilmHall hall = (FilmHall) msg.getData();
        // 读取存储在data文件夹里的影厅信息
        List<FilmHall> filmHalls = FileUtil.readData(FileUtil.FILM_HALL_FILE);
        // 将影片添加到影厅列表
        filmHalls.add(hall);
        // 保存修改后的影厅列表
        boolean success = FileUtil.saveData(filmHalls,FileUtil.FILM_HALL_FILE);
        SocketUtil.sendBack(client,success ? 1 : 0 );
    }

    /*
     * 功能：处理查看影片列表请求
     * 参数：
     * 返回值：
     * */
    private void processGetFilmList(Message msg) {
        String name = (String) msg.getData();
        List<Film> films = FileUtil.readData(FileUtil.FILM_FILE);
        if (name == null || "".equals(name)) {
            // 如果所查找的影片名称不存在或为空，返回data文件夹内的影片列表
            SocketUtil.sendBack(client,films);
        }else {
            // 最后面的toList()可能出bug，只是可能啊，也不一定
            List<Film> result = films.stream().filter(film -> film.getName().contains(name) || name.contains(film.getName())).toList();
            SocketUtil.sendBack(client,result);
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
                    //
                    result.put("user", user);
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
