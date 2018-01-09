package test.bwie.com.xiangmu.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import test.bwie.com.xiangmu.R;
import test.bwie.com.xiangmu.bean.ShopBean;
import test.bwie.com.xiangmu.presenter.OnUiCallback;
import test.bwie.com.xiangmu.utils.OkHttp3Utils;

/**
 * Created by 董洲 on 2017/11/28.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private String[] group;
    private String[][] child;
    private HashMap<Integer, Boolean> groupHashMap;
    private List<HashMap<Integer, Boolean>> childList;
    private List<List<ShopBean>> dataList;
    List<ShopBean.DataBean> class_list;
    public ExpandableAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        OkHttp3Utils.doGet("http://120.27.23.105/product/getCarts?uid=100", new OnUiCallback(){

            @Override
            public void onFailed(Call call, IOException e) {

            }

            @Override
            public void onSuccess(String result) throws IOException {
                Gson gson = new Gson();
                ShopBean bean2 =  gson.fromJson(result, ShopBean.class);
                class_list = bean2.getData();

                group = new String[class_list.size()];
                child = new String[class_list.size()][];
                groupHashMap = new HashMap<>();
                childList = new ArrayList<>();
                dataList = new ArrayList<>();

                for (int i = 0; i < class_list.size(); i++) {
                    group[i] = class_list.get(i).getSellerName();
                    groupHashMap.put(i, false);

                    String[] strings = new String[class_list.get(i).getList().size()];
                    HashMap<Integer, Boolean> map = new HashMap<>();
                    ArrayList<ShopBean> been = new ArrayList<>();
                    for (int y = 0; y < class_list.get(i).getList().size(); y++) {
                        strings[y] = class_list.get(i).getList() + class_list.get(i).getList().get(y).getImages();
                        map.put(y, false);
//                        ShopBean bean = new ShopBean("100", "1");
//                        been.add(bean);
                        child[i] = strings;
                        childList.add(map);
                        dataList.add(been);
                    }
                }
            }
        });
    }

    @Override
    public int getGroupCount() {
        return group==null?0:group.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return child[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return group[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return child[i];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.shop_group, null);
            holder = new GroupViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.group_tv);
            holder.ck = (CheckBox) view.findViewById(R.id.group_ck);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        holder.tv.setText(group[i]);
        holder.ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupHashMap.put(i, !groupHashMap.get(i));
                //设置二级列表的选中状态,根据一级列表的状态来改变
                setChildCheckAll();
                //计算选中的价格和数量
                String shopPrice = getShopPrice();
                //判断商品是否全部选中
                boolean b = selectAll();
//                adapterData.Data(v, shopPrice, b);
            }
        });
        holder.ck.setChecked(groupHashMap.get(i));
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.shop_child, null);
            holder = new ChildViewHolder();
            holder.ctv = (TextView) view.findViewById(R.id.child_tv);
            holder.cck = (CheckBox) view.findViewById(R.id.child_ck);
            holder.jianshao = (TextView) view.findViewById(R.id.jianshao);
            holder.zengjia = (TextView) view.findViewById(R.id.zengjia);
            holder.number = (TextView) view.findViewById(R.id.number);
            holder.cimage = (ImageView) view.findViewById(R.id.imageShow);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        //请求题目
        String createtime = class_list.get(i).getList().get(0).getTitle();
        holder.ctv.setText(createtime);
        //请求图片
        String detailUrl = class_list.get(i).getList().get(0).getImages();
        String [] temp = null;
        temp = detailUrl.split("\\|");
        Glide.with(context).load(temp[0]).into(holder.cimage);
        holder.cck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Integer, Boolean> hashMap = childList.get(i);
                hashMap.put(i1, !hashMap.get(i1));
                //判断二级列表是否全部选中
                ChildisChecked(i);
                //计算选中的价格和数量
                String shopPrice = getShopPrice();
                //判断商品是否全部选中
                boolean b = selectAll();
                adapterData.Data(view, shopPrice, b);
            }
        });
        final ChildViewHolder finalHolder = holder;
        holder.zengjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ShopBean> been = dataList.get(i);
                String num = finalHolder.number.getText().toString();
                int i = Integer.parseInt(num);
                ++i;
//                been.get(i1).setNumber(i + "");
                //计算选中的价格和数量
                String shopPrice = getShopPrice();
                //判断商品是否全部选中
                boolean b = selectAll();
                adapterData.Data(view, shopPrice, b);
                notifyDataSetChanged();
            }
        });
        holder.jianshao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ShopBean> been = dataList.get(i);
                String num = finalHolder.number.getText().toString();
                int i = Integer.parseInt(num);
                if (i > 1) {
                    --i;
                }
//                been.get(i1).setNumber(i + "");
                //计算选中的价格和数量
                String shopPrice = getShopPrice();
                //判断商品是否全部选中
                boolean b = selectAll();
                adapterData.Data(view, shopPrice, b);
                notifyDataSetChanged();
            }
        });
//        holder.number.setText(dataList.get(i).get(i1).getNumber().toString());

        holder.cck.setChecked(childList.get(i).get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    class GroupViewHolder {
        TextView tv;
        CheckBox ck;
    }
    class ChildViewHolder {
        //        TextView danjia;
        ImageView cimage;
        TextView ctv;
        CheckBox cck;
        TextView jianshao;
        TextView zengjia;
        TextView number;
    }
    private void setChildCheckAll() {
        for (int i = 0; i < childList.size(); i++) {
            HashMap<Integer, Boolean> integerBooleanHashMap1 = childList.get(i);
            Set<Map.Entry<Integer, Boolean>> entries = integerBooleanHashMap1.entrySet();
            for (Map.Entry<Integer, Boolean> entry : entries) {
                entry.setValue(groupHashMap.get(i));
            }
        }
        notifyDataSetChanged();
    }
    //判断二级列表是否全部选中
    private void ChildisChecked(int groupPosition) {
        boolean ischecked = true;
        HashMap<Integer, Boolean> hashMap = childList.get(groupPosition);
        Set<Map.Entry<Integer, Boolean>> entries = hashMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            if (!entry.getValue()) {
                ischecked = false;
                break;
            }
        }
        groupHashMap.put(groupPosition, ischecked);
        notifyDataSetChanged();
    }
    //全选
    public void checkAllShop(boolean checked) {
        Set<Map.Entry<Integer, Boolean>> entries = groupHashMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(checked);
        }
        //调用让二级列表全选的方法
        setChildCheckAll();
        notifyDataSetChanged();
    }
    //计算价格
    public String getShopPrice() {
        int price = 0;
        int number = 0;
        for (int y = 0; y < childList.size(); y++) {
            HashMap<Integer, Boolean> integerBooleanHashMap1 = childList.get(y);
            Set<Map.Entry<Integer, Boolean>> entries = integerBooleanHashMap1.entrySet();
            for (Map.Entry<Integer, Boolean> entry : entries) {
                if (entry.getValue()) {
//                    ShopBean bean = ShopBean.DataBean.ListBean.get(y).get(entry.getKey());
//                    price += Integer.parseInt(bean.getData().get(i).getList().get(i).getPrice()) * Integer.parseInt(bean.getNumber());
//                    number += Integer.parseInt(bean.getNumber());
                }
            }
        }
        return price + "," + number;
    }
    //编辑一级和二级列表,如果全部选中,全选按钮也选中
    public boolean selectAll() {
        boolean isChecked = true;
        if(childList!=null){
            for (int y = 0; y < childList.size(); y++) {
                HashMap<Integer, Boolean> hashMap = childList.get(y);
                Set<Map.Entry<Integer, Boolean>> entries = hashMap.entrySet();
                for (Map.Entry<Integer, Boolean> entry : entries) {
                    if (!entry.getValue()) {
                        isChecked = false;
                        break;
                    }
                }
            }
        }

        return isChecked;
    }
    private AdapterData adapterData;

    public interface AdapterData {
        void Data(View v, String str, boolean b);

    }

    public void getAdapterData(AdapterData adapterData) {
        this.adapterData = adapterData;
    }
}