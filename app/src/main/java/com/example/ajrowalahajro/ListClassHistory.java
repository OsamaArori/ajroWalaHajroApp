package com.example.ajrowalahajro;

import android.content.Context;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListClassHistory {
    URL u=new URL();
    adpHistory adHistory;
    public String getDataHistory=u.getDataHistory;
           // "http://192.168.1.7/projectDB/getDataHistory.php";
            //"http://192.168.1.14/projectDB/getDataHistory.php";
    public void getDataHistory(Context c, String id1,String type, ArrayList<String> address, ArrayList<String> title ,
                               ArrayList<String> encode,ArrayList<String> date, ListView listView) {

        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getDataHistory, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                address.add(UserDetailArray.getJSONObject(i).getString("Address"));
                                title.add(UserDetailArray.getJSONObject(i).getString("Title"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                date.add(UserDetailArray.getJSONObject(i).getString("Date"));

                            }
                            adHistory=new adpHistory(c,address,title,encode,date);
                            listView.setAdapter(adHistory);
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
                params.put("ID", id1);
                params.put("Type", type);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
}
