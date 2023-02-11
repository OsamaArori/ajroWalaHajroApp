package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class showNeed extends AppCompatActivity {
    TextView name;
    TextView address;
    TextView phone;
    TextView date;
    TextView desc;
    ImageView donpic;
    ImageView picuser;
    Button accept,call;
    Bitmap bit;
    ImageProcess ip=new ImageProcess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_need);
        Bundle args;
        args = getIntent().getExtras();
        String userID =(args.getString("id"));
        String donID =(args.getString("donID"));//donation id
        String needyID =(args.getString("needyID"));
        String donorID =(args.getString("donorid"));
        String pic =(args.getString("encode"));
        Toast.makeText(this, userID+donorID, Toast.LENGTH_SHORT).show();
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        date=findViewById(R.id.date);
        desc=findViewById(R.id.desc);
        donpic=findViewById(R.id.donpic);
        picuser=findViewById(R.id.picuser);
        name.setText(args.getString("name"));
        address.setText(args.getString("address"));
        phone.setText(args.getString("phone"));
        date.setText(args.getString("date"));
        desc.setText(args.getString("desc"));
        accept=findViewById(R.id.get);
        if(pic!=null){
            bit =ip.decode(pic);
            donpic.setImageBitmap(bit);}
        getDataDon(donID,donorID,"2");
        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone.getText().toString()));
                startActivity(intent);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataNotf("2",userID,needyID,donID,desc.getText().toString(),name.getText().toString());
            }
        });
    }
    URL u=new URL();
    public void getDataDon(String donid,String userid,String logintype) {
        //http://192.168.1.7/projectDB/getDonorInfoForNeedy.php"
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST,u.getDonorInfoForNeedy, new Response.Listener<String>() {
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
                        Toast.makeText(showNeed.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("DonId", donid);
                params.put("UserID", userid);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
    private void setDataNotf(String type,String donorid,String needyid,String dnid,String title,String username) {
        //"http://192.168.1.7/projectDB/setDataNotf.php"
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, u.setDataNotf, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"تم ارسال الطلب الى المتبرع",Toast.LENGTH_SHORT).show();
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
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                params.put("Type", "2");
                params.put("DID", donorid);
                params.put("NID", needyid);
                params.put("DNID", dnid);
                params.put("Descriptions", desc.getText().toString());
                params.put("Title", title);
                params.put("Name", username);
                params.put("Date", strDate);
                if(bit!=null) {
                    params.put("Pic", ip.encode(bit));
                }else{
                    params.put("Pic","123");
                }
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}