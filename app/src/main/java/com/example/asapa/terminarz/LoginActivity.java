package com.example.asapa.terminarz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText loginEditText,passEditText;
    Button btnLogin;
    Button btnRegister;
    DataManagerImpl dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister2);
        dataManager = new DataManagerImpl(this);

        loadPreferences();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();

                String pass = passEditText.getText().toString();
                String login = loginEditText.getText().toString();
                String password = dataManager.searchPass(login);
                if(pass.equals(password)){
                    Intent intent = new Intent(LoginActivity.this,AllTasks.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterUser.class);
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
