package com.example.libapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.libapp.adapter.AdapterSupply;
import com.example.libapp.database.DatabaseHelper;
import com.example.libapp.file.Supply;
import com.example.libapp.model.SchoolSupply;
import com.example.libapp.model.TypeSchoolSupply;

import java.util.ArrayList;
import java.util.List;

public class SchoolSupplyActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAdd;
    RecyclerView recyclerView;
    List<SchoolSupply> list = new ArrayList<>();
    DatabaseHelper db;
    AdapterSupply adapterSupply;
    Supply file;
    EditText search;
    Button btnSearch;
    Spinner filter;
    List<TypeSchoolSupply> type_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_supply);
        init();
        initSpinner();
        list = db.getListSchoolSupply();
//        file = new Supply(this);
//        list = file.getList();
        setupRecycle();
        btnAdd.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmp = (String) adapterView.getItemAtPosition(i);
                if(tmp.equals("Tất cả")){
                    list = db.getListSchoolSupply();
                    adapterSupply.setList(list);
                }else{
                    String [] sp = tmp.split(" - ");
                    list = db.getSchoolSupplyByTypeSchool(Integer.parseInt(sp[0]));
                    adapterSupply.setList(list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void init(){
        btnAdd = findViewById(R.id.btnAddSchoolSupply);
        recyclerView = findViewById(R.id.recycleview1);
        search = findViewById(R.id.search);
        btnSearch = findViewById(R.id.btnSearch);
        filter = findViewById(R.id.spinner_search);
        db = new DatabaseHelper(this);

    }
    private void initSpinner(){
        type_list = db.getListTypeSchoolSupply();
        List<String> sList = new ArrayList<>();
        sList.add("Tất cả");
        for(TypeSchoolSupply item : type_list){
            sList.add(String.valueOf(item.getId())+" - "+item.getName());
        }
        // Tạo ArrayAdapter sử dụng danh sách dữ liệu và layout mặc định cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sList);

        // Thiết lập layout cho danh sách dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter vào Spinner
        filter.setAdapter(adapter);
//        filter.setSelection(0);

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
            case R.id.btnSearch:
                String [] sp = search.getText().toString().split("-");
                if(sp.length == 3){
                    filter.setSelection(0);
                    list = db.getSchoolSupplyByAgeOrPrice(sp[0].trim(),sp[1].trim(),sp[2].trim());
                    List<SchoolSupply> temp = new ArrayList<>();
                    for(SchoolSupply item : list){
                        if(Integer.parseInt(sp[0].trim())<Integer.parseInt(item.getAges())){
                            temp.add(item);
                        }
                    }
                    adapterSupply.setList(temp);
                }else{
                    Toast.makeText(this, "Vui lòng nhập đầy đủ các trường!", Toast.LENGTH_SHORT).show();
                }
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