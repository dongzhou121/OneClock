package test.bwie.com.xiangmu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import test.bwie.com.xiangmu.R;
import test.bwie.com.xiangmu.bean.DatarightBean;
import test.bwie.com.xiangmu.bean.DateGridBean;
import test.bwie.com.xiangmu.utils.API;
import test.bwie.com.xiangmu.utils.GsonObjectCallback;
import test.bwie.com.xiangmu.utils.OkHttp3Utils;
import test.bwie.com.xiangmu.view.MyGridView;

/**
 * 董洲
 */

public class  MyAdapter_right extends RecyclerView.Adapter<MyAdapter_right.MyLeftViewHolder>{
    private Context context;
    private List<DatarightBean.DatasBean.ClassListBean> list;

    public MyAdapter_right(Context context, List<DatarightBean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.typeson_item, parent, false);
        final MyLeftViewHolder leftViewHolder = new MyLeftViewHolder(view);
        return leftViewHolder;
    }

    @Override
    public void onBindViewHolder(MyLeftViewHolder holder, int position) {
        //设置种类标题
        final MyLeftViewHolder myHolder = new MyLeftViewHolder(holder.itemView);
        //设置标题
        myHolder.tv_left_type.setText(list.get(position).getGc_name());
        //第三次请求网络 获取第三级数据
        OkHttp3Utils.doGet(API.TYPE_PATH + "&gc_id=" + list.get(position).getGc_id(),
                new GsonObjectCallback<DateGridBean>() {

                    @Override
                    public void onUi(DateGridBean dateGridBean) {
                        myHolder.gv.setAdapter(new MyAdapter_TypeGridView(context,
                                dateGridBean.getDatas().getClass_list()));
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyLeftViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_left_type;
        private MyGridView gv;
        public MyLeftViewHolder(View itemView) {
            super(itemView);
            tv_left_type = (TextView) itemView.findViewById(R.id.tv_type);
            gv = (MyGridView) itemView.findViewById(R.id.type_son);
        }
    }

    //声明成员变量
    public OnRecycleViewItemClickListener recycleViewItemClickListener;

    //定义点击接口
    public interface OnRecycleViewItemClickListener{
        void recycleViewItemClickListener(int position, View view, RecyclerView.ViewHolder viewHolder);
    }

    //提供set方法
    public void setRecycleViewItemClickListener(OnRecycleViewItemClickListener recycleViewItemClickListener) {
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }
}
