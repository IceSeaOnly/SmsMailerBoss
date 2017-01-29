package com.qdxiaogutou.eye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class settings extends AppCompatActivity {

    private Button toyidong;
    private Button toliantong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toyidong = (Button) findViewById(R.id.toyidong);
        toliantong = (Button) findViewById(R.id.toliantong);

        toyidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMailto("17854258196");
                finish();
            }
        });
        toliantong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMailto("17664051817");
                finish();
            }
        });
    }

    private void changeMailto(String s) {
        RequestParams p = new RequestParams();
        p.add("pass","bh1041414957");
        p.add("to",s);
        HttpUtil.get("http://mobile.binghai.site/mailto.do",p,new JsonHttpResponseHandler());
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
