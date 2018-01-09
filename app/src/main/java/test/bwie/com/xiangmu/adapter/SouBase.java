package test.bwie.com.xiangmu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import test.bwie.com.xiangmu.R;
import test.bwie.com.xiangmu.bean.SouSuoBean;

/**
 * Created by 董洲 on 2017/11/28.
 */

public class SouBase extends BaseAdapter {
    private Context context;
    private List<SouSuoBean.DataBean> list;

    public SouBase(Context context, List<SouSuoBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view==null){
            view=View.inflate(context, R.layout.item_sou,null);
            holder=new ViewHolder();
            holder.tv=(TextView) view.findViewById(R.id.item_sou_text);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Log.d("main",list.get(i).getTitle());
        holder.tv.setText(list.get(i).getTitle());
        return view;
    }
    class ViewHolder{
        TextView tv;
    }
}
