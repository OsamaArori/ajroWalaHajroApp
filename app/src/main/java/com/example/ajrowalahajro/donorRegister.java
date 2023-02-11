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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class donorRegister extends AppCompatActivity {
URL u=new URL();
    public String registerurl=u.registerurl;
            //"https://ajrowalahajro.000webhostapp.com/donorRegister.php";
            //"http://192.168.1.14/projectDB/donorRegister.php";
    public String checkrurl=u.checkrurl;
                    //"https://ajrowalahajro.000webhostapp.com/donorCheck.php";
                    //"http://192.168.1.14/projectDB/donorCheck.php";
    EditText name,email,phone,address,password,repassword;
    Button signup,choose,new1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_register);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("register");
        name = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                String Email=email.getText().toString();
                String Phone=phone.getText().toString();
                String Address=address.getText().toString();
                String Pass=password.getText().toString();
                String RePass=repassword.getText().toString();
                boolean check=validateInfo(Name,Email,Phone,Address,Pass,RePass);
                if(check==true){
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || phone.getText().toString().equals("")
                        || address.getText().toString().equals("")
                        || password.getText().toString().equals("") || repassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"The data is incomplete",Toast.LENGTH_LONG).show();
                }else{
                    if(password.getText().toString().equals(repassword.getText().toString())){
                        if(value.equals("1")){
                            check(email.getText().toString(),phone.getText().toString());
                            finish();
                        }else if(value.equals("2")){
                            Intent intent = new Intent(donorRegister.this, needyRegister.class);
                            intent.putExtra("name",name.getText().toString());
                            intent.putExtra("email",email.getText().toString());
                            intent.putExtra("phone",phone.getText().toString());
                            intent.putExtra("address",address.getText().toString());
                            intent.putExtra("password",password.getText().toString());
                            startActivity(intent);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"password not correct",Toast.LENGTH_LONG).show();
                    }
                }
            }}
        });
    }

    private Boolean validateInfo(String name1, String email1, String phone1, String address1, String pass1, String rePass1) {
        if(name1.length()==0){
            name.requestFocus();
            name.setError("يجب ان لا يكون فارغ");
            return false;}
//        else if(!name1.matches("[a-zA-Zا-ي]+")){
//            name.requestFocus();
//            name.setError("يجب ان يتضمن حروف فقط");
//            return false;
        //}
         else if(email1.length()==0){
            email.requestFocus();
            email.setError("يجب ان لا يكون فارغ");
            return false;
        }else if(!email1.matches("[a-z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("خطأ في الايميل");
            return false;
        }else if(phone1.length()==0){
            phone.requestFocus();
            phone.setError("يجب ان لا يكون فارغ");
            return false;
        }else if(!phone1.matches("[0-9]{10}$")){
            phone.requestFocus();
            phone.setError("خطأ في الرقم");
            return false;
        }else if(address1.length()==0){
            address.requestFocus();
            address.setError("يجب ان لا يكون فارغ");
            return false;
        }else if(pass1.length()<=5){
            password.requestFocus();
            password.setError("يجب ان تتكون كلمة السر من 6 خانات او اكثر");
            return false;
        }else if(!rePass1.equals(pass1)){
            repassword.requestFocus();
            repassword.setError("يجب ان تتطابق كلمة السر");
            return false;
        }
        return true;
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
                                    intent.putExtra("userType", "1");
                                    intent.putExtra("type", "register");
                                    intent.putExtra("phoneNo", phone1);
                                    intent.putExtra("name",name.getText().toString());
                                    intent.putExtra("email",email.getText().toString());
                                    intent.putExtra("address",address.getText().toString());
                                    intent.putExtra("password",password.getText().toString());
                                    startActivity(intent);
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
                        Toast.makeText(getApplicationContext(),"Error Response 101 ",Toast.LENGTH_SHORT).show();
                    }
                });
// Access the RequestQueue through your singleton class.
        rq.add(jsonObjectRequest);
    }
}