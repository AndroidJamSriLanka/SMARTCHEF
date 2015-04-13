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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class RecipesList extends ActionBarActivity {

    ListView listView;
    String title;
    String recipe_id_pass;
    JSONObject joRecipy;
    String webAddress;
    String getMethod;
    String response;
    TextView txtView;
    TextView countTxt;
    Boolean isRandomRecipy;


    ArrayList<String> recipylistItems=new ArrayList<String>();
    ArrayList<String> recipe_idListItem=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> recipyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        listView= (ListView) findViewById(R.id.listViewRecipes);
        txtView= (TextView) findViewById(R.id.textView5);
        countTxt= (TextView) findViewById(R.id.textView6);

        recipyadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,recipylistItems);
        listView.setAdapter(recipyadapter);
        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        getMethod=my_bundle_received.get("item1").toString();
        isRandomRecipy= (Boolean) my_bundle_received.get("item2");
        Log.d("Value","--"+my_bundle_received.get("item1").toString());

        if(isRandomRecipy==true){
               webAddress="http://api.pearson.com/kitchen-manager/v1/recipes?&limit=500";
        }

        else{
            webAddress="http://api.pearson.com/kitchen-manager/v1/recipes?ingredients-any="+ getMethod+"&limit=150";
        }


           WebService webService = new WebService();
                try {

                    response = webService.execute(webAddress ).get();

                    JSONObject jo = new JSONObject(response);
                    // txView.setText(jo.getString("recipes"));
                    String recipy = jo.getString("results");

                    String count=jo.getString("total");
                    countTxt.setText(count+" ");
                    JSONArray ja = new JSONArray(recipy);
                    //txView.setText(ja.length()+"");


                    for (int i = 0; i < ja.length(); i++) {
                        joRecipy = (JSONObject) ja.get(i);
                        title = joRecipy.getString("name");
                        recipylistItems.add(title);
                        recipyadapter.notifyDataSetChanged();
                    }


                    if (recipylistItems.size() == 0) {
                        txtView.setText("Recipes are not available");

                    }

                    //txView.setText(title);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent i;
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {
                // TODO Auto-generated method stub

                recipe_id_pass=recipylistItems.get(position);

                Bundle simple_bundle=new Bundle();
                simple_bundle.putString("item1", String.valueOf(position));
                simple_bundle.putString("item2",getMethod);
                simple_bundle.putBoolean("item3",isRandomRecipy);
                Intent intent=new Intent(RecipesList.this,RecipyDescription.class);
                intent.putExtras(simple_bundle);
                startActivity(intent);

            }
        });



    }
}
