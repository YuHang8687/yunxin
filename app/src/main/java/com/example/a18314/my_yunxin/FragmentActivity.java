package com.example.a18314.my_yunxin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;

import java.util.ArrayList;

public class FragmentActivity extends AppCompatActivity {
    private TabLayout tabTitle;
    private ViewPager pager;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private EditText GoUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        tabTitle = (TabLayout) findViewById(R.id.tab_title);
        pager = (ViewPager) findViewById(R.id.pager);
        initview();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.select, null);
        GoUserId = view.findViewById(R.id.GoUserId);
        builder.setView(view);
        builder.setTitle("请在下方输入要进行会话的用户名：");
        builder.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 打开单聊界面
                NimUIKit.startP2PSession(FragmentActivity.this, GoUserId.getText().toString().trim());
//                NimUIKit.startTeamSession(FragmentActivity.this, "回不去的青春");
            }

        });
        builder.show();
    }

    private void initview() {
        tabTitle.setupWithViewPager(pager);
        arrayList.add(new RecentContactsFragment());
        arrayList.add(new ContactsFragment());

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] title = {"会话", "联系人", "动态"};

            @Override
            public Fragment getItem(int position) {
                return arrayList.get(position);
            }

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        };
        pager.setAdapter(fragmentPagerAdapter);
    }
}
