package com.example.anu.cook;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SecondPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        Button course= (Button) findViewById(R.id.coursesButton);
        Button cuisins = (Button) findViewById(R.id.coursesButton);
        Button recipes= (Button) findViewById(R.id.recipesButton);


    }
    public void recipyClick(View view){
        Intent intent=new Intent(SecondPage.this,SearchActivity.class);
        startActivity(intent);
    }

    public void courseClick(View view){
        Intent intent=new Intent(SecondPage.this,CoursesSearchActivity.class);
        startActivity(intent);
    }

    public void cuisinesClick(View view){
        Intent intent=new Intent(SecondPage.this,CuisinesSearchActivity.class);
        startActivity(intent);
    }

}
