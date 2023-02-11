package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class adminShowNeed extends AppCompatActivity {
    TextView name;
    TextView address;
    TextView phone;
    TextView date;
    TextView desc;
    ImageView picuser;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();
    Button accept,refuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_need);
        Bundle args;
        args = getIntent().getExtras();
        String userID =(args.getString("id"));
        String needid =(args.getString("needid"));
        Toast.makeText(this, needid, Toast.LENGTH_SHORT).show();
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        date=findViewById(R.id.date);
        desc=findViewById(R.id.desc);
        picuser=findViewById(R.id.userpic);
        name.setText(args.getString("name"));
        address.setText(args.getString("address"));
        phone.setText(args.getString("phone"));
        date.setText(args.getString("date"));
        desc.setText(args.getString("desc"));
        accept=findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept(needid,"1");
                Toast.makeText(adminShowNeed.this, "تم التفعيل", Toast.LENGTH_SHORT).show();
            }
        });
        refuse=findViewById(R.id.refuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept(needid,"0");
                Toast.makeText(adminShowNeed.this, "تم الغاء التفعيل", Toast.LENGTH_SHORT).show();
            }
        });
        getDataNeed(userID);
    }
    URL u=new URL();
    public void getDataNeed(String id) {
        //"http://192.168.1.7/projectDB/getNeedyInfo.php"
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, u.getNeedyInfo, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                name.setText(UserDetailArray.getJSONObject(i).getString("Name"));
                                phone.setText(UserDetailArray.getJSONObject(i).getString("Phone"));
                                String encode=(UserDetailArray.getJSONObject(i).getString("Pic"));
                                if(encode!=null){
                                    bit =ip.decode(encode);
                                    picuser.setImageBitmap(bit);}
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(adminShowNeed.this,"Error Response 102",Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Id", id);
//                params.put("Kind", kind);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
    ///////////////////////////////////////////////////////////
    public void accept(String needid,String status) {
       // "http://192.168.1.7/projectDB/acceptNeed.php"
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, u.acceptNeed, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
//                            for (int i = 0; i < UserDetailArray.length(); i++) {
//                                name.setText(UserDetailArray.getJSONObject(i).getString("Name"));
//                                phone.setText(UserDetailArray.getJSONObject(i).getString("Phone"));
//                                String encode=(UserDetailArray.getJSONObject(i).getString("Pic"));
//                                if(encode!=null){
//                                    bit =ip.decode(encode);
//                                    picuser.setImageBitmap(bit);}
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(adminShowNeed.this,"Error Response 102",Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("needid", needid);
                params.put("Status", status);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
}