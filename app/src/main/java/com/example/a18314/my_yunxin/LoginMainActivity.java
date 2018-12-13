package com.example.a18314.my_yunxin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginMainActivity extends AppCompatActivity {
    private EditText name;
    private EditText pass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, MainActivity.class);
                intent.putExtra("username",name.getText().toString().trim());
                intent.putExtra("password",pass.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
