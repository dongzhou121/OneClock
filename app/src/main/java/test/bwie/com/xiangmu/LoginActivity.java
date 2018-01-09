package test.bwie.com.xiangmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.bwie.com.xiangmu.bean.ZhuCe;
import test.bwie.com.xiangmu.utils.API;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mymobile,mypassword;
    private TextView inzhuce;
    private Button login;
    private ImageView backUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        backUser.setOnClickListener(this);
        inzhuce.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    private void init() {
        mymobile = (EditText) findViewById(R.id.mobile);
        mypassword = (EditText) findViewById(R.id.password);
        inzhuce = (TextView) findViewById(R.id.inzhuce);
        login = (Button) findViewById(R.id.login);
        backUser = (ImageView) findViewById(R.id.back_user);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_user:
                Intent intent1 = new Intent(LoginActivity.this, ZhuceActivity.class);
                startActivity(intent1);
                break;
            case R.id.inzhuce:
                Intent intent2 = new Intent(LoginActivity.this,ZhuceActivity.class);
                startActivity(intent2);
                break;
            case R.id.login:
                String username = mymobile.getText().toString().trim();
                String password = mypassword.getText().toString().trim();
                if(checkDate(username,password)) {
                    final RequestParams params = new RequestParams(API.LOGIN_PATH);
                    params.addQueryStringParameter("username", username);
                    params.addQueryStringParameter("password", password);
                    x.http().get(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            ZhuCe login = gson.fromJson(result, ZhuCe.class);
                            if (login.getCode().equals("1")) {
                                Intent intent3 = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent3);
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public boolean onCache(String result) {
                            return false;
                        }
                    });
                }
                break;
        }
    }


    private boolean checkDate(String username, String password) {
        if(TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!isphone(username)){
            Toast.makeText(LoginActivity.this,"手机号格式不对",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length()<6){
            Toast.makeText(LoginActivity.this,"密码小于6位数",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean isphone(String name){
        if(name.length()==11){

            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(name);
            return matcher.matches();
        }
        return null;
    }

    }
