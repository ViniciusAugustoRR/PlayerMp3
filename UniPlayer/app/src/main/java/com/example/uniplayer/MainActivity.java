package com.example.uniplayer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Runnable{
    private Button btn_toMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(this , 1000);

    }


    @Override
    public void run() {
        toMenu();
    }

    protected void toMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);

    }
}
