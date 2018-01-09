package test.bwie.com.xiangmu.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.bwie.com.xiangmu.R;


/**
 * Created by 董洲 on 2017/11/28.
 */

public class AddDeleteView extends LinearLayout {
    private OnAddDelClickListener listener;
    private EditText etNumber;

    //对外提供一个点击的回调接口
    public interface OnAddDelClickListener{
        void onAddClick(View v);
        void onDelClick(View v);
    }

    public void setOnAddDelClickListener(OnAddDelClickListener listener){
        if(listener!=null){
            this.listener=listener;
        }
    }

    public AddDeleteView(Context context) {
        this(context,null);
    }

    public AddDeleteView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.shop_delect, this);

        //获取控件
        TextView txtDelete= (TextView) findViewById(R.id.txt_delete);
        TextView txtAdd= (TextView) findViewById(R.id.txt_add);
        etNumber = (EditText) findViewById(R.id.et_number);
//               TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddDeleteViewStyle);
//
//        String leftText = typedArray.getString(R.styleable.AddDeleteViewStyle_left_text);
//        String rightText = typedArray.getString(R.styleable.AddDeleteViewStyle_right_text);
//        String middleText = typedArray.getString(R.styleable.AddDeleteViewStyle_middle_text);
//        int color = typedArray.getColor(R.styleable.AddDeleteViewStyle_left_text_color, Color.BLACK);
//
//        txtDelete.setText(leftText);
//        txtAdd.setText(rightText);
//        etNumber.setText(middleText);
//        txtDelete.setTextColor(color);

        //回收
//        typedArray.recycle();
        txtDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelClick(view);
            }
        });

        txtAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddClick(view);
            }
        });
    }
    //对外提供一个修改数字的方法
    public void setNumber(int number){
        if(number>0){
            etNumber.setText(number+"");
        }
    }
    //对外提供一个获取当前数字的方法
    public int getNumber(){
        String string = etNumber.getText().toString();
        int i = Integer.parseInt(string);
        return i;
    }
}
