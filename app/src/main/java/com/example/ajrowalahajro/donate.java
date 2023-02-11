package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class donate extends AppCompatActivity {
    URL u=new URL();
    public String donateUrl=u.donateUrl;
            //"http://192.168.1.7/projectDB/donate.php";
            //"https://ajrowalahajro.000webhostapp.com/donate.php";
            //"http://192.168.1.14/projectDB/donate.php";
    EditText info,phone,address;
    Button donate,choose;
    ImageButton newpic;
    ImageView pic;
    public String id1;
    Bundle args;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Intent intent = getIntent();
        String kind=intent.getExtras().getString("kind");
        String type=intent.getExtras().getString("loginType");
        String id=intent.getExtras().getString("id");
        String id2=intent.getExtras().getString("needyid");
        String needsid=intent.getExtras().getString("needsid");
        String id3=intent.getExtras().getString("donorid");
        String donationid=intent.getExtras().getString("donationid");
        String title=intent.getExtras().getString("title");
        String username=intent.getExtras().getString("username");
//        Toast.makeText(getApplicationContext(),kind+"kind",Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),type+"type",Toast.LENGTH_SHORT).show();
        info = findViewById(R.id.info);
        String phone = intent.getExtras().getString("phone");
        String address = intent.getExtras().getString("address");
        Toast.makeText(getApplicationContext(),intent.getExtras().getString("address"),Toast.LENGTH_SHORT).show();
        pic= findViewById(R.id.imageView);
        donate= findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataHistory(type,id,kind,phone,address);
                if(type.equals("1")){
                    setDataNotf(type,id,id2,needsid,title,username);
                }else if(type.equals("2")){
                    setDataNotf(type,id3,id,donationid,title,username);
                    Toast.makeText(getApplicationContext(),id3+" "+id,Toast.LENGTH_SHORT).show();
                }
                finish();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
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
//                        Toast.makeText(getApplicationContext(),"جاري مراجعة التبرع",Toast.LENGTH_SHORT).show();
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
                params.put("Type", type);
                params.put("Id", id1);
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
    //////////////////////////////////////////////////////////////////////////////////

    private void setDataNotf(String type,String donorid,String needyid,String dnid,String title,String username) {
        //"http://192.168.1.7/projectDB/setDataNotf.php"
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, u.setDataNotf, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"تمت عملية التبرع",Toast.LENGTH_SHORT).show();
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
                params.put("Type", type);
                params.put("DID", donorid);
                params.put("NID", needyid);
                params.put("DNID", dnid);
                params.put("Descriptions", info.getText().toString());
                params.put("Title", title);
                params.put("Name", username);
                params.put("Date", strDate);
                if(bit!=null) {
                    params.put("Pic", ip.encode(bit));
                }else{
                    params.put("Pic",encodepic);
                }
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}