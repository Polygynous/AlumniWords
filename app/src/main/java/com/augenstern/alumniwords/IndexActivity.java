package com.augenstern.alumniwords;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.augenstern.alumniwords.bean.loginBean;
import com.augenstern.alumniwords.utils.Httptools;
import com.augenstern.alumniwords.utils.MyBean;
import com.augenstern.alumniwords.utils.MyPref;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_keyword;
    private ImageView iv_search;
    private MyPref mysp;
    private Button bt_share;
    private TextView tv_about;
    private Button bt_see;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_index);
        initView();
        initData();
    }

    private void initView() {
        et_keyword = (EditText) findViewById(R.id.et_keyword);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        bt_share = (Button) findViewById(R.id.bt_share);
        bt_share.setOnClickListener(this);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_about.setOnClickListener(this);
        bt_see = (Button) findViewById(R.id.bt_see);
        bt_see.setOnClickListener(this);

        //实现软键盘的搜索按钮
        et_keyword.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {//修改回车键功能
                    //隐藏软键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String Keyword = et_keyword.getText().toString().trim();
                    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                    intent.putExtra("Keyword", Keyword);
                    startActivity(intent);
                }
                return false;
            }
        });    }

    private void initData() {
        mysp = new MyPref(IndexActivity.this);
        if (!mysp.getString("cookie").isEmpty()) {//判断Cookie是否有效
            MyBean.setCookie(mysp.getString("cookie"));
            CheckLogin();
        } else {//Cookie无效则重新登录
            Login();
        }
    }

    private void CheckLogin() {
        Httptools.postAsync("/user/login/anonymous", "{}", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(IndexActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                loginBean loginBean = gson.fromJson(string, loginBean.class);
                if (!loginBean.getMessage().equals("ok")) {
                    mysp.setString("cookie", "");
                    MyBean.setCookie("");
                    Login();//清空原有数据后再重新登录
                }
            }
        });
    }

    private void Login() {
        Httptools.getAsync("/user/get/login", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(IndexActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Headers headers = response.headers();
                String cookie = headers.get("set-cookie");
                mysp.setString("cookie", cookie);
                MyBean.setCookie(cookie);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                String Keyword = et_keyword.getText().toString().trim();
                Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                intent.putExtra("Keyword", Keyword);
                startActivity(intent);
//                finish();
                break;
            case R.id.bt_share:
                startActivity(new Intent(IndexActivity.this, ShareActivity.class));
//                finish();
                break;
            case R.id.bt_see:
                startActivity(new Intent(IndexActivity.this, MainActivity.class));
//                finish();
                break;
            case R.id.tv_about:
                startActivity(new Intent(IndexActivity.this, AboutActivity.class));
                //finish();
                break;
        }
    }
}