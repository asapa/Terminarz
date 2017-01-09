package com.example.asapa.terminarz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    EditText loginEditText,passEditText;
    Button btnLogin;
    Button btnRegister;
    Button btnSeeDetals;
    boolean seeDeetailsFlag;
    Fragment fragReklama;

    FragmentTransaction frTrans;
    DataManagerImpl dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setAplicationLanguage();

        setContentView(R.layout.activity_main);
        loginEditText = (EditText) findViewById(R.id.loginEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister2);
        btnSeeDetals= (Button) findViewById(R.id.buttonDetails);

        seeDeetailsFlag=false;

        fragReklama=new FragmentReklama();
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
                    Toast.makeText(LoginActivity.this,getApplicationContext().getString(R.string.login_error), Toast.LENGTH_SHORT).show();
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


        btnSeeDetals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                frTrans= getFragmentManager().beginTransaction();

                if(!seeDeetailsFlag && frTrans.isEmpty()) {
                    frTrans.add(R.id.frameLayoutReklama, fragReklama);
                    btnSeeDetals.setText(getApplicationContext().getString(R.string.hide_details));
                    seeDeetailsFlag=true;
                }
                else{
                    Fragment reklama=getFragmentManager().findFragmentById(R.id.frameLayoutReklama);
                    frTrans.remove(reklama);
                    btnSeeDetals.setText(getApplicationContext().getString(R.string.btn_see_details));
                    seeDeetailsFlag=false;
                }
                frTrans.commit();

            }
        });
    }


    private void setAplicationLanguage() {
        String languageToLoad  = "pl";

        SharedPreferences mp = getSharedPreferences("LangPref", Context.MODE_PRIVATE);
        languageToLoad=mp.getString("lang","pl");


        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
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
