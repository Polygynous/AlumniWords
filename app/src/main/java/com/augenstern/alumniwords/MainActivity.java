package com.augenstern.alumniwords;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.augenstern.alumniwords.adapter.adviceAdapter;
import com.augenstern.alumniwords.bean.adviceBean;
import com.augenstern.alumniwords.bean.typeBaen;
import com.augenstern.alumniwords.utils.Httptools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mishaki.libsearchspinner.adapter.StringMarkSpinnerAdapter;
import com.mishaki.libsearchspinner.view.SearchSpinner;
import com.mishaki.libsearchspinner.view.StringSearchSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MScrollView ScrollView;
    private EditText et_keyword;
    private ImageView iv_search;
    private TextView tv_major;
    private TextView tv_school;
    private TextView tv_education;
    private TextView tv_workexp;
    private StringSearchSpinner sp_major;
    private StringSearchSpinner sp_school;
    private Spinner sp_education;
    private Spinner sp_workexp;
    private TextView tv_total;
    private Spinner sp_sortfield;
    private TextView tv_total2;
    private Spinner sp_sortfield2;
    private ProgressBar pb_progress;
    private Button bt_previous;
    private FloatingDraftButton bt_back2top;
    private Button bt_next;
    private RecyclerView rv_advices;
    private int pages = 1;//总页数
    private int current = 1;//当前页数
    private String major = "";//专业
    private String school = "";//学校
    private String education = "";//最高学历
    private String workexp = "";//工作经验
    private String content = "";//搜索内容
    private String sortField = "";//排序方法
    private ArrayList<String> Majors = new ArrayList<>();
    private ArrayList<String> Schools = new ArrayList<>();
    private ArrayList<String> Educations = new ArrayList<>();
    private ArrayList<String> WorkExps = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        initView();
        if (getIntent().getStringExtra("Keyword") != null) {
            content = getIntent().getStringExtra("Keyword");
            et_keyword.setText(content);
        }
        initData();
    }

    private void initView() {
        ScrollView = (MScrollView) findViewById(R.id.ScrollView);
        et_keyword = (EditText) findViewById(R.id.et_keyword);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        tv_major = (TextView) findViewById(R.id.tv_major);
        tv_school = (TextView) findViewById(R.id.tv_school);
        tv_education = (TextView) findViewById(R.id.tv_education);
        tv_workexp = (TextView) findViewById(R.id.tv_workexp);
        sp_major = (StringSearchSpinner) findViewById(R.id.sp_major);
        sp_school = (StringSearchSpinner) findViewById(R.id.sp_school);
        sp_education = (Spinner) findViewById(R.id.sp_education);
        sp_workexp = (Spinner) findViewById(R.id.sp_workexp);
        tv_total = (TextView) findViewById(R.id.tv_total);
        sp_sortfield = (Spinner) findViewById(R.id.sp_sortfield);
        tv_total2 = (TextView) findViewById(R.id.tv_total2);
        sp_sortfield2 = (Spinner) findViewById(R.id.sp_sortfield2);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        bt_previous = (Button) findViewById(R.id.bt_previous);
        bt_back2top = (FloatingDraftButton) findViewById(R.id.bt_back2top);
        bt_next = (Button) findViewById(R.id.bt_next);
        iv_search.setOnClickListener(this);
        ScrollView.setV1(findViewById(R.id.rv_container2));
        ScrollView.setV3(findViewById(R.id.bt_back2top));

        rv_advices = (RecyclerView) findViewById(R.id.rv_advices);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_advices.setLayoutManager(layoutManager);

        //实现软键盘的搜索按钮
        et_keyword.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {//修改回车键功能
                    //隐藏软键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    content = et_keyword.getText().toString().trim();
                    getAdvices();
                }
                return false;
            }
        });
    }

    private void initData() {
        bt_previous.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_back2top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView.fullScroll(View.FOCUS_UP);//回到顶部
            }
        });
        ArrayList<String> fields = new ArrayList<>();
        fields.add("最新");
        fields.add("人气");
        sp_sortfield.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, fields));
        sp_sortfield.setSelection(0,false);
        sp_sortfield.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position==0) {
                    sortField = "createTime";
                }else{
                    sortField = "thumbNum";
                }
                getAdvices();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        sp_sortfield2.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, fields));
        sp_sortfield2.setSelection(0,false);
        sp_sortfield2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position==0) {
                    sortField = "createTime";
                }else{
                    sortField = "thumbNum";
                }
                getAdvices();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        sp_major.setVisibility(View.GONE);
        sp_school.setVisibility(View.GONE);
        sp_education.setVisibility(View.GONE);
        sp_workexp.setVisibility(View.GONE);
        tv_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_major.setVisibility(View.VISIBLE);
                sp_major.performClick();
//                tv_major.setClickable(false);
            }
        });
        tv_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_school.setVisibility(View.VISIBLE);
                sp_school.performClick();
