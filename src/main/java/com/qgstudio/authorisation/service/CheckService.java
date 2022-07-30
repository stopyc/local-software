package com.qgstudio.authorisation.service;

import com.qgstudio.authorisation.client.TaskClient;

import java.util.ResourceBundle;

/**
 * @program: local-software
 * @description:
 * @author: stop.yc
 * @create: 2022-07-30 09:46
 **/
public class CheckService {
    public static int version_id;
    public static int software_id;
    public static int function_type;


    static {
        ResourceBundle bundle = ResourceBundle.getBundle("system".trim());
        version_id = Integer.parseInt(bundle.getString("version_id"));
        software_id = Integer.parseInt(bundle.getString("software_id"));
        function_type = Integer.parseInt(bundle.getString("function_type"));
    }

   public boolean check() {
       return new TaskClient().check(software_id, version_id, function_type);
   }

}
