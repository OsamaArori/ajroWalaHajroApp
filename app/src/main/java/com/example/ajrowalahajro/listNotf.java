package com.example.ajrowalahajro;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

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

public class listNotf extends AppCompatActivity {
    URL u=new URL();
    adpNotf ad;
    ArrayList<String> fromid=new ArrayList<String>();
    ArrayList<String> notfid=new ArrayList<String>();
    ArrayList<String> pic=new ArrayList<String>();
    public String getData = "";
            //"http://192.168.1.7/projectDB/donornotifcations.php";
    //"https://ajrowalahajro.000webhostapp.com/needs.php";

    //"http://192.168.1.14/projectDB/needs.php";
    public void getData(Context c, String userID,String loginType, ArrayList<String> name, ArrayList<String> date, ArrayList<String> encode
            ,  ArrayList<String> description, ListView listView) {
        if(loginType.equals("1")){
            getData=u.donornotifcations;
                    //"http://192.168.1.7/projectDB/donornotifcations.php";
        }else if(loginType.equals("2")){
            getData=u.needynotifcations;
                    //"http://192.168.1.7/projectDB/needynotifcations.php";
        }
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getData, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                notfid.add(UserDetailArray.getJSONObject(i).getString("NotfID"));
                                fromid.add(UserDetailArray.getJSONObject(i).getString("FromId"));
                                name.add(UserDetailArray.getJSONObject(i).getString("Name"));
                                date.add(UserDetailArray.getJSONObject(i).getString("Date"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                pic.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                description.add(UserDetailArray.getJSONObject(i).getString("Descriptions"));
                            }
                            ad = new adpNotf(c, name, date, encode, description);
                            listView.setAdapter(ad);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(c, "Error Response 102", Toast.LENGTH_SHORT).show();
                    }
                }
                ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID", userID);
                params.put("Type", loginType);
                return params;
            }
        };
        rq.add(jsonObjectRequest);
    }
}