package com.example.anu.cook;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CourseRecipeDirections extends ActionBarActivity {

    String recipe_id;
    String webAddress;
    String response;
    String directionResponce;
    JSONObject joIngre;
    String ingrediance="";
    String getMeth;
    String url;
    String dir="";

    String ingre="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_recipe_directions);
        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        recipe_id=my_bundle_received.get("item1").toString();
        url=my_bundle_received.get("item2").toString();
        Log.d("Value", "--" + my_bundle_received.get("item2").toString());



        TextView titleTextView= (TextView) findViewById(R.id.title);
        TextView ingredianceTextView= (TextView) findViewById(R.id.ingrediance);
        TextView discription= (TextView) findViewById(R.id.description);


        WebService webService = new WebService();
        WebService directionWeb=new WebService();
        try {

            response= webService.execute(url).get();

            JSONObject jo = new JSONObject(response);
            String recipy = jo.getString("ingredients");

            titleTextView.setText(jo.getString("name"));
            JSONArray ja = new JSONArray(recipy);
            ArrayList<String> ingryArray = new ArrayList<>();
            for (int i = 0; i <ja.length() ; i++) {
                joIngre = (JSONObject) ja.get(i);
               ingre= joIngre.getString("name");

                    ingryArray.add(ingre);

                for(int j=0;j<ingryArray.size()-1;j++) {
                    ingre = ingryArray.get(j)+"\n" + ingre;

                }

            }






            new DownloadImageTask((ImageView) findViewById(R.id.imageButton))
                    .execute(jo.getString("image"));
            Log.d("ingredients in array",ingre);

            ingredianceTextView.setText(ingre);


            //discription.setText(joIngre.getString("url"));
          //  url=joIngre.getString("url");


            if(url==null){
                discription.setText("Directions not found");
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try{


            directionResponce=directionWeb.execute(url).get();
            JSONObject jo = new JSONObject(directionResponce);

            String directions = jo.getString("directions");

            JSONArray ja = new JSONArray(directions);



            for (int i = ja.length()-1; i >=0 ; i--) {
                dir= (String) ja.get(i)+"\n"+dir;
            }

            discription.setText(dir);


        } catch (InterruptedException e) {
            Log.d("catch ","catch ");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("catch ","catch ");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("catch ","catch ");
        }
}

    public void  onUrlClick(View view){

        Log.d("url clicked","url clicked");
        Bundle simple_bundle = new Bundle();
        simple_bundle.putString("item1", url);


        Intent intent = new Intent(CourseRecipeDirections.this, WebSite.class);
        intent.putExtras(simple_bundle);
        startActivity(intent);
    }


}
