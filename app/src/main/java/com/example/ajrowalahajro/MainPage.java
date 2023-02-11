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

public class MainPage extends AppCompatActivity {
    TextView stname,scname;
    RoundedImageView ri;
    Bitmap bit;
    FloatingActionButton fab;
    ImageProcess ip=new ImageProcess();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_main_page);
        DrawerLayout dl= findViewById(R.id.drawer_layout);
        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(GravityCompat.START);
            }
        });
        NavigationView nv=findViewById(R.id.navigationview);
        nv.setItemIconTintList(null);
        NavController nvc= Navigation.findNavController(this,R.id.navfragment);
        NavigationUI.setupWithNavController(nv,nvc);
        profile fragment = new profile();
        needs needs1 = new needs();
        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        bundle.putString("id", intent.getExtras().getString("id"));
        bundle.putString("pic", intent.getExtras().getString("pic"));
        bundle.putString("loginType", intent.getExtras().getString("loginType"));
        bundle.putString("username", intent.getExtras().getString("username"));
        bundle.putString("address", intent.getExtras().getString("address"));
        bundle.putString("phone", intent.getExtras().getString("phone"));
        needs1.setArguments(bundle);
        fragment.setArguments(bundle);//Here pass your data to fragment
        String name=intent.getExtras().getString("username");
        String email=intent.getExtras().getString("email");
        String pic=intent.getExtras().getString("pic");
        String logType=intent.getExtras().getString("loginType");
        String id=intent.getExtras().getString("id");
        String address=intent.getExtras().getString("address");
        String phone=intent.getExtras().getString("phone");
        View header = nv.getHeaderView(0);
        ri=header.findViewById(R.id.imageprofile);
        bit=ip.decode(pic);
        if(bit!=null)
            ri.setImageBitmap(bit);
        stname=header.findViewById(R.id.textView);
        scname=header.findViewById(R.id.textView2);
        stname.setText(name);
        scname.setText(email);
        BottomNavigationView navView=findViewById(R.id.bottomnavigationbar);
        NavController nvc2= Navigation.findNavController(this,R.id.navfragment);
        NavigationUI.setupWithNavController(navView,nvc2);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, newdonate.class);
                intent.putExtra("logType",logType);
                intent.putExtra("id",id);
                intent.putExtra("address",address);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });


//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.signout:
//                        Toast.makeText(MainPage.this, "sign out", Toast.LENGTH_SHORT).show();
//                        finish();break;
//                }
//                return true;
//            }
//        });


    }

}