package com.zsh.cinema.sys.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
* 文件操作工具类
* */
public class FileUtil {
    /*
    * 用户存档文件
    * */
    public static final String USER_FILE = "data/user.obj";
    /*
    * 影片存档文件
    * */
    public static final String FILM_FILE = "data/film.obj";
    /*
    * 播放计划存档文件
    * */
    public static final String FILM_PLAN_FILE = "data/filmPlan.obj";
    /*
    * 影厅存档文件
    * */
    public static final String FILM_HALL_FILE = "data/filmHall.obj";
    /*
    * 订单存档文件
    * */
    public static final String ORDER_FILE = "data/order.obj";
    /*
    * 解冻申请存档文件
    * */
    public static final String UNFROZEN_APPLY_FILE = "data/unfrozenApply.obj";

    /*
    * 功能：保存给定的列表数据至给定的路径文件中
    * 参数：
    *   dataList：列表数据
    *   path：路径
    *   <T>：因为不清楚（列表中的）保存的具体数据类型，故使用泛型
    * 返回值：
    *
    * */
    public static <T> boolean saveData(List<T> dataList,String path) {
        File file = new File(path);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try (OutputStream os = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(os)){
                oos.writeObject(dataList);
                oos.flush();
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /*
     * 功能：从给定路径的文件中读取数据
     * 参数：
     *   path：路径
     *   <T>：因为不清楚（列表中的）保存的具体数据类型，故使用泛型
     * 返回值：
     *
     * */
    public static <T> List<T> readData(String path){
        try (InputStream is = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(is)){
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            // 修改过，可能与注册时无法连接服务端的bug有关
            throw new RuntimeException();
//            e.printStackTrace();
//            return new ArrayList<>();
        }
    }
}
