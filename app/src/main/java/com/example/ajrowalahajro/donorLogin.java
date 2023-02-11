package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class donorLogin extends AppCompatActivity {
    URL u=new URL();
    public String loginurl=u.loginurl;
            //"https://ajrowalahajro.000webhostapp.com/donorLogIn.php";
            //"http://192.168.1.14/projectDB/donorLogIn.php";
    public String needyLogin=u.needyLogin;
                    //"https://ajrowalahajro.000webhostapp.com/needyLogin.php";
                    public String adminLogin=u.adminLogin;
                            //"http://192.168.1.7/projectDB/adminLogin.php";

    EditText email, password;
    Button login;
    Button donorRegister;
    TextView forgetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);
        Bundle extras = getIntent().getExtras();
        String type = extras.getString("login");
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        donorRegister=findViewById(R.id.donorRegister);
        forgetpass=findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), phoneNumber.class);
                intent.putExtra("loginType", type);
                startActivity(intent);
            }
        });
        if(type.equals("3")){
            donorRegister.setVisibility(View.GONE);
            forgetpass.setVisibility(View.GONE);
            email.setHint("اسم المستخدم");
        }
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("1")){
                    login();
                }else if(type.equals("2")){
                    needyLogin();
                }else if(type.equals("3")){
                    adminLogin();
                }

            }
        });
        donorRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String value = extras.getString("login");
                if(value.equals("1")){
                    Intent i = new Intent(getApplicationContext(), donorRegister.class);
                    i.putExtra("register","1");
                    startActivity(i);
                }else if(value.equals("2")){
                    Intent i = new Intent(getApplicationContext(), donorRegister.class);
                    i.putExtra("register","2");
                    startActivity(i);
                }

            }
        });
    }
    public void login() {
        final String[] strEmail = new String[1];
        final String[] strPhone = new String[1];
        final String[] strPass = new String[1];
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, loginurl, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("dishs");
                            int i=0;
                            int z=jsonArray.length()-1;
                            while (jsonArray.length()>i){
                                strEmail[0] = jsonArray.getJSONObject(i).get("Email").toString();
                                strPhone[0] = jsonArray.getJSONObject(i).get("Phone").toString();
                                strPass[0] = jsonArray.getJSONObject(i).get("Pass").toString();
                                if ((strPhone[0].equals(email.getText().toString())||strEmail[0].equals(email.getText().toString()))&&strPass[0].equals(password.getText().toString())) {
                                    Intent x = new Intent(getApplicationContext(), MainPage.class);
                                    x.putExtra("id", jsonArray.getJSONObject(i).get("Id").toString());
                                    x.putExtra("username", jsonArray.getJSONObject(i).get("Name").toString());
                                    x.putExtra("email", jsonArray.getJSONObject(i).get("Email").toString());
                                    x.putExtra("phone", jsonArray.getJSONObject(i).get("Phone").toString());
                                    x.putExtra("address", jsonArray.getJSONObject(i).get("Address").toString());
                                    x.putExtra("pic", jsonArray.getJSONObject(i).get("Pic").toString());
                                    x.putExtra("loginType", "1");
                                    startActivity(x);
                                    Toast.makeText(getApplicationContext(), "التسجيل كمتبرع", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), " اهلا بك "+jsonArray.getJSONObject(i).get("Name").toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                }else if(z==i){
                                    Toast.makeText(getApplicationContext(), "البريد الالكتروني او رقم الهاتف الذي ادخلته غير صحيح", Toast.LENGTH_SHORT).show();
                                    break; }
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
    ////////////////////////////////////////////
    public void needyLogin() {
        final String[] strEmail = new String[1];
        final String[] strPhone = new String[1];
        final String[] strPass = new String[1];
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, needyLogin, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("dishs");
                            int i=0;
                            int z=jsonArray.length()-1;
                            while (jsonArray.length()>i){
                                strEmail[0] = jsonArray.getJSONObject(i).get("Email").toString();
                                strPhone[0] = jsonArray.getJSONObject(i).get("Phone").toString();
                                strPass[0] = jsonArray.getJSONObject(i).get("Pass").toString();
                                if ((strPhone[0].equals(email.getText().toString())||strEmail[0].equals(email.getText().toString()))&&strPass[0].equals(password.getText().toString())) {
                                    Intent x = new Intent(getApplicationContext(), MainPage.class);
                                    x.putExtra("id", jsonArray.getJSONObject(i).get("Id").toString());
                                    x.putExtra("username", jsonArray.getJSONObject(i).get("Name").toString());
                                    x.putExtra("email", jsonArray.getJSONObject(i).get("Email").toString());
                                    x.putExtra("phone", jsonArray.getJSONObject(i).get("Phone").toString());
                                    x.putExtra("address", jsonArray.getJSONObject(i).get("Address").toString());
                                    x.putExtra("pic", jsonArray.getJSONObject(i).get("Pic").toString());
                                    x.putExtra("loginType", "2");
                                    startActivity(x);
                                    Toast.makeText(getApplicationContext(), "التسجيل كمتلقي", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), " اهلا بك "+jsonArray.getJSONObject(i).get("Name").toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                }else if(z==i){
                                    Toast.makeText(getApplicationContext(), "البريد الالكتروني او رقم الهاتف الذي ادخلته غير صحيح", Toast.LENGTH_SHORT).show();
                                    break; }
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
    ////////////////////////////////////////////////////////////////
    public void adminLogin() {
        final String[] strUser = new String[1];
        final String[] strPass = new String[1];
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, adminLogin, null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("dishs");
                            int i=0;
                            int z=jsonArray.length()-1;
                            while (jsonArray.length()>i){
                                strUser[0] = jsonArray.getJSONObject(i).get("Name").toString();
                                strPass[0] = jsonArray.getJSONObject(i).get("Pass").toString();
                                if (strUser[0].equals(email.getText().toString())&&strPass[0].equals(password.getText().toString())) {
                                    Intent x = new Intent(getApplicationContext(), adminMain.class);
                                    x.putExtra("loginType", "3");
                                    startActivity(x);
                                    Toast.makeText(getApplicationContext(), "التسجيل كمسؤول", Toast.LENGTH_SHORT).show();
                                    break;
                                }else if(z==i){
                                    Toast.makeText(getApplicationContext(), "خطأ في اسم المستخدم او كلمة السر", Toast.LENGTH_SHORT).show();
                                    break; }
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