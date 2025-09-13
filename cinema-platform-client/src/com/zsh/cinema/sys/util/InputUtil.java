package com.zsh.cinema.sys.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/*
* 输入工具类
* */
public class InputUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    /*
    * 获取从控制台获取给定范围内的整数
    *
    * */

    public static int getInputInteger(String tip,int min,int max){
        System.out.println(tip);

        while (true){
            if (SCANNER.hasNextInt()){
                int number = SCANNER.nextInt();
                if (number >= min && number <= max){
                    return number;
                }else {
                    System.out.printf("输入错误，请输入%d~%d范围内的数字\n",min,max);
                }
            }else {
                System.out.printf("输入错误，请输入%d~%d范围内的数字\n",min,max);
                SCANNER.next();// 关键：清除输入缓冲区中的非整数输入
            }
        }
    }
    /*
    * 从控制台获取一个字符串
    * */
    public static String getInputText(String tip){
        System.out.println(tip);
        // 获取用户输入并返回
        return SCANNER.next();
    }
    /*
    * 从控制台获取一个日期
    * */
    public static Date getInputDate(String tip){
        System.out.println(tip);
        while (true){
            String dateStr = SCANNER.nextLine();
            try {
                return DateUtil.str2Date(dateStr);
            } catch (ParseException e) {
                System.out.println("输入错误，请重新输入~，日期格式为：yyyy-MM-dd HH:mm:ss");
            }
        }
    }


}
