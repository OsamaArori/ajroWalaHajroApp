package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class restPass extends AppCompatActivity {
    URL u=new URL();
//    public String getDonorInfourl=u.getInfourl;
//    public String getNeedyInfourl=u.getNeedyInfourl;
    EditText pass,repass;
    Button update;
    public String id1;
    public String loginType;
    public String phone;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_pass);
        args = getIntent().getExtras();
        id1 =args.getString("id");
        Toast.makeText(this, id1, Toast.LENGTH_SHORT).show();
        loginType=args.getString("type");
        Toast.makeText(this, loginType, Toast.LENGTH_SHORT).show();
        if(id1.equals("0")){
            phone=(args.getString("phone"));
        }
        pass=findViewById(R.id.pass);
        repass=findViewById(R.id.repass);
        update=findViewById(R.id.updatepass);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass.getText().toString().isEmpty()||repass.getText().toString().isEmpty()){
                    Toast.makeText(restPass.this,"احد الحقول او كلاهما فارغ",Toast.LENGTH_SHORT).show();
                }else if(pass.getText().toString().equals(repass.getText().toString())){
                    if(loginType.equals("1")){
                        if(id1.equals("0")){
                            updateData2(phone,u.resetDonorPass);
                        }else updateData(id1,u.updateDonorPass);
                    }else if(loginType.equals("2")){
                        if(id1.equals("0")){
                            updateData2(phone,u.resetNeedyPass);
                        }else updateData(id1,u.updateNeedyPass);
                    }
                    finish();
                }else {
                    Toast.makeText(restPass.this,"كلمة السر غير متطابقه",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void updateData(String id1,String url) {
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(restPass.this,"تم تغيير كلمة السر",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(restPass.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Id", id1);
                params.put("Pass", pass.getText().toString());
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
    //////////////////////////////////////////////////////
    private void updateData2(String phone,String url) {
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(restPass.this,"تم اعادة تعيين كلمة السر",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(restPass.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Phone", phone);
                params.put("Pass", pass.getText().toString());
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}