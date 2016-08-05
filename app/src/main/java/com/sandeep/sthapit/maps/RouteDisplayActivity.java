package com.sandeep.sthapit.maps;


import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class RouteDisplayActivity extends AppCompatActivity {
    TextView tv_from;
    TextView tv_to;
    TextView tv_list;
    EditText et_from;
    EditText et_to;
    DatabaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);
        init();
    }

    public void init(){
        myDbHelper = new DatabaseHelper(this);
        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }

    }

    public void show_routes(View view) {
        tv_list = (TextView) findViewById(R.id.tv_query_route_list);
        et_from = (EditText) findViewById(R.id.et_query_from);
        et_to = (EditText) findViewById(R.id.et_query_to);
        ArrayList<String> b = myDbHelper.showList(et_from.getText().toString(), et_to.getText().toString());
        String m = "";
        for(int i =0; i<b.size(); i++){
            m = m + b.get(i) + "\n";
        }
        tv_list.setText(m);
    }

    public void go_to_map(View view) {
        Intent intent = new Intent(RouteDisplayActivity.this, MainActivity.class);

        Toast.makeText(view.getContext(), "Map Location", Toast.LENGTH_SHORT).show();
        RouteDisplayActivity.this.startActivity(intent);
    }

}
