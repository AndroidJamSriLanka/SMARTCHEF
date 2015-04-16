package com.example.anu.cook;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CoursesSearchActivity extends ActionBarActivity {

    ListView listView;
    String webArddress;
    JSONObject joRecipy;
    String title;
    String response;
    String url;
    String numberOfCourses;
    String recipe_id_pass;
    String url1;
    ArrayList<String> coursesItem = new ArrayList<String>();
    ArrayList<String> urlList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    JSONObject jo;
    String courseName;
    JSONArray ja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_search);
        listView = (ListView) findViewById(R.id.listView2);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coursesItem);
        listView.setAdapter(adapter);


        webArddress = "http://api.pearson.com:80/kitchen-manager/v1/courses?limit=30";

        WebService webService = new WebService();
        try {

            response = webService.execute(webArddress).get();

            jo = new JSONObject(response);
            courseName = jo.getString("results");

            String count = jo.getString("total");

            ja = new JSONArray(courseName);


            for (int i = 0; i < ja.length(); i++) {
                joRecipy = (JSONObject) ja.get(i);
                title = joRecipy.getString("name");
                url=joRecipy.getString("url");
                numberOfCourses = joRecipy.getString("recipe_count");
                urlList.add(url);
                coursesItem.add(title + "            " + numberOfCourses);

                adapter.notifyDataSetChanged();
            }




        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }




    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        Intent i;
        @Override
        public void onItemClick (AdapterView < ? > arg0, View arg1,int position, long id){
        // TODO Auto-generated method stub

        url1=urlList.get(position);
        Bundle simple_bundle = new Bundle();
        simple_bundle.putString("item1", String.valueOf(position));
        simple_bundle.putString("item2",url1);
        Intent intent = new Intent(CoursesSearchActivity.this, CourseSpecificRecipes.class);
        intent.putExtras(simple_bundle);
        startActivity(intent);

    }
    }

    );

}

}

