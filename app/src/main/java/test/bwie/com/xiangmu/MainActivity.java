package test.bwie.com.xiangmu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import test.bwie.com.xiangmu.adapter.MyFragmentAdapter;
import test.bwie.com.xiangmu.fragment.FenLei;
import test.bwie.com.xiangmu.fragment.Shop;
import test.bwie.com.xiangmu.fragment.ShouYe;
import test.bwie.com.xiangmu.fragment.User;

public class MainActivity extends FragmentActivity {
   private ViewPager viewPager;
    private RadioGroup rg;
    private RadioButton shou,fen,shop,user;
    private List<Fragment> list;
    private ShouYe one;
    private FenLei two;
    private Shop three;
    private User four;
    private MyFragmentAdapter myFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        shou = (RadioButton) findViewById(R.id.btn_one);
        fen = (RadioButton) findViewById(R.id.btn_two);
        shop = (RadioButton) findViewById(R.id.btn_three);
        user = (RadioButton) findViewById(R.id.btn_four);
        rg = (RadioGroup) findViewById(R.id.rg_menu);

        list = new ArrayList<Fragment>();
        one = new ShouYe();
        two = new FenLei();
        three = new Shop();
        four = new User();
        list.add(one);
        list.add(two);
//        list.add(three);
        list.add(four);

        FragmentManager fm = getSupportFragmentManager();
        //初始化自定义适配器
        myFragmentAdapter =  new MyFragmentAdapter(fm,list);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(0);//默认为第一个页面
        //viewPager的滑动
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_one:
                        //给ViewPager设置当前布局
                        viewPager.setCurrentItem(0);
                    case R.id.btn_two:
                        //给ViewPager设置当前布局
                        viewPager.setCurrentItem(1);
                    case R.id.btn_three:
                        //给ViewPager设置当前布局
                        viewPager.setCurrentItem(2);
                    case R.id.btn_four:
                        //给ViewPager设置当前布局
                        viewPager.setCurrentItem(3);
                }
            }
        });
        //RadioButton的点击事件实现联动
     shou.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             viewPager.setCurrentItem(0);
         }
     });
        fen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });
    }

}
