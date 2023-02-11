package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class newdonate extends AppCompatActivity {
    URL u=new URL();
    public String donateUrl=u.donateUrl2;
    //"https://ajrowalahajro.000webhostapp.com/donate.php";
    //"http://192.168.1.14/projectDB/donate.php";
    EditText info;
    Button donate;
    ImageButton newpic;
    ImageView pic;
    Spinner sp;
    public static String kind;
    public String id1;
    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newdonate);
        Intent intent = getIntent();
        String type=intent.getExtras().getString("logType");
        String id=intent.getExtras().getString("id");
        Toast.makeText(getApplicationContext(),type+"logType",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),id+" id",Toast.LENGTH_SHORT).show();
        info = findViewById(R.id.info);
        String phone = intent.getExtras().getString("phone");
        String address = intent.getExtras().getString("address");
        pic= findViewById(R.id.imageView);
        donate= findViewById(R.id.donate);
        sp=findViewById(R.id.spinner);
        if(type.equals("2")){
            donate.setText("طلب");
        }
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataHistory(type,id,sp.getSelectedItemPosition()+"",phone,address);
                if(type.equals("1")){
                    //"http://192.168.1.7/projectDB/setDonate.php"
                    setNew(u.setDonate,id,sp.getSelectedItemPosition()+"",phone,address);
                }else if(type.equals("2")){
                   // "http://192.168.1.7/projectDB/setNeed.php"
                    setNew(u.setNeed,id,sp.getSelectedItemPosition()+"",phone,address);
                }
            }
        });

        newpic=findViewById(R.id.newpic);
        newpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 1);
            }
        });
    }
    public Bitmap bit;
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    final Bundle extras = data.getExtras();
    if (extras != null) {
        if (requestCode == 1) {
            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
            bit =(Bitmap)extras.get("data");
            pic.setImageBitmap(bit);        }
    }}
    String encodepic="123";
    ImageProcess ip=new ImageProcess();
    private void setDataHistory(String type,String id1,String kind1,String phone,String address) {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, donateUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Type", type);
                params.put("Id", id1);
                params.put("Info", info.getText().toString());
                params.put("Phone", phone);
                params.put("Address", address);
                if(bit!=null) {
                    params.put("Pic", ip.encode(bit));
                }else{
                    params.put("Pic",encodepic);
                }
                params.put("DonateState", "1");
                params.put("Kind", kind1);
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
    //////////////////////////////////////////////////////////////////////
    private void setNew(String url,String id1,String kind1,String phone,String address) {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"تم التبرع",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                String strDate = dateFormat.format(date);
                params.put("Id", id1);
                params.put("Title", "تبرع جديد");
                params.put("Info", info.getText().toString());
                params.put("Phone", phone);
                params.put("Address", address);
                params.put("Date", strDate);
                if(bit!=null) {
                    params.put("Pic", ip.encode(bit));
                }else{
                    params.put("Pic",encodepic);
                }
                params.put("DonateState", "1");
                params.put("Kind", kind1);
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}