package com.example.ajrowalahajro;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class adpNotf extends ArrayAdapter<String> {
    public Context context;
    ArrayList<String> name;
    ArrayList<String> date;
    ArrayList<String> needpic;
    ArrayList<String> description;
    ArrayList<Button> btnDonate;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();
    public adpNotf(Context c, ArrayList<String> name, ArrayList<String> date , ArrayList<String> needpic,
                ArrayList<String> description ) {
        //decleration data
        super(c, R.layout.listnotifications, R.id.name, name);
        this.context = c;
        this.name = name;
        this.date = date;
        this.needpic=needpic;
        this.description = description;
        //this.btnDonate=btnDonate;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inf.inflate(R.layout.listnotifications, parent, false);
        TextView name1 = (TextView) row.findViewById(R.id.name);
        TextView date1 = (TextView) row.findViewById(R.id.date);
        TextView description1 = (TextView) row.findViewById(R.id.description);
        ImageView needpic1=(ImageView)row.findViewById(R.id.notfpic) ;
        name1.setText(name.get(position));
        date1.setText(date.get(position));
        if(description.get(position).length()>35){
            description1.setText(description.get(position));}
        else{
            description1.setText(description.get(position));
        }
        if(needpic!=null){
            bit =ip.decode(needpic.get(position));

            needpic1.setImageBitmap(bit);}


        return row;
    }



}
