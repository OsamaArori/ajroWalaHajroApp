package com.example.ajrowalahajro;

import android.content.Context;
import android.content.Intent;
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

public class Register {
    public String name;
    public String email;
    public String phone;
    public String address;
    public String password;
    public String waterbill;
    public String salary;
    public String nfm;
    public Register(String name,String email,String phone,String address,String password){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.password=password;
    }
    public Register(String name,String email,String phone,String address,String password,String waterbill
                    ,String salary,String nfm){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.password=password;
        this.waterbill=waterbill;
        this.salary=salary;
        this.nfm=nfm;
    }
    URL u=new URL();
    public void donorRegister(Context c) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST,u.registerurl , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c,"تم انشاء حسابك كمتبرع بنجاح",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(c,""+error,Toast.LENGTH_LONG).show();
                    }
                }
                ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Name",name);
                params.put("Email",email);
                params.put("Phone",phone);
                params.put("Address",address);
                params.put("Pass",password);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
    public void needyRegister(Context c) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, u.registerurl2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, "تم انشاء حسابك كمستفيد بنجاح", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(c,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Name",name);
                params.put("Email",email);
                params.put("Phone",phone);
                params.put("Address",address);
                params.put("Pass",password);
                params.put("Waterbill",waterbill);
                params.put("Salary",salary);
                params.put("Nfm",nfm);
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}
