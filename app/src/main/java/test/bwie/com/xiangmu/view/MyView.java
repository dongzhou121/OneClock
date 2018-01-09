package test.bwie.com.xiangmu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.bwie.com.xiangmu.R;

/**
 * Created by 董洲 on 2017/12/7.
 */

public class MyView extends LinearLayout {
    private ImageView iv_add;
    private ImageView iv_del;
    private TextView tv_num;
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.shop_delect, this);
        iv_add = (ImageView) view.findViewById(R.id.txt_add);
        iv_del = (ImageView) view.findViewById(R.id.txt_delete);
        tv_num = (TextView) view.findViewById(R.id.et_number);
    }
    public void setAddClickListener(OnClickListener onClickListener) {
        iv_add.setOnClickListener(onClickListener);
    }
    public void setDelClickListener(OnClickListener onClickListener) {
        iv_del.setOnClickListener(onClickListener);
    }
    public void setNum(String num) {
        tv_num.setText(num);
    }
    public int getNum() {
        String num = tv_num.getText().toString();
        return Integer.parseInt(num);
    }
}
