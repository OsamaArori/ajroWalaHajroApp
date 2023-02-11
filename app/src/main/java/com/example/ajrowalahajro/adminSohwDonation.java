package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adminSohwDonation extends AppCompatActivity {
    TextView name;
    TextView address;
    TextView phone;
    TextView date;
    TextView desc;
    ImageView picdon;
    ImageView picuser;
    Bitmap bit;

    ImageProcess ip=new ImageProcess();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sohw_donation);
        Bundle args;
        args = getIntent().getExtras();
        String userID =(args.getString("id"));
        Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        date=findViewById(R.id.date);
        desc=findViewById(R.id.desc);
        picdon=findViewById(R.id.picdon);
        picuser=findViewById(R.id.userpic);
        name.setText(args.getString("name"));
        address.setText(args.getString("address"));
        phone.setText(args.getString("phone"));
        date.setText(args.getString("date"));
        desc.setText(args.getString("desc"));
        if(args.getString("picdon")!=null){
            bit =ip.decode(args.getString("picdon"));
            picdon.setImageBitmap(bit);}
        getDataDonaitios(userID);
    }
    URL u=new URL();
    public void getDataDonaitios(String id) {
        //"http://192.168.1.7/projectDB/getDonorInfo.php"
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, u.getDonorInfo, new Response.Listener<String>() {
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
                        Toast.makeText(adminSohwDonation.this,"Error Response 102",Toast.LENGTH_SHORT).show();
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
}