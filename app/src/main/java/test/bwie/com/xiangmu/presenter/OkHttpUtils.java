package test.bwie.com.xiangmu.presenter;

import android.os.Handler;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 董洲 on 2017/12/7.
 */

public class OkHttpUtils {
    private Handler handler = new Handler();
    public Handler getHandler(){
        return handler;
    }
    //单例
    private static OkHttpUtils okHttpUtils = new OkHttpUtils();
    private OkHttpUtils(){};
    public static OkHttpUtils getInstance(){
        return okHttpUtils;
    }
    private OkHttpClient client;
    //
    private  OkHttpClient getOkHttpClient() {
        synchronized (OkHttpUtils.class) {
            if (client == null) {
                client = new OkHttpClient();
            }
        }
        return client;
    }
    //公用的get请求方法  完成的功能不确定
    public void doGet(String url, Callback callback){
        OkHttpClient okHttpClient = getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
    public  void doPost(String url, Map<String,String> map, Callback callback){
        OkHttpClient okHttpClient = getOkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合   设置请求体
        for (String mapKey : map.keySet()){
            builder.add(mapKey,map.get(mapKey));
        }
        //设置请求方式
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        //执行请求方式    接口回调
        okHttpClient.newCall(request).enqueue(callback);
    }
    /**
     *1.下载地址
     */
    public  void doDown(String url,Callback callback){
        OkHttpClient okHttpClient = getOkHttpClient();
        Request build = new Request.Builder().url(url).build();
        okHttpClient.newCall(build).enqueue(callback);
    }
}

