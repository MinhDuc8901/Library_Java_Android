package com.example.libapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.libapp.adapter.AdapterRecycle;
import com.example.libapp.database.DatabaseHelper;
import com.example.libapp.file.TypeSupply;
import com.example.libapp.model.TypeSchoolSupply;

import java.util.ArrayList;
import java.util.List;

public class TypeSchoolSupplyActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAdd;
    RecyclerView recyclerView;
    List<TypeSchoolSupply> list = new ArrayList<>();
    DatabaseHelper db;
    AdapterRecycle adapterRecycle;
    SharedPreferences preferences;
    TypeSupply file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_school_supply);
        init();
        list = db.getListTypeSchoolSupply();
//        list.add(new TypeSchoolSupply(1,"Pencil","but chi"));
//        list.add(new TypeSchoolSupply(2,"Ink","Muc"));
        // khởi tạo SharedPreferences và lưu dữ liệu
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("category",new TypeSchoolSupply(1,"Pencil","but chi").toString());
//        editor.apply();
//        // lấy dữ liệu trong SharedPreferences
//        String category = preferences.getString("category","Defaul value");
//        Log.d("SharedPreferences", "category: " + category);

        // Khởi tạo file và lấy dữ liệu trong file
//        file = new TypeSupply(this);
//        list = file.getList();
        setupRecycle();
        btnAdd.setOnClickListener(this);
    }

    public void init(){
        btnAdd = findViewById(R.id.btnAddTypeSchoolSupply);
        recyclerView = findViewById(R.id.recycleview2);
        db = new DatabaseHelper(this);
        preferences = getSharedPreferences("my_lib", Context.MODE_PRIVATE);

    }

    private void setupRecycle(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        adapterRecycle = new AdapterRecycle(list,this,db);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterRecycle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddTypeSchoolSupply:
                Intent intent = new Intent(this,AddTypeSchoolSupplyActivity.class);
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
                list = db.getListTypeSchoolSupply();
                adapterRecycle.setList(list);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == 10002){
            try {
                list = db.getListTypeSchoolSupply();
                adapterRecycle.setList(list);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}