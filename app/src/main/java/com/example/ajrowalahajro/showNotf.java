package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class showNotf extends AppCompatActivity {
    TextView name;
    TextView address;
    TextView phone;
    TextView date;
    TextView desc;
    ImageView picnotf;
    ImageView picuser;
    Button accept,call;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notf);
        Bundle args;
        args = getIntent().getExtras();
        String userID =(args.getString("id"));
        String notfID =(args.getString("notfid"));
        String loginType =(args.getString("type"));
        String pic =(args.getString("encode"));
        Toast.makeText(this, userID+notfID+loginType, Toast.LENGTH_SHORT).show();
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        date=findViewById(R.id.date);
        desc=findViewById(R.id.desc);
        picnotf=findViewById(R.id.picnotf);
        picuser=findViewById(R.id.picuser);
        name.setText(args.getString("name"));
        address.setText(args.getString("address"));
        phone.setText(args.getString("phone"));
        date.setText(args.getString("date"));
        desc.setText(args.getString("desc"));
        if(pic!=null){
            bit =ip.decode(pic);
            picnotf.setImageBitmap(bit);}
        getDataNotf(notfID,userID,loginType);
        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone.getText().toString()));
                startActivity(intent);
            }
        });
    }
    URL u=new URL();
    public void getDataNotf(String notfid,String userid,String logintype) {
        //"http://192.168.1.7/projectDB/getDonorInfo.php"
        String url="";
        if(logintype.equals("1")){
            url=u.getNotfDataForDonor;
        }else if(logintype.equals("2")){
            url=u.getNotfDataForNeedy;
        }
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST,url , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                name.setText(UserDetailArray.getJSONObject(i).getString("Name"));
                                phone.setText(UserDetailArray.getJSONObject(i).getString("Phone"));
                                address.setText(UserDetailArray.getJSONObject(i).getString("Address"));
                                date.setText(UserDetailArray.getJSONObject(i).getString("Date"));
                                desc.setText(UserDetailArray.getJSONObject(i).getString("Desc"));
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
                        Toast.makeText(showNotf.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("NotfId", notfid);
                params.put("UserID", userid);
                if(logintype.equals("1")){
                    params.put("Type", "2");
                }else if(logintype.equals("2")){
                    params.put("Type", "1");
                }
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
}