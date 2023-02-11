package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class adminUsersProfile extends AppCompatActivity {
    URL u=new URL();
    EditText id,name,email,phone,address, password,salary,nfm;
    Button waterbill,delete,history;
    ImageView pic;
    public String id1;
    public String loginType;
    Bundle args;
    ImageProcess ip=new ImageProcess();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_profile);
        id = (EditText)findViewById(R.id.id);
        name = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        address = (EditText)findViewById(R.id.address);
        password = (EditText)findViewById(R.id.pass);
        salary = (EditText)findViewById(R.id.salary);
        nfm = (EditText)findViewById(R.id.nfm);
        pic = (ImageView)findViewById(R.id.imageView);
        //encodepic=(args.getString("pic"));
        if(encodepic!=null)
            pic.setImageBitmap(ip.decode(encodepic));
        waterbill=findViewById(R.id.waterbillview);
        args = getIntent().getExtras();
        id1 =(args.getString("id"));
        loginType=(args.getString("type"));
        if(loginType.equals("1")){
            salary.setVisibility(View.GONE);
            nfm.setVisibility(View.GONE);
            waterbill.setVisibility(View.GONE);
        }
        Toast.makeText(this,loginType,Toast.LENGTH_SHORT).show();
if(loginType.equals("1")){
    getData(id1,loginType,u.getDonorInfo);
}else if(loginType.equals("2")){
    getData(id1,loginType,u.getNeedyInfo);
}
delete=findViewById(R.id.delete);
history=findViewById(R.id.history);
delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(adminUsersProfile.this,loginType,Toast.LENGTH_SHORT).show();
        if(loginType.equals("1")){
            delete(id1,u.deleteDonor);
        }else if(loginType.equals("2")){
            delete(id1,u.deleteneedy);
        }
        finish();
    }
});
history.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        history nextFrag= new history();
        Bundle bundle = new Bundle();
        bundle.putString("id", id1);
        bundle.putString("type", loginType);
        Toast.makeText(adminUsersProfile.this,id1+" ihweflkjh",Toast.LENGTH_SHORT).show();
        nextFrag.setArguments(bundle);
        FragmentManager fragmentManager = adminUsersProfile.this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.adminUsersProfile, nextFrag).commit();
    }
});
waterbill.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(adminUsersProfile.this, openImage.class);
        intent.putExtra("id", id1);
        //Toast.makeText(adminUsersProfile.this,wb,Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
});
    }
    String encodepic="123";
    String wb="";
    private void getData(String id1,String type,String url) {
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            id.setText(UserDetailArray.getJSONObject(0).getString("Id"));
                            name.setText(UserDetailArray.getJSONObject(0).getString("Name"));
                            email.setText(UserDetailArray.getJSONObject(0).getString("Email"));
                            phone.setText(UserDetailArray.getJSONObject(0).getString("Phone"));
                            address.setText(UserDetailArray.getJSONObject(0).getString("Address"));
                            password.setText(UserDetailArray.getJSONObject(0).getString("Pass"));
                            if(type.equals("2")){
                            nfm.setText(UserDetailArray.getJSONObject(0).getString("NFM"));
                            salary.setText(UserDetailArray.getJSONObject(0).getString("Salary"));}
                            encodepic=UserDetailArray.getJSONObject(0).getString("Pic");
                            if(encodepic!=null)
                                pic.setImageBitmap(ip.decode(encodepic));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(adminUsersProfile.this,"Error Response 102"+error,Toast.LENGTH_SHORT).show();
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
    private void delete(String id1,String url) {
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            //JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(adminUsersProfile.this,"Error Response 102"+error,Toast.LENGTH_SHORT).show();
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