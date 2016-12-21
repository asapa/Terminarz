package com.example.asapa.terminarz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;


public class SettingsActivity extends AppCompatActivity {

    Button btnPL;
    Button btnENG;
    Button btnRU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnPL= (Button) findViewById(R.id.buttonPL);
        btnENG= (Button) findViewById(R.id.buttonENG);
        btnRU= (Button) findViewById(R.id.buttonRU);

        View.OnClickListener OnClickListener = new View.OnClickListener( ){
            @Override
            public void onClick(View view){
                String lang="pl";
                    switch(view.getId()){
                        case R.id.buttonPL:
                            lang="pl";
                            break;
                        case R.id.buttonENG:
                            lang="eng";
                            break;
                        case R.id.buttonRU:
                            lang="ru";
                            break;
                    }
                setAplicationLanguage(lang);

                SharedPreferences langPref = getSharedPreferences("LangPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = langPref.edit();
                editor.putString("lang",lang);
                editor.commit();

                Intent intent = new Intent(SettingsActivity.this,AllTasks.class);
                startActivity(intent);

            }
        };

        btnPL.setOnClickListener(OnClickListener);
        btnENG.setOnClickListener(OnClickListener);
        btnRU.setOnClickListener(OnClickListener);
    }


    private void setAplicationLanguage(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
