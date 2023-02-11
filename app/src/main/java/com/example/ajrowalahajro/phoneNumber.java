package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class phoneNumber extends AppCompatActivity {
    Button send;
    EditText phone;
    String loginType;
    URL u=new URL();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        Bundle extras = getIntent().getExtras();
        loginType = extras.getString("loginType");
//        Toast.makeText(this, loginType, Toast.LENGTH_SHORT).show();
        phone=findViewById(R.id.phone);
        send=findViewById(R.id.sendcode);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
//                intent.putExtra("phoneNo", phone.getText().toString());
//                intent.putExtra("type", "restPassword");
//                intent.putExtra("userType", loginType);
//                startActivity(intent);
                if(loginType.equals("1")){
                    check(u.checkrurl,phone.getText().toString());
                }else if(loginType.equals("2")){
                    check(u.checkrurl2,phone.getText().toString());
                }

                finish();
            }
        });
    }
    public void check(String checkrurl,String phone1) {
        final String[] strPhone = new String[1];
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, checkrurl, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("dishs");
                            int i=0;
                            int z=jsonArray.length()-1;
                            while (jsonArray.length()>i){
                                strPhone[0] = jsonArray.getJSONObject(i).get("Phone").toString();
                                if (phone1.equals(strPhone[0])) {
                                    Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
                                    intent.putExtra("phoneNo", phone.getText().toString());
                                    intent.putExtra("type", "restPassword");
                                    intent.putExtra("userType", loginType);
                                    startActivity(intent);
                                    break;
                                } else if(z==i){
                                    Toast.makeText(phoneNumber.this, "رقم الهاتف غير موجود مسبقاً", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                i++;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"error "+e.toString(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}