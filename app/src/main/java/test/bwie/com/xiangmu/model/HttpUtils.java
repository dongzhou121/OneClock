package test.bwie.com.xiangmu.model;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import test.bwie.com.xiangmu.presenter.CallBack;

/**
 * Created by 董洲 on 2017/11/28.
 */

public class HttpUtils {
    private static final String TAG = "HttpUtils";
    private static volatile HttpUtils instance;

    Handler handler=new Handler();

    public HttpUtils() {
    }

    public static HttpUtils getInstance(){
        if(null==instance){
            synchronized (HttpUtils.class){
                if(instance==null){
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void get(String url, Map<String,String> map,
                    final CallBack callBack, final Class cla, final String tag){

        if(TextUtils.isEmpty(url)){
            return;
        }

        StringBuffer sb=new StringBuffer();
        sb.append(url);

        if(!map.isEmpty()){
            Log.i(TAG, "get: url="+"开始拼接+++++++++++++++++");
            if(url.contains("?")){
                if(url.indexOf("?")==url.length()-1){
                }else {
                    sb.append("&");
                }
            }else {
                sb.append("?");
            }

            for (Map.Entry<String,String> entry:map.entrySet()){
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            if(sb.indexOf("&")!=-1){
                sb.deleteCharAt(sb.lastIndexOf("&"));
            }
        }



        Log.i(TAG, "get: url="+sb);

        OkHttpClient client=new OkHttpClient.Builder().
                addInterceptor(new Logger()).build()
                ;

        final Request request=new Request.Builder()
                .get()
                .url(sb.toString())
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(tag,e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, "onResponse: result--"+result);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Object o;
                        if (TextUtils.isEmpty(result)) {
                            o = null;
                        } else {
                            o = GsonUtils.getInstance().fromJson(result, cla);
                        }
                        //Log.e(TAG, "onResponse: bean--"+);
                        callBack.onSuccess(tag, o);
                    }
                });
            }
        });

    }

}
