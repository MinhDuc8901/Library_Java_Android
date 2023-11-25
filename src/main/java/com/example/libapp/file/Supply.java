package com.example.libapp.file;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.libapp.model.SchoolSupply;
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

public class Supply {
    File internal ;
    public Supply(Context context){
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File path = contextWrapper.getDir("manager",ContextWrapper.MODE_PRIVATE);
        internal = new File(path,"supply");
    }

    public void addSupply(SchoolSupply schoolSupply){
        try {
            FileOutputStream os = new FileOutputStream(internal);
            os.write((schoolSupply.toString()+"\n").getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SchoolSupply> getList(){
        List<SchoolSupply> list = new ArrayList<>();
        try {
            FileInputStream os = new FileInputStream(internal);
            DataInputStream di = new DataInputStream(os);
            BufferedReader bf = new BufferedReader(new InputStreamReader(di));
            String a = "";
            while ((a = bf.readLine())!=null){
                String [] tmp = a.split("-");
                list.add(new SchoolSupply(Integer.parseInt(tmp[0]),tmp[1],Integer.parseInt(tmp[2]),tmp[3],
                        tmp[4],Double.parseDouble(tmp[5])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
