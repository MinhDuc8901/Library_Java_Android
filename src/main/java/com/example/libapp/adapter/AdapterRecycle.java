package com.example.libapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libapp.AddSchoolSupplyActivity;
import com.example.libapp.AddTypeSchoolSupplyActivity;
import com.example.libapp.R;
import com.example.libapp.database.DatabaseHelper;
import com.example.libapp.model.TypeSchoolSupply;

import java.util.List;

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.ViewHolder>{
    private Context context;
    private List<TypeSchoolSupply> list;
    private DatabaseHelper db;

    public AdapterRecycle(List<TypeSchoolSupply> list,Context context,DatabaseHelper db){
        this.context = context;
        this.list = list;
        this.db = db;
    }

    public void setList(List<TypeSchoolSupply> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typesupply,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TypeSchoolSupply typeSchoolSupply = list.get(position);
        if(typeSchoolSupply == null){
            return ;
        }
        holder.tvDesc.setText(typeSchoolSupply.getDesc());
        holder.tvName.setText(typeSchoolSupply.getName());
        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTypeSchoolSupplyActivity.class);
                intent.putExtra("TypeSupply", typeSchoolSupply); // Truyền dữ liệu cần chỉnh sửa
                ActivityCompat.startActivityForResult((Activity) context,intent,10002,null);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteTypeSchoolSupplyById(typeSchoolSupply.getId());
                list = db.getListTypeSchoolSupply();
                setList(list);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvDesc;
        private Button btnDelete,btnModify;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnModify = itemView.findViewById(R.id.btnModify);
        }
    }
}
