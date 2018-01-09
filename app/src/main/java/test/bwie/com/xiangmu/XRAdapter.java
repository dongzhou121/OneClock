package test.bwie.com.xiangmu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import test.bwie.com.xiangmu.bean.JiuGong;

/**
 * Created by 董洲 on 2017/11/13.
 */

public class XRAdapter extends RecyclerView.Adapter {
    JiuGong data;
    Context context;
    ArrayList mlist;//创建一个集合
    MyViewHolder myViewHolder;
//俩个布局
    private int TYPE_ONE = 0;
    private int TYPE_TWO = 1;

    public XRAdapter(JiuGong data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getItemViewType(viewType) == TYPE_ONE) {
            myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_one, parent, false));
            return myViewHolder;

        }else {
            MyViewHoldertwo myViewHoldertwo = new MyViewHoldertwo(LayoutInflater.from(context).inflate(R.layout.layout_two, parent, false));
            return myViewHoldertwo;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ONE) {
            MyViewHolder holderone = (MyViewHolder) holder;
            holderone.txtOne.setText(data.getData().get(position).getName());
            String image = data.getData().get(position).getIcon();
            ImageLoader.getInstance().displayImage(image,holderone.imageOne);
        }else{
            MyViewHoldertwo holdertwo = (MyViewHoldertwo) holder;
            ImageLoader.getInstance().displayImage(data.getData().get(position).getIcon(),((MyViewHoldertwo) holder).imageTwo);
            holdertwo.txtTwo.setText(data.getData().get(position).getName());
            holdertwo.textTwoTxt.setText(data.getData().get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.getData().size();
    }

    //第一个ViewHolder
    // ViewHolder 1
    class MyViewHolder extends RecyclerView.ViewHolder {
       TextView txtOne;
        ImageView imageOne;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtOne = (TextView) itemView.findViewById(R.id.one_txt);
            imageOne = (ImageView) itemView.findViewById(R.id.one_image);
        }
    }
    //第二个ViewHolder
    // ViewHolder 2
    class MyViewHoldertwo extends RecyclerView.ViewHolder{

        ImageView imageTwo;
        TextView txtTwo;
        TextView textTwoTxt;

        public MyViewHoldertwo(View itemView) {
            super(itemView);
            imageTwo = (ImageView) itemView.findViewById(R.id.two_image);
            txtTwo = (TextView) itemView.findViewById(R.id.two_txt);
            textTwoTxt = (TextView) itemView.findViewById(R.id.two_txt_two);
        }
    }
    //getItemViewType方法，主要是用来进行多条目判断的，那几个数据用这个ViewHolder,其他的数据用那个Viewholder。
    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ONE) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }
}
