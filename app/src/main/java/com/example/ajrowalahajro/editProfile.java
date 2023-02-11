package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class editProfile extends AppCompatActivity {
    URL u=new URL();
    public String getDonorInfourl=u.getInfourl;
    public String getNeedyInfourl=u.getNeedyInfourl;
    EditText id,name,email,phone,address;
    Button update,choose,newpic;
    ImageView pic;
    public String id1;
    public String loginType;
    Bundle args;
    String encodepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        id = (EditText) findViewById(R.id.id1);
        name = (EditText) findViewById(R.id.username1);
        email = (EditText) findViewById(R.id.email1);
        phone = (EditText) findViewById(R.id.phone1);
        address = (EditText) findViewById(R.id.address1);
        pic = (ImageView) findViewById(R.id.imageView1);
        args = getIntent().getExtras();
        id1 =(args.getString("id"));
        loginType=(args.getString("type"));
        Toast.makeText(this,id1,Toast.LENGTH_SHORT).show();
        encodepic=(args.getString("pic"));
        if(loginType.equals("1")){
            getData(id1,getDonorInfourl);
        }else if(loginType.equals("2")){
            getData(id1,getNeedyInfourl);
        }
        update=findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginType.equals("1")){
                    updateData(id1,u.updateInfourl);
                }else if(loginType.equals("2")){
                    updateData(id1,u.updateNeedyInfourl);
                }
                finish();
            }
        });
        choose=findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                intent.putExtra("crop", "true");
//                intent.putExtra("scale", true);
//                intent.putExtra("outputX", 256);
//                intent.putExtra("outputY", 256);
//                intent.putExtra("aspectX", 1);
//                intent.putExtra("aspectY", 1);
//                intent.putExtra("return-data", true);
//                startActivityForResult(intent, 10);
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 1);
            }
        });
        newpic=findViewById(R.id.newpic);
        newpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,20);
            }
        });
    }
    public Bitmap bit;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }final Bundle extras = data.getExtras();
        if (extras != null) {
            if (requestCode == 1) {
                bit =(Bitmap)extras.get("data");
                pic.setImageBitmap(bit);
            }else if(requestCode==20){
                bit=(Bitmap)extras.get("data");
                pic.setImageBitmap(bit);
            }}
    }

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
                            id.setText(UserDetailArray.getJSONObject(0).getString("Id"));
                            name.setText(UserDetailArray.getJSONObject(0).getString("Name"));
                            email.setText(UserDetailArray.getJSONObject(0).getString("Email"));
                            phone.setText(UserDetailArray.getJSONObject(0).getString("Phone"));
                            address.setText(UserDetailArray.getJSONObject(0).getString("Address"));
                            //password.setText(UserDetailArray.getJSONObject(0).getString("Pass"));
                            encodepic=UserDetailArray.getJSONObject(0).getString("Pic");
                            if(encodepic!=null)
                                pic.setImageBitmap(ip.decode(encodepic));
                            profile fragment = new profile();
                            Bundle bundle = new Bundle();
                            bundle.putString("name", name.getText().toString());
                            bundle.putString("email", email.getText().toString());
                            if(encodepic!=null)
                                bundle.putString("pic", encodepic);
                            fragment.setArguments(bundle);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(editProfile.this,"Error Response 102"+error,Toast.LENGTH_SHORT).show();
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

    private void updateData(String id1,String url) {
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(editProfile.this,"updated",Toast.LENGTH_SHORT).show();
                        //getData(id1,url);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(editProfile.this,"Error Response 102",Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("Id", id1);
                params.put("Name", name.getText().toString());
                params.put("Email", email.getText().toString());
                params.put("Phone", phone.getText().toString());
                params.put("Address", address.getText().toString());
                //params.put("Pass", password.getText().toString());
                if(bit!=null) {
                    params.put("Pic", ip.encode(bit));
                }else{
                    params.put("Pic",encodepic);
                }
                return  params;
            }
        };
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}