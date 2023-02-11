package com.example.ajrowalahajro;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    URL u=new URL();
    public String getDonorInfourl=u.getInfourl;
    public String getNeedyInfourl=u.getNeedyInfourl;
            //"http://192.168.1.7/projectDB/getDonorInfo.php";
            //"http://192.168.1.14/projectDB/getDonorInfo.php";
    public String updateInfourl=u.updateInfourl;
                    //"http://192.168.1.7/projectDB/updateDonorInfo.php";
                    //"http://192.168.1.14/projectDB/updateDonorInfo.php";
    EditText id,name,email,phone,address, password;
    Button update;
    ImageView pic;
    public String id1;
    public String loginType;
    Bundle args;
    TextView restPass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        id = (EditText) view.findViewById(R.id.id);
        name = (EditText) view.findViewById(R.id.username);
        email = (EditText) view.findViewById(R.id.email);
        phone = (EditText) view.findViewById(R.id.phone);
        address = (EditText) view.findViewById(R.id.address);
        password = (EditText) view.findViewById(R.id.pass);
        pic = (ImageView) view.findViewById(R.id.imageView);
        args = getActivity().getIntent().getExtras();
        id1 =(args.getString("id"));
        loginType=(args.getString("loginType"));
        Toast.makeText(getActivity().getApplicationContext(),id1,Toast.LENGTH_SHORT).show();
        encodepic=(args.getString("pic"));
        if(loginType.equals("1")){
            getData(id1,getDonorInfourl);
        }else if(loginType.equals("2")){
            getData(id1,getNeedyInfourl);
        }
        password.setVisibility(View.GONE);
        update=view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), editProfile.class);
                intent.putExtra("type", loginType);
                intent.putExtra("id", id1);
                intent.putExtra("pic", encodepic);
                startActivity(intent);
            }
        });
        restPass=view.findViewById(R.id.restPass);
        restPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), restPass.class);
                intent.putExtra("type", loginType);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });
        return view;
    }
    public Bitmap bit;
    String encodepic="123";
    ImageProcess ip=new ImageProcess();

    private void getData(String id1,String url) {
        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
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
                        Toast.makeText(getActivity().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
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

//    private void updateData(String id1) {
//        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
//        StringRequest jsonObjectRequest = new StringRequest
//                (Request.Method.POST, updateInfourl, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getActivity().getApplicationContext(),"updated",Toast.LENGTH_SHORT).show();
//                        getData(id1);
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//                        Toast.makeText(getActivity().getApplicationContext(),"Error Response 102",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                ){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String>params=new HashMap<String, String>();
//
//
//                params.put("Id", id1);
//                params.put("Name", name.getText().toString());
//                params.put("Email", email.getText().toString());
//                params.put("Phone", phone.getText().toString());
//                params.put("Address", address.getText().toString());
//                params.put("Pass", password.getText().toString());
//                if(bit!=null) {
//                    params.put("Pic", ip.encode(bit));
//                }else{
//                    params.put("Pic",encodepic);
//                }
//                return  params;
//            }
//        };
//// Access the RequestQueue through your singleton class.
//        rq.add(jsonObjectRequest);
//    }
}