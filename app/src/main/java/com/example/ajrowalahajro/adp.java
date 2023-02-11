package com.example.ajrowalahajro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adp extends ArrayAdapter<String> {
    public Context context;
    ArrayList<String> title;
    ArrayList<String> date;
    ArrayList<String> needpic;
    ArrayList<String> address;
    ArrayList<String> description;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();
    public adp(Context c, ArrayList<String> title, ArrayList<String> date , ArrayList<String> needpic,
               ArrayList<String> address, ArrayList<String> description ) {
        //decleration data
        super(c, R.layout.listneeds, R.id.title, title);
        this.context = c;
        this.title = title;
        this.date = date;
        this.needpic=needpic;
        this.address = address;
        this.description = description;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inf.inflate(R.layout.listneeds, parent, false);
        TextView title1 = (TextView) row.findViewById(R.id.title);
        TextView date1 = (TextView) row.findViewById(R.id.date);
        TextView address1 = (TextView) row.findViewById(R.id.address);
        TextView description1 = (TextView) row.findViewById(R.id.description);
        ImageView needpic1=(ImageView)row.findViewById(R.id.needpic) ;
        needpic1.setVisibility(View.GONE);
        title1.setText(title.get(position));
        date1.setText(date.get(position));
        address1.setText(address.get(position));
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
