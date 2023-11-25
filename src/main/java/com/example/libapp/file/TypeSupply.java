package com.example.libapp.file;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.libapp.model.TypeSchoolSupply;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TypeSupply {
    File internal ;
    public TypeSupply(Context context){
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File path = contextWrapper.getDir("manager",ContextWrapper.MODE_PRIVATE);
        internal = new File(path,"type_supply");
    }

    public void addTypeSupply(TypeSchoolSupply typeSchoolSupply){
        try {
            FileOutputStream os = new FileOutputStream(internal);
            os.write((typeSchoolSupply.toString()+"\n").getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<TypeSchoolSupply> getList(){
        List<TypeSchoolSupply> list = new ArrayList<>();
        try {
            FileInputStream os = new FileInputStream(internal);
            DataInputStream di = new DataInputStream(os);
            BufferedReader bf = new BufferedReader(new InputStreamReader(di));
            String a = "";
            while ((a = bf.readLine())!=null){
                String [] tmp = a.split("-");
                list.add(new TypeSchoolSupply(Integer.parseInt(tmp[0]),tmp[1],tmp[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
