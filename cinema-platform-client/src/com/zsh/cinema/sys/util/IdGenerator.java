package com.zsh.cinema.sys.util;

import java.util.Random;

/*
* id生成器
* */
public class IdGenerator {

    private static Random RANDOM = new Random();

    private static final char[] characters = {
            'A','B','C','D','E','F','G','H','I','J','K','L',
            'M','N','O','P','Q','R','S','T','U','V','W','X',
            'Y','Z','0','1','2','3','4','5','6','7','8','9'
    };

    /*
    * 根据给定的字符串生成id
    * */

    public static String generatorId(int length){
        StringBuilder sb = new StringBuilder("ZSH_");
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(characters.length);
            sb.append(characters[index]);
        }
        return sb.toString();
    }

}
