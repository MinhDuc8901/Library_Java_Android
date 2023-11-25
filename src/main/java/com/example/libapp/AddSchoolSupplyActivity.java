package com.example.libapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libapp.adapter.AdapterRecycle;
import com.example.libapp.adapter.AdapterSupply;
import com.example.libapp.database.DatabaseHelper;
import com.example.libapp.model.SchoolSupply;
import com.example.libapp.model.TypeSchoolSupply;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddSchoolSupplyActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtName,edtAge,edtImage,edtPrice;
    Spinner spinner;
    Button btnAdd,addImage;
    ImageView imageView;

    private boolean isAdd = true;
    private SchoolSupply item;
    private DatabaseHelper db;
    List<TypeSchoolSupply> list;
    TextView title;

    boolean is_permision = false;
    String value_spinner = "";
    int update_id = 0;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school_supply);
        init();
        list = db.getListTypeSchoolSupply();


        // Kiểm tra quyền đọc bộ nhớ
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Nếu quyền chưa được cấp, yêu cầu quyền
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {
            // Nếu quyền đã được cấp, mở Intent chọn ảnh
//            openGallery();
            is_permision = true;
        }


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            isAdd = true;
            edtName.setText("");
            edtAge.setText("");
//            edtImage.setText("");
            edtPrice.setText("");
            initSpinner(list,true,-1);
        }else{
            isAdd = false;

            item = (SchoolSupply) bundle.get("TypeSupply");
            update_id = item.getId();
            initSpinner(list,false,item.getType_id());
            edtName.setText(item.getName());
            edtAge.setText(item.getAges());
//            edtImage.setText(item.getImage());
            // Chuyển đổi chuỗi Base64 thành Bitmap
            Bitmap bitmap = decodeBase64(item.getImage());

            // Hiển thị Bitmap trong ImageView
            imageView.setImageBitmap(bitmap);
            edtPrice.setText(String.valueOf(item.getPrice()));
            title.setText("Chỉnh sửa loại dụng cụ");
            btnAdd.setText("Lưu thay đổi");
        }
        imageView.setDrawingCacheEnabled(true);
        btnAdd.setOnClickListener(this);
        addImage.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedValue = (String) adapterView.getItemAtPosition(i);
                String [] temp = selectedValue.split("-");
                value_spinner = temp[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private Bitmap decodeBase64(String base64) {
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void initSpinner(List<TypeSchoolSupply> list,boolean is_add,int id ){
        List<String> listString = new ArrayList<>();
        if(is_add) listString.add("Chọn loại đồ dùng");
        int position = 0;
        int count = 0;
        for(TypeSchoolSupply item : list){
            listString.add(String.valueOf(item.getId())+"-"+item.getName());
            if(id!=-1){
                if(id == item.getId()) position = count;
            }
            count+=1;
        }
        // Tạo ArrayAdapter sử dụng danh sách dữ liệu và layout mặc định cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listString);

        // Thiết lập layout cho danh sách dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán ArrayAdapter vào Spinner
        spinner.setAdapter(adapter);
        if(id != -1){
            spinner.setSelection(position);
        }
    }

    private void openGallery() {
        // Mở Intent để chọn ảnh từ thiết bị
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    void init(){
        edtName = findViewById(R.id.edtName1);
        edtAge = findViewById(R.id.edtAges);
//        edtImage = findViewById(R.id.edtImage);
        edtPrice = findViewById(R.id.edtPrice);
        spinner = findViewById(R.id.spType);
        btnAdd = findViewById(R.id.btnAddSupply);
        title = findViewById(R.id.tvTitle);
        addImage = findViewById(R.id.addImage);
        imageView = findViewById(R.id.image);
        db = new DatabaseHelper(this);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnAddSupply){
            if(isAdd){
                String image = bitmapToBase64(Bitmap.createBitmap(imageView.getDrawingCache()));
//                imageView.setDrawingCacheEnabled(false);
                if(value_spinner.equals("Chọn loại đồ dùng")){
                    Toast.makeText(this, "Vui lòng chọn loại đồ dùng", Toast.LENGTH_SHORT).show();
                    return;
                }
                SchoolSupply tmp = new SchoolSupply(edtName.getText().toString(),Integer.parseInt(value_spinner),edtAge.getText().toString(),image,Double.parseDouble(edtPrice.getText().toString()));
                db.addSchoolSupply(tmp);
                setResult(RESULT_OK);
                finish();
            }else{
                String image = bitmapToBase64(Bitmap.createBitmap(imageView.getDrawingCache()));
//                imageView.setDrawingCacheEnabled(false);
                SchoolSupply tmp = new SchoolSupply(update_id,edtName.getText().toString(),Integer.parseInt(value_spinner),edtAge.getText().toString(),image,Double.parseDouble(edtPrice.getText().toString()));
                db.updateSchoolSupplyById(tmp);
                setResult(RESULT_OK);
                finish();
            }
        }
        if(view.getId() == R.id.addImage){
//            Log.d( "test","test: ");
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Nhận địa chỉ URI của ảnh đã chọn
            Uri selectedImageUri = data.getData();

            // Hiển thị ảnh trong ImageView
            imageView.setImageURI(selectedImageUri);
            imageView.setDrawingCacheEnabled(true);
        }
    }

}