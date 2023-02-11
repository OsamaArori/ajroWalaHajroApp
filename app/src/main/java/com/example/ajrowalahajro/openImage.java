package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class openImage extends AppCompatActivity {
    ImageView waterbill;
    Button close;
    Bundle args;
    URL u=new URL();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_image);
        waterbill=findViewById(R.id.wb);
        close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        args = getIntent().getExtras();
        getData(args.getString("id"),u.getNeedyInfo);
    }
    String wb ="";
    ImageProcess ip=new ImageProcess();
    private void getData(String id1,String url) {
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            wb=UserDetailArray.getJSONObject(0).getString("Waterbill");
                            if(wb!=null){
                                waterbill.setImageBitmap(ip.decode(wb));
                                wb=UserDetailArray.getJSONObject(0).getString("Waterbill");
                            }
                            //pic.setImageBitmap(ip.decode(wb));
//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(openImage.this,"Error Response 102"+error,Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Id", id1);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
}