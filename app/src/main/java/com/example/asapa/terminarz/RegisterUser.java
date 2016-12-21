package com.example.asapa.terminarz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUser extends AppCompatActivity {
    DataManagerImpl dataManager;
    EditText passET;
    EditText loginET;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        dataManager = new DataManagerImpl(this);

        btn = (Button) findViewById(R.id.btnRegister);
        loginET = (EditText) findViewById(R.id.loginEditText);
        passET = (EditText) findViewById(R.id.passEditText);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setLogin(loginET.getText().toString());
                user.setPassword(passET.getText().toString());
                dataManager.saveUser(user);
                Intent intent = new Intent(RegisterUser.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}
