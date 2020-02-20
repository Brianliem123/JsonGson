package com.fa.merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fa.merchant.adapter.CustomAdapter;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter customAdapter = new CustomAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialKomponen();
        generateAdapter();
        getData();
    }

    public void inisialKomponen(){
        recyclerView = findViewById(R.id.rv_main);
    }

    public void generateAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);
    }

    public void getData(){
        InputStream jsonFile = getResources().openRawResource(R.raw.data);
        String url = "http://192.168.6.221:81/api/products";
        AsyncTaskData asyncTaskData = new AsyncTaskData(customAdapter, MainActivity.this);
        asyncTaskData.execute(url);
    }
}


