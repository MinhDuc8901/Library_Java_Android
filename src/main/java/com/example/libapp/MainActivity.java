package com.example.libapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.libapp.model.TypeSchoolSupply;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSchoolSupply, btnTypeSchoolSupply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSchoolSupply = findViewById(R.id.btnSchoolSupply);
        btnTypeSchoolSupply = findViewById(R.id.btnTypeSchoolSupply);
        btnSchoolSupply.setOnClickListener(this);
        btnTypeSchoolSupply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSchoolSupply:
                Intent intent1 = new Intent(this,SchoolSupplyActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnTypeSchoolSupply:
                Intent intent2 = new Intent(this, TypeSchoolSupplyActivity.class);
                startActivity(intent2);
                break;
        }
    }
}