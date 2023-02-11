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

public class ListClass extends AppCompatActivity {
    URL u=new URL();
    adp ad;
    public String getData=u.getData;
            //"https://ajrowalahajro.000webhostapp.com/needs.php";

            //"http://192.168.1.14/projectDB/needs.php";
    public void getData(Context c, String status,ArrayList<String> needsid,ArrayList<String> id, ArrayList<String> title, ArrayList<String> date , ArrayList<String> encode
            , ArrayList<String> address, ArrayList<String> description,String kind, ListView listView) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getData, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                needsid.add(UserDetailArray.getJSONObject(i).getString("Id"));
                                id.add(UserDetailArray.getJSONObject(i).getString("NeedyID"));
                                title.add(UserDetailArray.getJSONObject(i).getString("Title"));
                                date.add(UserDetailArray.getJSONObject(i).getString("Date"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                address.add(UserDetailArray.getJSONObject(i).getString("Address"));
                                description.add(UserDetailArray.getJSONObject(i).getString("Description"));
                            }
                            ad=new adp(c,title,date,encode,address,description);
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
                params.put("Status", status);
                params.put("Kind", kind);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
adpDonations ad2;
    public String getData2=u.getData2;
            //"http://192.168.1.7/projectDB/donations.php";
    //"https://ajrowalahajro.000webhostapp.com/needs.php";

    //"http://192.168.1.14/projectDB/needs.php";
    ArrayList<String> donorid=new ArrayList<String>();
    public void getData2(Context c, String status,ArrayList<String> donationid,ArrayList<String> id, ArrayList<String> title, ArrayList<String> date , ArrayList<String> encode
            , ArrayList<String> address, ArrayList<String> description,String kind, ListView listView) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getData2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                donationid.add(UserDetailArray.getJSONObject(i).getString("Id"));
                                id.add(UserDetailArray.getJSONObject(i).getString("donorID"));
                                donorid.add(UserDetailArray.getJSONObject(i).getString("donorID"));
                                title.add(UserDetailArray.getJSONObject(i).getString("Title"));
                                date.add(UserDetailArray.getJSONObject(i).getString("Date"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                address.add(UserDetailArray.getJSONObject(i).getString("Address"));
                                description.add(UserDetailArray.getJSONObject(i).getString("Description"));
                            }
                            ad2=new adpDonations(c,id,title,date,encode,address,description);
                            listView.setAdapter(ad2);
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
                params.put("Status", status);
                params.put("Kind", kind);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    adpDonations adminADPDonaitions;
    public String getDataDonaitios=u.getDataDonaitios;
            //"http://192.168.1.7/projectDB/getDonations.php";
    //"https://ajrowalahajro.000webhostapp.com/needs.php";

    //"http://192.168.1.14/projectDB/needs.php";
    public void getDataDonaitios(Context c,ArrayList<String> id, ArrayList<String> title, ArrayList<String> date , ArrayList<String> encode
            , ArrayList<String> address, ArrayList<String> description, ListView listView) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getDataDonaitios, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                id.add(UserDetailArray.getJSONObject(i).getString("DonorID"));
                                title.add(UserDetailArray.getJSONObject(i).getString("Title"));
                                date.add(UserDetailArray.getJSONObject(i).getString("Date"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                address.add(UserDetailArray.getJSONObject(i).getString("Address"));
                                description.add(UserDetailArray.getJSONObject(i).getString("Description"));
                            }
                            adminADPDonaitions=new adpDonations(c,id,title,date,encode,address,description);
                            listView.setAdapter(adminADPDonaitions);
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
//                params.put("Status", status);
//                params.put("Kind", kind);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
adpDonations adminADPNeeds;
    public String getDataNeeds=u.getDataNeeds;
            //"http://192.168.1.7/projectDB/getNeeds.php";
    //"https://ajrowalahajro.000webhostapp.com/needs.php";

    //"http://192.168.1.14/projectDB/needs.php";
    public void getDataNeeds(Context c,ArrayList<String> needid,ArrayList<String> id, ArrayList<String> title, ArrayList<String> date , ArrayList<String> encode
            , ArrayList<String> address, ArrayList<String> description, ListView listView) {
        RequestQueue rq = Volley.newRequestQueue(c);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST, getDataNeeds, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray UserDetailArray = jsonResponse.getJSONArray("dishs");
                            for (int i = 0; i < UserDetailArray.length(); i++) {
                                needid.add(UserDetailArray.getJSONObject(i).getString("Id"));
                                id.add(UserDetailArray.getJSONObject(i).getString("NeedID"));
                                title.add(UserDetailArray.getJSONObject(i).getString("Title"));
                                date.add(UserDetailArray.getJSONObject(i).getString("Date"));
                                encode.add(UserDetailArray.getJSONObject(i).getString("Pic"));
                                address.add(UserDetailArray.getJSONObject(i).getString("Address"));
                                description.add(UserDetailArray.getJSONObject(i).getString("Description"));
                            }
                            adminADPNeeds=new adpDonations(c,id,title,date,encode,address,description);
                            listView.setAdapter(adminADPNeeds);
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
//                params.put("Status", status);
//                params.put("Kind", kind);
                return  params;
            }
        };
        rq.add(jsonObjectRequest);
    }

}
