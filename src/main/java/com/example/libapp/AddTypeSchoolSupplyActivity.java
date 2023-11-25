package com.example.libapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.libapp.database.DatabaseHelper;
import com.example.libapp.model.TypeSchoolSupply;

import java.lang.reflect.Type;

public class AddTypeSchoolSupplyActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName,edtDesc;
    private Button btnAdd;
    private TextView title;
    private boolean isAdd = true;
    private TypeSchoolSupply item;
    private DatabaseHelper db;
    private int id_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_school_supply);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            isAdd = true;
            edtDesc.setText("");
            edtName.setText("");
        }else{
            isAdd = false;
            item = (TypeSchoolSupply) bundle.get("TypeSupply");
            edtName.setText(item.getName());
            edtDesc.setText(item.getDesc());
            id_type = item.getId();
            title.setText("Chỉnh sửa loại dụng cụ");
            btnAdd.setText("Lưu thay đổi");
        }
        btnAdd.setOnClickListener(this);
    }

    public void init(){
        edtName = findViewById(R.id.edtName);
        edtDesc = findViewById(R.id.edtDesc);
        title = findViewById(R.id.titleTypeSupply);
        btnAdd = findViewById(R.id.btnAddTypeSupply);
        db = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnAddTypeSupply){
            if(isAdd){
                TypeSchoolSupply tmp = new TypeSchoolSupply(edtName.getText().toString(),edtDesc.getText().toString());
                db.addTypeSchoolSupply(tmp);
                setResult(RESULT_OK);
                finish();
            }else{
                TypeSchoolSupply tmp = new TypeSchoolSupply(id_type,edtName.getText().toString(),edtDesc.getText().toString());
                db.updateTypeSupply(tmp);
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}