package test.bwie.com.xiangmu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import test.bwie.com.xiangmu.LoginActivity;
import test.bwie.com.xiangmu.R;

/**
 * Created by 董洲 on 2017/10/31.
 */

public class User extends Fragment{
  private ImageView touxiang;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user, null);
        touxiang = (ImageView) view.findViewById(R.id.login_img);

        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
