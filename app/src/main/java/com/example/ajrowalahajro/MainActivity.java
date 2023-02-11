package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton donorBtn,needyBtn,adminBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        donorBtn=findViewById(R.id.donorBtn);
        needyBtn=findViewById(R.id.needyBtn);
        adminBtn=findViewById(R.id.adminBtn);
        donorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, donorLogin.class);
                intent.putExtra("login","1");
                startActivity(intent);

            }
        });
        needyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, donorLogin.class);
                intent.putExtra("login","2");
                startActivity(intent);
            }
        });
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, donorLogin.class);
                intent.putExtra("login","3");
                startActivity(intent);
            }
        });

    }

}