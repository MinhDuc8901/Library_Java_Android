package com.example.libapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.libapp.adapter.AdapterSupply;
import com.example.libapp.database.DatabaseHelper;
import com.example.libapp.file.Supply;
import com.example.libapp.model.SchoolSupply;

import java.util.ArrayList;
import java.util.List;

public class SchoolSupplyActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAdd;
    RecyclerView recyclerView;
    List<SchoolSupply> list = new ArrayList<>();
    DatabaseHelper db;
    AdapterSupply adapterSupply;
    Supply file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_supply);
        init();

        list = db.getListSchoolSupply();
//        file = new Supply(this);
//        list = file.getList();
        setupRecycle();
        btnAdd.setOnClickListener(this);
    }
    void init(){
        btnAdd = findViewById(R.id.btnAddSchoolSupply);
        recyclerView = findViewById(R.id.recycleview1);
        db = new DatabaseHelper(this);

    }

    private void setupRecycle(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        adapterSupply = new AdapterSupply(list,this,db);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterSupply);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddSchoolSupply:
                Intent intent = new Intent(this,AddSchoolSupplyActivity.class);
//                intent.putExtra("TypeSupply", new TypeSchoolSupply());
                startActivityForResult(intent,10001);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10001){
            try {
                list = db.getListSchoolSupply();
                adapterSupply.setList(list);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == 10002){
            try {
                list = db.getListSchoolSupply();
                adapterSupply.setList(list);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}