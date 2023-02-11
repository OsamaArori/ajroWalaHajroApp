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
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import java.util.ArrayList;

public class adpHistory extends ArrayAdapter<String> {

    public Context context;
    ArrayList<String> address;
    ArrayList<String> title;
    ArrayList<String> pic;
    ArrayList<String> date;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();
    public adpHistory(Context c, ArrayList<String> address, ArrayList<String> title , ArrayList<String> pic , ArrayList<String> date) {
        super(c, R.layout.listhistory, R.id.title1, title);
        this.context = c;
        this.address = address;
        this.title = title;
        this.pic=pic;
        this.date=date;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inf.inflate(R.layout.listhistory, parent, false);
        TextView address1 = (TextView) row.findViewById(R.id.address);
        TextView title1 = (TextView) row.findViewById(R.id.title1);
        ImageView pic1=(ImageView)row.findViewById(R.id.pic1) ;
        TextView date1 = (TextView) row.findViewById(R.id.date1);
        address1.setText(address.get(position));
        title1.setText(title.get(position));
        if(address.get(position).length()>35){
            address1.setText(address.get(position));}
        else{
            address1.setText(address.get(position));
        }
        if(pic!=null){
            bit =ip.decode(pic.get(position));
            pic1.setImageBitmap(bit);}
        date1.setText(date.get(position));

        return row;
    }


}
