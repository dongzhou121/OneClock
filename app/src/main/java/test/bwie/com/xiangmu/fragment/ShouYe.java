package test.bwie.com.xiangmu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import okhttp3.Call;
import test.bwie.com.xiangmu.R;
import test.bwie.com.xiangmu.XRAdapter;
import test.bwie.com.xiangmu.adapter.JiuGongAdapter;
import test.bwie.com.xiangmu.adapter.SouBase;
import test.bwie.com.xiangmu.bean.JiuGong;
import test.bwie.com.xiangmu.bean.LunBo;
import test.bwie.com.xiangmu.bean.SouSuoBean;
import test.bwie.com.xiangmu.utils.API;
import test.bwie.com.xiangmu.utils.DividerItemDecoration;
import test.bwie.com.xiangmu.utils.GlideImaGlideImageLoader;
import test.bwie.com.xiangmu.utils.GsonObjectCallback;
import test.bwie.com.xiangmu.utils.NetWorkUtils;
import test.bwie.com.xiangmu.utils.OkHttp3Utils;

/**
 * Created by 董洲 on 2017/10/31.
 */

public class ShouYe extends Fragment implements TextWatcher {
    private Banner mbanner;
    private String lbpath=API.LunBo_Path;
    private String jgpath = API.JiuGong_Path;
    private List<String> lunboList;
    private ImageView erweima;
    private RecyclerView jgrecyclerview;
    private XRecyclerView xr;
    private int curr;//获取数据开始
    private List<String> list=new ArrayList<>();
    //在封装的实体类中找到集合的那个类作为泛型
    private List<JiuGong.DataBean> jiugongList;
    private CountdownView countdownView;
    //搜索框
    private EditText appSou;
    private String sousuopath = API.SOU_PATH;
    private List<SouSuoBean.DataBean> sdata;
    private SouBase souBase;
    private String asou;
    private ListView listShow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye, null);

        ButterKnife.bind(getActivity());
        sdata = new ArrayList<SouSuoBean.DataBean>();
        appSou = (EditText) view.findViewById(R.id.app_sou);
        listShow = (ListView) view.findViewById(R.id.app_sou_list);
        appSou.addTextChangedListener(this);
        //监听事件
        appSou.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    appSou.setText("");
                }
            }
        });

        //实例化控件
        mbanner= (Banner) view.findViewById(R.id.banner);
        erweima = (ImageView) view.findViewById(R.id.erweima);
        jgrecyclerview = (RecyclerView) view.findViewById(R.id.jgrecyclerview);
        xr = (XRecyclerView) view.findViewById(R.id.xr);
        //加布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xr.setLayoutManager(layoutManager);
        xr.setPullRefreshEnabled(true);
        xr.setLoadingMoreEnabled(true);


          //上拉加载下拉刷新
        xr.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                curr=21;
                list.clear();
                getData(API.JiuGong_Path,curr);
                xr.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                 curr += 1;    //分页
                getData(API.JiuGong_Path,curr);
                xr.refreshComplete();
            }
        });
        getData(API.JiuGong_Path,curr);

        countdownView = (CountdownView) view.findViewById(R.id.countdownView);
        countdownView.start(1000000000);//倒计时开始
        //记住
        boolean newtWorkAvailable = NetWorkUtils.isNetWorkAvailable(getActivity());
        if (!newtWorkAvailable) {
            Toast.makeText(getActivity(), "点击", Toast.LENGTH_SHORT).show();

        }
        getdata();

        //二维码的点击事件
        erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent,1);
            }

        });
        //实例化放轮播图的集合
        lunboList = new ArrayList<>();
        GetDate(lbpath);

        return view;
    }

    private void getData(String jgpath, int curr) {
        OkHttp3Utils.getInstance().doGet(jgpath, new GsonObjectCallback<JiuGong>() {
            @Override
            public void onUi(JiuGong jiuGong) {
                XRAdapter mxradapter=new XRAdapter(jiuGong,getActivity());
                xr.setAdapter(mxradapter);
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }

    //网络获取图片
    private void GetDate(String lbpath) {
        OkHttp3Utils.getInstance().doGet(lbpath, new GsonObjectCallback<LunBo>() {
            @Override
            public void onUi(LunBo lunBo) {
                for (int i = 0; i < lunBo.getData().size(); i++) {

                    String images = lunBo.getData().get(i).getIcon();

                    lunboList.add(images);
                }

              //设置图片加载器
                mbanner.setImageLoader(new GlideImaGlideImageLoader());
                mbanner.setImages(lunboList);
                mbanner.start();

            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });

    }

    public void getdata() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
//设置布局管理器
        jgrecyclerview.setLayoutManager(layoutManager);
        //RecyclerView做出Gridview布局   实现效果
        jgrecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),4));
        jgrecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        //使用okhttp实现页面
        OkHttp3Utils.getInstance().doGet(jgpath, new GsonObjectCallback<JiuGong>() {


                    @Override
                    public void onUi(JiuGong jiuGong) {
                        jiugongList=jiuGong.getData();
                        JiuGongAdapter mAdapter =  new JiuGongAdapter(getActivity(),jiugongList);
                        jgrecyclerview.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //获取输入框的值
        asou = appSou.getText().toString().trim();

        OkHttp3Utils.getInstance().doGet(sousuopath + "?keywords=" + asou + "&page=1", new GsonObjectCallback<SouSuoBean>() {

            @Override
            public void onUi(final SouSuoBean souSuoBean) {
                  /*适配器*/
                if (asou != null && !asou.equals("")) {
                    sdata = souSuoBean.getData();
                    souBase = new SouBase(getActivity(),sdata);
                    listShow.setAdapter(souBase);
                    listShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getActivity(), Shop.class);
                            intent.putExtra("url", souSuoBean.getData().get(i).getDetailUrl());
                            startActivity(intent);
                            Toast.makeText(getActivity(), "假装你已经点击了哦！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (souBase != null) {
                    sdata.clear();
                    souBase.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
    @Override
    public void afterTextChanged(Editable editable) {

    }

    //创建GlideImageLoader 图片加载类
//    public class GlideIm4ageLoader extends ImageLoader {
//
////        @Override
//
//        public void displayImage(Context context, Object path, ImageView imageView) {
//
//            Glide.with(context).load(path).into(imageView);
//
//        }
//
//    }
}
