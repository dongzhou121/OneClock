package test.bwie.com.xiangmu.presenter;


import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by 董洲 on 2017/12/7.
 */

public abstract  class OnUiCallback implements Callback {
    private Handler handler = OkHttpUtils.getInstance().getHandler();
    public abstract void onFailed(Call call, IOException e);
    public abstract void onSuccess(String result) throws IOException;
    @Override
    public void onFailure(final Call call,final IOException e) {
        //该方式  存在问题  网络请求也跑到了主线程   待解决
        //该方法就是把  线程post到handler所在的线程
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(call, e);
            }
        });
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        //该方式  存在问题  网络请求也跑到了主线程   待解决
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
