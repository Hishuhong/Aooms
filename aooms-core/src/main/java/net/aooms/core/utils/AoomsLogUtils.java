package net.aooms.core.utils;

/**
 * 日志打印
 * Created by cccyb on 2018-03-06
 */
public class AoomsLogUtils {

    public static String errorLogFormat(String errorMsg){
        return "AoomsErrorMsg >> " + errorMsg;
    }

    public static String logFormat(String msg){
        return "AoomsMsg >> " + msg;
    }

}