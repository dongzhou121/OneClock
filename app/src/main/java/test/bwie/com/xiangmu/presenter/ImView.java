package test.bwie.com.xiangmu.presenter;

/**
 * Created by 董洲 on 2017/11/28.
 */

public interface ImView {
    void onSuccess(String tag, Object o);
    void onFailed(String tag, Exception e);
}
