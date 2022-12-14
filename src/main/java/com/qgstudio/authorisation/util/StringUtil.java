package com.qgstudio.authorisation.util;


import java.util.Random;

/**
 * @program: qg
 * @description: 字符串工具类
 * @author: stop.yc
 * @create: 2022-04-17 11:49
 **/
public class StringUtil {

    /**
     * @Description: 判断字符串是否为空
     * @Param: [str]
     * @return: boolean
     * @Author: stop.yc
     * @Date: 2022/4/17
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * @Description: 判断字符串是否不为空
     * @Param: [str]
     * @return: boolean
     * @Author: stop.yc
     * @Date: 2022/4/17
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str.trim());
    }

    /**
    * @Description: 返回一个没有空格的字符串
    * @Param: [str]
    * @return: java.lang.String
    * @Author: stop.yc
    * @Date: 2022/4/17
    */
    public static  String getTrimStr(String str) {
        //空的话
        if (isEmpty(str)) {
            return "";
        }
        return str.trim();
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
