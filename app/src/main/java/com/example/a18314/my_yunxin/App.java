package com.example.a18314.my_yunxin;

import android.app.Application;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.util.NIMUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NIMClient.init(this, null, null);

        if (NIMUtil.isMainProcess(this)) {
            // 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
            NimUIKit.init(this);
        }
    }
    }

