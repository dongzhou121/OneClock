package test.bwie.com.xiangmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.bwie.com.xiangmu.bean.ZhuCe;
import test.bwie.com.xiangmu.fragment.User;
import test.bwie.com.xiangmu.utils.API;

public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener {
  private EditText zhuName,zhuPass,zhuRepass,zhuEmail;
    private Button zhuce;
    private ImageView backLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        backLogin = (ImageView) findViewById(R.id.back_login);
        zhuName = (EditText) findViewById(R.id.zhu_name);
        zhuPass = (EditText) findViewById(R.id.zhu_pass);
        zhuRepass = (EditText) findViewById(R.id.zhu_repass);
        zhuEmail = (EditText) findViewById(R.id.zhu_email);
        zhuce = (Button) findViewById(R.id.zhuce_btn);


        backLogin.setOnClickListener(this);
        zhuce.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_login:
                Intent intent1 = new Intent(ZhuceActivity.this, User.class);
                startActivity(intent1);
                break;
            case R.id.zhuce_btn:
                String username = zhuName.getText().toString().trim();
                String password = zhuPass.getText().toString().trim();
                String password_confirm = zhuRepass.getText().toString().trim();
                String email = zhuEmail.getText().toString().trim();
                if (checkDate(username, password,password_confirm,email)) {
                    final RequestParams params = new RequestParams(API.ZHUCE_PATH);
                    params.addQueryStringParameter("username", username);
                    params.addQueryStringParameter("password", password);
                    params.addQueryStringParameter("password_confirm", password_confirm);
                    params.addQueryStringParameter("email", email);
                    x.http().get(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            ZhuCe zhuCe = gson.fromJson(result,ZhuCe.class);
                            if (zhuCe.getCode().equals("1")) {
                                Intent intent2 = new Intent(ZhuceActivity.this, LoginActivity.class);
                                startActivity(intent2);
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

    /**
     * 验证输入的合法性
     * @param username
     * @param password
     * @param password_confirm
     * @param email
     * @return
     */
    private boolean checkDate(String username,String password,String password_confirm,String email){
        if(TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)||
                TextUtils.isEmpty(password_confirm)|| TextUtils.isEmpty(email)){
            Toast.makeText(ZhuceActivity.this,"手机号或密码或邮箱不能为空",Toast.LENGTH_SHORT).show();
            return false;
    }
        if(!isphone(username)){
            Toast.makeText(ZhuceActivity.this,"手机号格式不对",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length()<6){
            Toast.makeText(ZhuceActivity.this,"密码小于6位数",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password_confirm.equals(password)){
            Toast.makeText(ZhuceActivity.this,"密码不同",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isemail(email)){
            Toast.makeText(ZhuceActivity.this,"邮箱格式不对",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private boolean isemail(String email) {
        // 邮箱有效性验证
         Pattern pattern = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher mc = pattern.matcher(email);
        return mc.matches();
    }

    private Boolean isphone(String username) {
        if(username.length()==11){
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }
        return null;
    }
    }

