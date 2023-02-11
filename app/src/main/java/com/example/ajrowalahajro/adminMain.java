package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

public class adminMain extends AppCompatActivity {
    TextView stname,scname;
    RoundedImageView ri;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        profile fragment = new profile();
        Bundle bundle = new Bundle();
        Intent intent = getIntent();
//        bundle.putString("id", intent.getExtras().getString("id"));
//        bundle.putString("pic", intent.getExtras().getString("pic"));
//        bundle.putString("loginType", intent.getExtras().getString("loginType"));
//        fragment.setArguments(bundle);//Here pass your data to fragment
//        String name=intent.getExtras().getString("name");
//        String email=intent.getExtras().getString("email");
//        String pic=intent.getExtras().getString("pic");
//        String logType=intent.getExtras().getString("loginType");
//        String id=intent.getExtras().getString("id");
        //View header = nv.getHeaderView(0);
//        ri=header.findViewById(R.id.imageprofile);
//        bit=ip.decode(pic);
//        if(bit!=null)
//            ri.setImageBitmap(bit);
//        stname=header.findViewById(R.id.textView);
//        scname=header.findViewById(R.id.textView2);
//        stname.setText(name);
//        scname.setText(email);
        BottomNavigationView navView=findViewById(R.id.adminbottomnavigationbar);
        NavController nvc2= Navigation.findNavController(this,R.id.adminfragment);
        NavigationUI.setupWithNavController(navView,nvc2);



    }
}