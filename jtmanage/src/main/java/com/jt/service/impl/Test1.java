package com.jt.service.impl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2019/9/2.
 */
public class Test1 {
    public static void main(String[] args) {
        // TODO 输出最长对称字符串：goog
        String input1 = "abcndcbaefgh";

        // TODO 输出3个最长对称字符串：aba/aca/ada
        String input2 = "abcda";

        // TODO 输出2个最长对称字符串：pop/upu，不会输出特殊字符的对称字符串p-p
        String input3 = "pop-upu";

        //首先判断每个字符的个数
        HashMap<String,Integer> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        //去除特殊字符
        for (int i=0;i<input1.length();i++) {
            String a = input1.charAt(i)+"";
            if(!a.matches("^[a-z]")){
                input1.replace(a,"");
            }
        }
        //取一段首尾字符相等的字符串,得到所有对称的字符串,都存入map集合中,以字符串为key,以长度为value值
        for (int a=0;a<input1.length();a++){
            for (int b=a;b<input1.length();b++){
                if (input1.charAt(a)==input1.charAt(b)){//判断是否相同,相同则截取
                    String sub = input1.substring(a,b);
                    sub = sub+ input1.charAt(b);
                    find(sub,map);
                }
            }
        }
        //对map集合进行遍历,取出最长的对称字符串长度
        int avg = 0;
        for(String key:map.keySet()){
            if(map.get(key)>avg){
                avg=map.get(key);
            }
        }
        //把所有最长对称字符串存入list集合中
        for(String key:map.keySet()){
            if(map.get(key)==avg){
                list.add(key);
            }
        }
        //对集合遍历输出
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

    }
    public static void find(String b,HashMap<String,Integer> map){
        int small=1;
        int bie=b.length()-2;
        while (small<bie){//依次比较所截取的字符串内部的值,头尾是否相同
            if (b.charAt(small)!=b.charAt(bie)){//内部存在一对不同,则有多个最长对称字符串
                String middle = b.substring(small,bie+1);
                for (int i=0;i<middle.length();i++ ){//依次拼接存入map集合中,可以优化使用stringbuffer
                    String key = b.substring(0,small)+middle.charAt(i)+b.substring(bie+1);
                    Integer value = 2*small+1;
                    map.put(key,value);
                }
                return;
            }
            small++;
            bie--;
        }
        //截取的字符串内部都是对称的,就只有一个最长对称字符串.
        map.put(b,b.length());
    }
}
