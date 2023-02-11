package com.example.ajrowalahajro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class needyRegister extends AppCompatActivity {
    URL u=new URL();
    public String registerurl=u.registerurl2;
            //"http://192.168.1.7/projectDB/needyRegister.php";
    //"https://ajrowalahajro.000webhostapp.com/donorRegister.php";
    //"http://192.168.1.14/projectDB/donorRegister.php";
    public String checkrurl=u.checkrurl2;
                    //"http://192.168.1.7/projectDB/needyCheck.php";
    //"https://ajrowalahajro.000webhostapp.com/donorCheck.php";
    //"http://192.168.1.14/projectDB/donorCheck.php";
    EditText waterbill,salary,nfm;
    Button signup;
    ImageButton imagewaterbill;
    Bitmap bit;
    String name = "";
    String email = "";
    String phone = "";
    String address = "";
    String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needy_register);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        email = extras.getString("email");
        phone = extras.getString("phone");
        address = extras.getString("address");
        password = extras.getString("password");
        waterbill=findViewById(R.id.waterbill);
        salary=findViewById(R.id.salary);
        nfm=findViewById(R.id.nfm);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check=validateInfo(waterbill.getText().toString(),salary.getText().toString(),nfm.getText().toString());
               if(check==true){
                   check(email,phone);
                   finish();
               }
            }
        });
        imagewaterbill=findViewById(R.id.imagewaterbill);
        imagewaterbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
    }
    private Boolean validateInfo(String waterbill1, String salary1, String nfm1) {
        if(waterbill1.length()==0){
            waterbill.requestFocus();
            waterbill.setError("يجب ان ترفق صورة فاتورة مياه");
            return false;
        }else if(salary1.length()==0){
            salary.requestFocus();
            salary.setError("يجب ان لا يكون فارغ");
            return false;
        }else if(nfm1.length()==0){
            nfm.requestFocus();
            nfm.setError("يجب ان لا يكون فارغ");
            return false;
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }final Bundle extras = data.getExtras();
        if (extras != null) {
            if (requestCode == 1) {
                bit =(Bitmap)extras.get("data");
                waterbill.setText(bit.toString());
            }}
    }
    ///////////////////////////////////// check email and phone
    public void check(String email1,String phone1) {
        final String[] strEmail = new String[1];
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
                                strEmail[0] = jsonArray.getJSONObject(i).get("Email").toString();
                                strPhone[0] = jsonArray.getJSONObject(i).get("Phone").toString();
                                if (email1.equals(strEmail[0])) {
                                    Toast.makeText(getApplicationContext(),"البريد الالكتروني موجود مسبقاً", Toast.LENGTH_SHORT).show();
                                    break;
                                }else if(phone1.equals(strPhone[0])){
                                    Toast.makeText(getApplicationContext(),"رقم الهاتف موجود مسبقاً", Toast.LENGTH_SHORT).show();
                                    break;}
                                else if(z==i){
                                    Intent intent = new Intent(getApplicationContext(),VerifyPhoneNo.class);
                                    ImageProcess ip=new ImageProcess();
                                    String waterbill =ip.encode(bit);
                                    intent.putExtra("userType", "2");
                                    intent.putExtra("type", "register");
                                    intent.putExtra("phoneNo", phone1);
                                    intent.putExtra("name",name);
                                    intent.putExtra("email",email);
                                    intent.putExtra("address",address);
                                    intent.putExtra("password",password);
                                    intent.putExtra("waterbill",waterbill);
                                    intent.putExtra("salary",salary.getText().toString());
                                    intent.putExtra("nfm",nfm.getText().toString());
                                    startActivity(intent);
                                    break;
                                }
                                i++;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
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