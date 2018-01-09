package dongzhou.bwie.com.yuekaomoni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dongzhou.bwie.com.yuekaomoni.R;

/**
 * Created by 董洲 on 2017/11/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter <MyRecyclerAdapter.MyViewHolder>{
        private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    MyViewHolder holder;
        public MyRecyclerAdapter(Context context, List<String> datas){
        this. mContext=context;
        this. mDatas=datas;
         inflater=LayoutInflater. from(mContext);
}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout. item_home,parent, false);
        holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    holder.tvShow.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvShow;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvShow=(TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
