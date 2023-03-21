package com.augenstern.alumniwords.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.augenstern.alumniwords.MyLayoutManager;
import com.augenstern.alumniwords.R;
import com.augenstern.alumniwords.bean.adviceBean;
import com.augenstern.alumniwords.utils.Httptools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class adviceAdapter extends RecyclerView.Adapter<adviceAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<adviceBean> Objects;

    public adviceAdapter(Context context, ArrayList<adviceBean> objects) {
        mContext = context;
        Objects = objects;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.advice_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final adviceBean adviceBean = Objects.get(position);
        holder.tv_advice.setText(adviceBean.getContent());
        holder.tv_date.setText(adviceBean.getCreateTime().split("T")[0]);
        holder.iv_thumbs_up.setSelected(adviceBean.isHasThumb());//设置点赞状态
        holder.tv_thumbs_up.setText(String.valueOf(adviceBean.getThumbNum()));
        ArrayList<String> tables = new ArrayList<>();
        if (!adviceBean.getMajor().isEmpty()) {
            if (adviceBean.getResearch() != null) {
                tables.add(adviceBean.getEducation() + "（" + adviceBean.getResearch() + "）");
            }else{
                tables.add(adviceBean.getMajor());
            }
        }
        if (!adviceBean.getEducation().isEmpty()) {
            tables.add(adviceBean.getEducation());
        }
        if (!adviceBean.getSchool().isEmpty()) {
            tables.add(adviceBean.getSchool());
        }
        if (!adviceBean.getWorkExp().isEmpty()) {
            tables.add(adviceBean.getWorkExp());
        }
        tableAdapter tableAdapter = new tableAdapter(mContext, tables);
        holder.rv_tables.setAdapter(tableAdapter);
        holder.iv_thumbs_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = !holder.isthumbsup;
                if (status) {
                    holder.tv_thumbs_up.setText(String.valueOf(adviceBean.getThumbNum() + 1));
                }else{
                    holder.tv_thumbs_up.setText(String.valueOf(adviceBean.getThumbNum()));
                }
                ThumbsUp(adviceBean);
                holder.isthumbsup = status;
                holder.iv_thumbs_up.setSelected(status);
            }
        });
        holder.tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(mContext);
                inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                final AlertDialog.Builder Dialog = new AlertDialog.Builder(mContext);
                Dialog.setTitle("我要反馈")
                        .setView(inputServer)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String feedback = inputServer.getText().toString();
                                if (feedback != null && !feedback.isEmpty()) {
                                    Toast.makeText(mContext, "反馈成功，十分感谢您的反馈!\n我们必将认真审核!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "反馈失败！您未输入任何信息!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                Dialog.show();
            }
        });
    }

    public void ThumbsUp(adviceBean adviceBean){
        String postId = String.valueOf(adviceBean.getId());
        JSONObject body = new JSONObject();
        try {
            body.put("postId",postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Httptools.postAsync("/post/thumb?postId=" + postId, body.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("TAG", "点赞信息: " + string);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_advice;
        public TextView tv_date;
        public ImageView iv_thumbs_up;
        public TextView tv_thumbs_up;
        public TextView tv_feedback;
        public RecyclerView rv_tables;
        public boolean isthumbsup = false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_advice = (TextView) rootView.findViewById(R.id.tv_advice);
            this.tv_date = (TextView) rootView.findViewById(R.id.tv_date);
            this.rv_tables = (RecyclerView) rootView.findViewById(R.id.rv_tables);
            this.iv_thumbs_up = (ImageView) rootView.findViewById(R.id.iv_thumbs_up);
            this.tv_thumbs_up = (TextView) rootView.findViewById(R.id.tv_thumbs_up);
            this.tv_feedback = (TextView) rootView.findViewById(R.id.tv_feedback);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            rv_tables.setLayoutManager(layoutManager);
//            MyLayoutManager myLayoutManager = new MyLayoutManager();
//            myLayoutManager.setAutoMeasureEnabled(true);//防止recyclerview高度为wrap时测量item高度0(一定要加这个属性，否则显示不出来）
//            rv_tables.setLayoutManager(myLayoutManager);
        }
    }
}
