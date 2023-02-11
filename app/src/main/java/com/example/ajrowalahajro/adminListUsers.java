package com.example.ajrowalahajro;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adminListUsers extends AppCompatActivity {
    ArrayList<String> id=new ArrayList<String>();
    adminADPUsers ad;
    //public String getData="http://192.168.1.4/projectDB/getDonors.php";
    //"https://ajrowalahajro.000webhostapp.com/needs.php";

    //"http://192.168.1.14/projectDB/needs.php";
    public void getData(Context c, ArrayList<String> name, ArrayList<String> pic , ListView listView,String getData) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getData, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                id.add(UserDetailArray.getJSONObject(i).getString("Id"));
                                name.add(UserDetailArray.getJSONObject(i).getString("Name"));
                                pic.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                            }
                            ad=new adminADPUsers(c,name,pic);
                            listView.setAdapter(ad);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(c,"Error Response 102",Toast.LENGTH_SHORT).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
//                params.put("name", status);
//                params.put("Kind", kind);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
}
