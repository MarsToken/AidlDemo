package com.example.weatherlib;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("service", "start success!");
    }

    public void launch(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setPackage("com.autopai.carcenter");
        context.startActivity(intent);
    }
}
