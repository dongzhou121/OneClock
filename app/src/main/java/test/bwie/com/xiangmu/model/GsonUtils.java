package test.bwie.com.xiangmu.model;

import com.google.gson.Gson;

/**
 * Created by 董洲 on 2017/11/28.
 */

public class GsonUtils {
    private static Gson gson;
    public static Gson getInstance(){
        if(gson==null){
            gson=new Gson();
        }
        return gson;
    }
}
