package com.example.a18314.my_yunxin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        login(username,password);
    }
    private void login(String name,String pass){
        NimUIKit.login(new LoginInfo(name, pass), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                // 进入主界面
                startActivity(new Intent(MainActivity.this,FragmentActivity.class));
                finish();
            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }
}