//                tv_school.setClickable(false);
            }
        });
        tv_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_education.setVisibility(View.VISIBLE);
                sp_education.performClick();
            }
        });
        tv_workexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_workexp.setVisibility(View.VISIBLE);
                sp_workexp.performClick();
            }
        });
        sp_major.setOnSelectedListener(new SearchSpinner.OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (position == 0) {
                    sp_major.setVisibility(View.GONE);
                } else {
                    sp_major.setVisibility(View.VISIBLE);
                }
                major = Majors.get(position);
//                tv_major.setClickable(true);
                getAdvices();
            }
        });
        sp_school.setOnSelectedListener(new SearchSpinner.OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (position == 0) {
                    sp_school.setVisibility(View.GONE);
                } else {
                    sp_school.setVisibility(View.VISIBLE);
                }
                school = Schools.get(position);
//                tv_school.setClickable(true);
                getAdvices();
            }
        });
        sp_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    sp_education.setVisibility(View.GONE);
                } else {
                    sp_education.setVisibility(View.VISIBLE);
                }
                education = Educations.get(position);
                getAdvices();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_workexp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    sp_workexp.setVisibility(View.GONE);
                } else {
                    sp_workexp.setVisibility(View.VISIBLE);
                }
                workexp = WorkExps.get(position);
                getAdvices();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Majors.add("");
        Schools.add("");
        Educations.add("");
        WorkExps.add("");
        Httptools.getAsync("/tag/get/map", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
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
                        for (int i = 0; i < data.getJSONArray("专业").length(); i++) {
                            typeBaen typebaen = gson.fromJson(data.getJSONArray("专业").get(i).toString(), typeBaen.class);
                            Majors.add(typebaen.getTagName());
                        }
                        for (int i = 0; i < data.getJSONArray("学校").length(); i++) {
                            typeBaen typebaen = gson.fromJson(data.getJSONArray("学校").get(i).toString(), typeBaen.class);
                            Schools.add(typebaen.getTagName());
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
//                                sp_major.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, Majors));
//                                sp_school.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, Schools));
                                sp_major.setAdapter(new StringMarkSpinnerAdapter(),Majors);
                                sp_school.setAdapter(new StringMarkSpinnerAdapter(),Schools);
                                sp_education.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, Educations));
                                sp_workexp.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, WorkExps));
//                                sp_major.setSelection(0, true);
//                                sp_school.setSelection(0, true);
                                sp_education.setSelection(0, true);
                                sp_workexp.setSelection(0, true);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        pages = 1;//初始化
        current = 1;
        getAdvices();
    }

    private void getAdvices() {
        pb_progress.setVisibility(View.VISIBLE);//显示加载条
//        ArrayList<adviceBean> adviceBeans = new ArrayList<>();
//        adviceAdapter adviceAdapter = new adviceAdapter(MainActivity.this,adviceBeans);
//        rv_advices.setAdapter(adviceAdapter);//清空数据后再回到顶部可以规避偶尔没回到顶部的Bug
        rv_advices.setVisibility(View.GONE);//先隐藏RecyclerView后再回到顶部也可以规避偶尔没回到顶部的Bug
        ScrollView.fullScroll(View.FOCUS_UP);//回到顶部
        bt_previous.setVisibility(View.GONE);
        bt_next.setVisibility(View.GONE);
        String url = "/post/list/page?current=" + current + "&pageSize=10&reviewStatus=1&sortField=" + sortField + "&sortOrder=descend";
        if (!major.isEmpty()) {
            url += "&major=" + major;
        }
        if (!school.isEmpty()) {
            url += "&school=" + school;
        }
        if (!education.isEmpty()) {
            url += "&education=" + education;
        }
        if (!workexp.isEmpty()) {
            url += "&workExp=" + workexp;
        }
        if (!content.isEmpty()) {
            url += "&content=" + content;
        }
        Httptools.getAsync(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.getString("message").equals("ok")) {
                        final JSONObject data = jsonObject.getJSONObject("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv_total.setText("建议列表（" + String.valueOf(data.getInt("total")) + "）");
                                    tv_total2.setText("建议列表（" + String.valueOf(data.getInt("total")) + "）");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        pages = data.getInt("pages");
                        JSONArray records = data.getJSONArray("records");
                        ArrayList<adviceBean> adviceBeans = new ArrayList<>();
                        Gson gson = new Gson();
                        for (int i = 0; i < records.length(); i++) {
                            adviceBeans.add(gson.fromJson(records.get(i).toString(), adviceBean.class));
                        }
                        final adviceAdapter adviceAdapter = new adviceAdapter(MainActivity.this, adviceBeans);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (current != 1) {//当前页不为第一页时显示上一页按钮
                                    bt_previous.setVisibility(View.VISIBLE);
                                }
                                if (current != pages) {//当前页不为最后一页时显示下一页按钮
                                    bt_next.setVisibility(View.VISIBLE);
                                }
                                pb_progress.setVisibility(View.GONE);//隐藏加载条
                                rv_advices.setVisibility(View.VISIBLE);
                                rv_advices.setAdapter(adviceAdapter);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_previous:
                current -= 1;
                if (current <= 0) {
                    current = 1;
                }
                break;
            case R.id.bt_next:
                current += 1;
                if (current > pages) {
                    current = pages;
                }
                break;
            case R.id.iv_search:
                content = et_keyword.getText().toString().trim();
                break;
        }
        getAdvices();
    }
}