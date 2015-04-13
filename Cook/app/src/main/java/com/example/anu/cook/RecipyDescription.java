package com.example.anu.cook;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class RecipyDescription extends ActionBarActivity {

    String recipe_id;
    String webAddress;
    String response;
    String directionResponce;
    JSONObject joIngre;
    String ingrediance="";
    String getMeth;
    String url;
    String dir="";
    Boolean isRandomRecipy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipy_description);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        recipe_id=my_bundle_received.get("item1").toString();
        getMeth=my_bundle_received.get("item2").toString();
        isRandomRecipy= (Boolean) my_bundle_received.get("item3");
        Log.d("Value", "--" + my_bundle_received.get("item1").toString());



        TextView titleTextView= (TextView) findViewById(R.id.title);
        TextView ingredianceTextView= (TextView) findViewById(R.id.ingrediance);
        TextView discription= (TextView) findViewById(R.id.description);
        TextView urlForWeb= (TextView) findViewById(R.id.link);


        if(isRandomRecipy==true){
            webAddress="http://api.pearson.com/kitchen-manager/v1/recipes?&limit=500";
        }

        else{
            webAddress="http://api.pearson.com/kitchen-manager/v1/recipes?ingredients-any="+ getMeth+"&limit=150";
        }



        WebService webService = new WebService();
        WebService directionWeb=new WebService();
        try {

            response= webService.execute(webAddress).get();

            JSONObject jo = new JSONObject(response);
            // txView.setText(jo.getString("recipes"));
            String recipy = jo.getString("results");
            JSONArray ja = new JSONArray(recipy);

                joIngre = (JSONObject) ja.get(Integer.parseInt(recipe_id));

                String ingre = joIngre.getString("ingredients");
                JSONArray ingryArray = new JSONArray(ingre);
                for (int j = 0; j < ingryArray.length(); j++) {
                    ingrediance = (String) ingryArray.get(j)+"\n"+ingrediance;

                }



            titleTextView.setText(joIngre.getString("name"));
            Log.d("ingredients in array",ingrediance);

            ingredianceTextView.setText(ingrediance);


           //discription.setText(joIngre.getString("url"));
            url=joIngre.getString("url");


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


        Intent intent = new Intent(RecipyDescription.this, WebSite.class);
        intent.putExtras(simple_bundle);
        startActivity(intent);
    }

}
