package com.example.anu.cook;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CourseSpecificRecipes extends ActionBarActivity {

    ListView listView;
    String response;
    String url;
    String position;
    JSONObject joIngre;
    JSONObject joRecipy;
    String recipe;
    String url1;
    ArrayList<String> recipylistItems=new ArrayList<String>();
    ArrayList<String> urlList=new ArrayList<>();
    ArrayAdapter<String> recipyadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_specific_recipes);

        listView= (ListView) findViewById(R.id.listView3);
        recipyadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recipylistItems);
        listView.setAdapter(recipyadapter);
        TextView txtView = (TextView) findViewById(R.id.textView8);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        position=my_bundle_received.get("item1").toString();
        url=my_bundle_received.get("item2").toString();
        Log.d("Value", "--" + my_bundle_received.get("item2").toString());


        WebService webService = new WebService();

        try {

            response = webService.execute(url+"?limit=40").get();

            JSONObject jo = new JSONObject(response);
            String courseName = jo.getString("recipes");
            JSONArray ja = new JSONArray(courseName);


            for (int i = 0; i < ja.length(); i++) {
                joRecipy = (JSONObject) ja.get(i);
                recipe = joRecipy.getString("name");
                recipylistItems.add(recipe);
                url=joRecipy.getString("url");
                urlList.add(url);
                recipyadapter.notifyDataSetChanged();

            }





            //discription.setText(joIngre.getString("url"));

            if (recipylistItems.size() == 0) {
                txtView.setText("Recipes are not available");

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
        Intent intent = new Intent(CourseSpecificRecipes.this, CourseRecipeDirections.class);
        intent.putExtras(simple_bundle);
        startActivity(intent);

    }
    }

    );

}

}
