package test.bwie.com.xiangmu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import test.bwie.com.xiangmu.R;
import test.bwie.com.xiangmu.bean.JiuGong;

/**
 * Created by 董洲 on 2017/11/7.
 */

public class JiuGongAdapter extends RecyclerView.Adapter<JiuGongAdapter.MyViewHolder> {
    private Context context;
    private List<JiuGong.DataBean> jiugongList;
    private ImageLoader instance;

    public JiuGongAdapter(Context context, List<JiuGong.DataBean> jiugongList) {
        this.context = context;
        this.jiugongList = jiugongList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<JiuGong.DataBean> getJiugongList() {
        return jiugongList;
    }

    public void setJiugongList(List<JiuGong.DataBean> jiugongList) {
        this.jiugongList = jiugongList;
    }

    @Override
    public JiuGongAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.jiugong_layout,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(JiuGongAdapter.MyViewHolder holder, int position) {
        //jiugongList = new ArrayList<>();
        holder.jgText.setText(jiugongList.get(position).getName());


        String image = jiugongList.get(position).getIcon();
        instance= ImageLoader.getInstance();
        instance.displayImage(image,holder.jgImage);
    }

    @Override
    public int getItemCount() {
        return jiugongList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView jgText;
        private ImageView jgImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            jgText = (TextView) itemView.findViewById(R.id.jiugong_text);
            jgImage = (ImageView) itemView.findViewById(R.id.jiugong_image);

        }
    }
}
