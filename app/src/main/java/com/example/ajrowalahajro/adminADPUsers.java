package com.example.ajrowalahajro;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class adminADPUsers extends ArrayAdapter<String> {
    public Context context;
    ArrayList<String> name;
    ArrayList<String> pic;
    //TextView t1, d1;//t1=title  d1=describtion
    Bitmap bit;

    ImageProcess ip=new ImageProcess();

    public adminADPUsers(Context c, ArrayList<String> name,  ArrayList<String> pic) {
        //decleration data
        super(c, R.layout.admin_list_users, R.id.username, name);
        this.context = c;
        this.name = name;
        this.pic = pic;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inf.inflate(R.layout.admin_list_users, parent, false);
        TextView name1 = (TextView) row.findViewById(R.id.username);
        ImageView pic1 = (ImageView) row.findViewById(R.id.userpic);
        name1.setText(name.get(position));
        if(pic!=null){
            bit =ip.decode(pic.get(position));
            pic1.setImageBitmap(bit);}


        return row;
    }

}
