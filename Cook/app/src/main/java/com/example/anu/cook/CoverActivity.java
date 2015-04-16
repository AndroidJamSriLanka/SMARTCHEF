package com.example.anu.cook;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class CoverActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        this.startActivity(getIntent());
        next();

    }


    public void next(){
        Thread logoTimer = new Thread(){
            @Override
            public void run() {

                try {

                    sleep(2500);

                    Intent i = new Intent("com.example.anu.SECONDPAGE");

                    startActivity(i);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                } finally {

                    finish();
                }

            }

        };

        logoTimer.start();
    }
}
