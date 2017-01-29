package com.qdxiaogutou.eye;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/18.
 */
public class EntityAdapter extends BaseAdapter {
    private ArrayList<MsgEntity> entitys;
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<PhoneInfo> peps;

    public EntityAdapter(ArrayList<MsgEntity> entitys, Activity activity) {
        this.entitys = entitys;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        peps = new GetPhoneNumberFromMobile().getPhoneNumberFromMobile(activity);
    }

    @Override
    public int getCount() {
        return entitys.size();
    }

    @Override
    public MsgEntity getItem(int position) {
        return entitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class VH {
        ImageView icon;
        TextView time;
        TextView content;
        LinearLayout phone_opt;
        ImageView callback;
        ImageView smsback;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        VH vh = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item, null);
            vh = new VH();
            vh.icon = (ImageView) convertView.findViewById(R.id.picon);
            vh.time = (TextView) convertView.findViewById(R.id.time);
            vh.content = (TextView) convertView.findViewById(R.id.content);
            vh.phone_opt = (LinearLayout) convertView.findViewById(R.id.phone_opt);
            vh.callback = (ImageView) convertView.findViewById(R.id.callback);
            vh.smsback = (ImageView) convertView.findViewById(R.id.smsback);

            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }

        final JSONObject data = JSONObject.parseObject(getItem(position).getJdata());
        String upload_type = data.getString("upload_type");
        vh.time.setText(TimeFormater.getSuitableDateTime(data.getLong("time")));
        if (upload_type.equals("sms")) {
            vh.icon.setImageResource(R.mipmap.sms);
            vh.phone_opt.setVisibility(View.VISIBLE);
            vh.content.setText(
                    "发件人:" + getPep(data.getString("phone")) + "\n" +
                            "短信时间:" + data.getString("receive_time") + "\n" +
                            "内容:" + data.getString("content") + "\n" +
                            "类型:" + data.getString("type")
            );
        } else if (upload_type.equals("phone")) {
            vh.icon.setImageResource(R.mipmap.twelvekeydialer);
            vh.phone_opt.setVisibility(View.VISIBLE);
            vh.content.setText(getPep(data.getString("phone"))+"远程来电");
        }else if(upload_type.equals("test")){
            vh.icon.setImageResource(R.mipmap.test);
            vh.phone_opt.setVisibility(View.GONE);
            vh.content.setText("远程测试");
        }

        vh.callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getString("phone").trim()));
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                activity.startActivity(intent);

            }
        });
        vh.smsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+data.getString("phone")));
                intent.putExtra("sms_body", "你好，现在不方便，稍后联系你。李振。");
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    private String getPep(String phone) {
        for (int i = 0; i < peps.size(); i++) {
            if(peps.get(i).getNumber().contains(phone)||phone.contains(peps.get(i).getNumber()))
                return peps.get(i).getName();
        }
        return phone;
    }
}
