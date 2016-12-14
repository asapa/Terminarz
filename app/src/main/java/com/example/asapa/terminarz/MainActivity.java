package com.example.asapa.terminarz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText loginEditText,passEditText;
    Button btnLogin;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        loadPreferences();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
                Intent intent = new Intent(MainActivity.this,ShowTask.class);
                startActivity(intent);
            }
        });

    }

    public void loadPreferences(){
        SharedPreferences mp = getSharedPreferences("MyPreference", Context.MODE_PRIVATE);
        loginEditText.setText(mp.getString("text",""));

    }

    public void savePreferences(){
        SharedPreferences mp = getSharedPreferences("MyPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mp.edit();
        String text = loginEditText.getText().toString();
        editor.putString("text",text);
        editor.commit();
    }
}
