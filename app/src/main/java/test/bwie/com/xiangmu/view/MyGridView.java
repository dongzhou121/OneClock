package test.bwie.com.xiangmu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 董洲
 */
public class MyGridView extends GridView {
    public MyGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //makeMeasureSpec根据提供的大小值和模式创建一个测量值(格式)
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
