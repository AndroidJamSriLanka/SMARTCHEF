package com.example.anu.cook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    EditText searchEdit;
    ListView listView;
    Button addButton;
    String seachBar;
    String s="";
    Boolean israndomResipy=false;
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEdit= (EditText) findViewById(R.id.searchIngredience);
        listView= (ListView) findViewById(R.id.listView);
        addButton= (Button) findViewById(R.id.addButton);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        listView.setAdapter(adapter);



    }


   public void addSearchButton(View view){
       seachBar=searchEdit.getText().toString();
       if(!seachBar.isEmpty()){
           listItems.add(String.valueOf(searchEdit.getText()));

           adapter.notifyDataSetChanged();
           searchEdit.setText(null);

           Toast.makeText(getApplicationContext(), "Successfully added to list", Toast.LENGTH_SHORT).show();
           Log.d("Search", String.valueOf(searchEdit.getText()+"added anusha"));

       }
       else {
           Toast.makeText(getApplicationContext(), "Add ingredience", Toast.LENGTH_SHORT).show();
           Log.d("Search", String.valueOf(searchEdit.getText() + "sameera else"));
       }


   }

    public void search(View view) {

        if(listItems.size()>0) {
            clickSearchButtonCorrectly();

        }
        else {
            AlertBox();
        }


    }

    public void setS(){
        for (int i = 0; i < listItems.size(); i++) {
            s=listItems.get(i)+"%2C"+s;

        }
    }

    public String getS(){
        return s;
    }


    public void AlertBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Go to random recipes");
        alertDialogBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        alertDialogBuilder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        israndomResipy=true;
                        clickSearchButtonCorrectly();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

   public void clickSearchButtonCorrectly(){
       final ProgressDialog dialog;
       dialog = ProgressDialog.show(this, "Please Wait", "Loading ", false, true);
       dialog.setCancelable(false);
       dialog.setInverseBackgroundForced(false);
       dialog.show();

       setS();

       Thread t = new Thread() {
           @Override
           public void run() {
               try {
                   sleep(100000);  //Delay of 10 seconds
                   dialog.hide();
               } catch (Exception e) {
               }
           }
       };
       t.start();
       Bundle simple_bundle = new Bundle();
       simple_bundle.putString("item1", getS());
        simple_bundle.putBoolean("item2",israndomResipy);

       Intent intent = new Intent(SearchActivity.this, RecipesList.class);
       intent.putExtras(simple_bundle);
       startActivity(intent);


   }



}
