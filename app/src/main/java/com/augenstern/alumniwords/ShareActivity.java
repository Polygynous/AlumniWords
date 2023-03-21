package com.augenstern.alumniwords;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.augenstern.alumniwords.bean.typeBaen;
import com.augenstern.alumniwords.utils.Httptools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_title;
    private RelativeLayout rl_container1;
    private TextView tv_tips;
    private Spinner sp_school;
    private LinearLayout ll_container1;
    private Spinner sp_major;
    private LinearLayout ll_container2;
    private EditText et_research;
    private LinearLayout ll_container3;
    private Spinner sp_education;
    private LinearLayout ll_container4;
    private Spinner sp_workexp;
    private LinearLayout ll_container5;
    private LinearLayout ll_container6;
    private EditText et_content;
    private Button bt_submit;
    private Button bt_reset;
    private RelativeLayout rl_container2;
    private ArrayList<String> Schools = new ArrayList<>();
    private ArrayList<String> Majors = new ArrayList<>();
    private ArrayList<String> Educations = new ArrayList<>();
    private ArrayList<String> WorkExps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_container1 = (RelativeLayout) findViewById(R.id.rl_container1);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        sp_school = (Spinner) findViewById(R.id.sp_school);
        ll_container1 = (LinearLayout) findViewById(R.id.ll_container1);
        sp_major = (Spinner) findViewById(R.id.sp_major);
        ll_container2 = (LinearLayout) findViewById(R.id.ll_container2);
        et_research = (EditText) findViewById(R.id.et_research);
        ll_container3 = (LinearLayout) findViewById(R.id.ll_container3);
        sp_education = (Spinner) findViewById(R.id.sp_education);
        ll_container4 = (LinearLayout) findViewById(R.id.ll_container4);
        sp_workexp = (Spinner) findViewById(R.id.sp_workExp);
        ll_container5 = (LinearLayout) findViewById(R.id.ll_container5);
        ll_container6 = (LinearLayout) findViewById(R.id.ll_container6);
        et_content = (EditText) findViewById(R.id.et_content);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_reset = (Button) findViewById(R.id.bt_reset);
        rl_container2 = (RelativeLayout) findViewById(R.id.rl_container2);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_submit.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
        initData();
    }

    private void initData() {
        Schools.add("");
        Majors.add("");
        Educations.add("");
        WorkExps.add("");
        Httptools.getAsync("/tag/get/map", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ShareActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.getString("message").equals("ok")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        Gson gson = new Gson();
                        for (int i = 0; i < data.getJSONArray("学校").length(); i++) {
                            typeBaen typebaen = gson.fromJson(data.getJSONArray("学校").get(i).toString(), typeBaen.class);
                            Schools.add(typebaen.getTagName());
                        }
                        for (int i = 0; i < data.getJSONArray("专业").length(); i++) {
                            typeBaen typebaen = gson.fromJson(data.getJSONArray("专业").get(i).toString(), typeBaen.class);
                            Majors.add(typebaen.getTagName());
                        }
                        for (int i = 0; i < data.getJSONArray("最高学历").length(); i++) {
                            typeBaen typebaen = gson.fromJson(data.getJSONArray("最高学历").get(i).toString(), typeBaen.class);
                            Educations.add(typebaen.getTagName());
                        }
                        for (int i = 0; i < data.getJSONArray("工作经验").length(); i++) {
                            typeBaen typebaen = gson.fromJson(data.getJSONArray("工作经验").get(i).toString(), typeBaen.class);
                            WorkExps.add(typebaen.getTagName());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sp_school.setAdapter(new ArrayAdapter<String>(ShareActivity.this, R.layout.spinner_item, Schools));
                                sp_major.setAdapter(new ArrayAdapter<String>(ShareActivity.this, R.layout.spinner_item, Majors));
                                sp_education.setAdapter(new ArrayAdapter<String>(ShareActivity.this, R.layout.spinner_item, Educations));
                                sp_workexp.setAdapter(new ArrayAdapter<String>(ShareActivity.this, R.layout.spinner_item, WorkExps));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                submit();
                break;
            case R.id.bt_reset:
                sp_school.setSelection(0);
                sp_major.setSelection(0);
                sp_education.setSelection(0);
                sp_workexp.setSelection(0);
                et_research.setText("");
                et_content.setText("");
                break;
        }
    }

    private void submit() {
        String research = et_research.getText().toString().trim();
//        if (TextUtils.isEmpty(research)) {
//            Toast.makeText(this, "如没有可不填", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (sp_school.getSelectedItemId() != 0) {
            Toast.makeText(this, "请选择您的学校名称。", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sp_major.getSelectedItemId() != 0) {
            Toast.makeText(this, "请选择您的专业名称。", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sp_education.getSelectedItemId() != 0) {
            Toast.makeText(this, "请选择您的最高学历。", Toast.LENGTH_SHORT).show();
            return;
        }

        if (sp_workexp.getSelectedItemId() != 0) {
            Toast.makeText(this, "请选择您的工作经验。", Toast.LENGTH_SHORT).show();
            return;
        }

        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请您写下您的专业建议\n专业建议最少20字\n从学习到就业话题均可", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "投稿成功!\n" + "感谢您的建议,审核通过后即可公开!", Toast.LENGTH_SHORT).show();
    }
}