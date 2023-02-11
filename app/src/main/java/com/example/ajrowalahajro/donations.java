package com.example.ajrowalahajro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link donations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class donations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public donations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment donations.
     */
    // TODO: Rename and change types and number of parameters
    public static donations newInstance(String param1, String param2) {
        donations fragment = new donations();
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
    SwipeRefreshLayout srl;
    ListView listView;

    ArrayList<String> donationsid = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> encode = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    //ListClass l = new ListClass();
    Spinner s;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args;
        args = getActivity().getIntent().getExtras();
        String loginType =(args.getString("loginType"));
        String userID =(args.getString("id"));
        String username =(args.getString("username"));
        donationsid.clear();
        id.clear();
        title.clear();
        date.clear();
        encode.clear();
        address.clear();
        description.clear();
        View v = inflater.inflate(R.layout.fragment_donations, container, false);
        s=v.findViewById(R.id.spinnerDonations);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long L) {
                donationsid.clear();
                id.clear();
                title.clear();
                date.clear();
                encode.clear();
                address.clear();
                description.clear();
                ListClass l = new ListClass();
//                Toast.makeText(getActivity().getApplicationContext(), "spinner "+(i), Toast.LENGTH_SHORT).show();
                srl=v.findViewById(R.id.refresh2);
                listView = v.findViewById(R.id.listdonations);
                l.getData2(getActivity().getApplicationContext(), "1",donationsid,id, title, date, encode,address, description,""+i,listView);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent,View view,int position,long id11){
                        if(loginType.equals("1")){
                            Toast.makeText(getActivity().getApplicationContext(), "clicked "+(position), Toast.LENGTH_SHORT).show();
                        }else if(loginType.equals("2")){
                            Intent intent = new Intent(getActivity(), showNeed.class);
                            intent.putExtra("donID", donationsid.get(position));
                            intent.putExtra("id", id.get(position));
                            intent.putExtra("donorid", l.donorid.get(position));
                            intent.putExtra("needyID", userID);
                            intent.putExtra("encode", encode.get(position));
                            startActivity(intent);
//                            Toast.makeText(getActivity().getApplicationContext(), "clicked "+(position), Toast.LENGTH_SHORT).show();
                        }
                    }});
                srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        donationsid.clear();
                        id.clear();
                        title.clear();
                        date.clear();
                        encode.clear();
                        address.clear();
                        description.clear();
                        l.getData2(getActivity().getApplicationContext(), "1",donationsid,id, title, date, encode,address, description,""+i,listView);
                        srl.setRefreshing(false);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }
}