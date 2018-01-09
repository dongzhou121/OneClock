package test.bwie.com.xiangmu.presenter;

import java.util.Map;

import test.bwie.com.xiangmu.model.HttpUtils;

/**
 * Created by 董洲 on 2017/11/28.
 */

public class Presenter {
    private ImView inv;

    public void  attachView(ImView inv){
        this.inv=inv;
    }

    public void get(String url, Map<String,String> map, String tag, Class clv){
        HttpUtils.getInstance().get(url, map, new CallBack() {
            @Override
            public void onSuccess(String tag, Object o) {
                if(o!=null){
                    inv.onSuccess(tag,o);
                }
            }

            @Override
            public void onFailed(String tag, Exception e) {
                inv.onFailed(tag,e);
            }
        },clv,tag);
    }
    public void deleteView(){
        if(inv!=null){
            inv=null;
        }
    }
}
