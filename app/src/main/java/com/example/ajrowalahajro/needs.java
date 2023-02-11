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
 * Use the {@link needs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class needs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public needs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment needs.
     */
    // TODO: Rename and change types and number of parameters
    public static needs newInstance(String param1, String param2) {
        needs fragment = new needs();
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
    ArrayList<String> needsid = new ArrayList<String>();
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
        String address1 =(args.getString("address"));
        String phone =(args.getString("phone"));
        needsid.clear();
        id.clear();
        title.clear();
        date.clear();
        encode.clear();
        address.clear();
        description.clear();
        View v = inflater.inflate(R.layout.fragment_needs, container, false);
        s=v.findViewById(R.id.spinnerNeeds);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long L) {
                needsid.clear();
                id.clear();
                title.clear();
                date.clear();
                encode.clear();
                address.clear();
                description.clear();
                ListClass l = new ListClass();
                Toast.makeText(getActivity().getApplicationContext(), "spinner "+(i), Toast.LENGTH_SHORT).show();
                srl=v.findViewById(R.id.refresh);
                listView = v.findViewById(R.id.listneeds);
                if(loginType.equals("1")){
                    l.getData(getActivity().getApplicationContext(), "1",needsid,id, title, date, encode,address, description,""+i,listView);
                }else if(loginType.equals("2")){
                    l.getData2(getActivity().getApplicationContext(), "1",needsid,id, title, date, encode,address, description,""+i,listView);
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent,View view,int position,long id11){
                        if(loginType.equals("1")){
                            Intent intent = new Intent(getActivity(), donate.class);
                            intent.putExtra("kind", ""+i);
                            intent.putExtra("id", userID);
                            intent.putExtra("loginType", loginType);
                            intent.putExtra("needyid", id.get(position));
                            intent.putExtra("needsid", needsid.get(position));
                            intent.putExtra("title", title.get(position));
                            intent.putExtra("username", username);
                            intent.putExtra("address", address1);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
//                            Toast.makeText(getActivity().getApplicationContext(), "clicked "+(position), Toast.LENGTH_SHORT).show();
                        }else if(loginType.equals("2")){
                            Intent intent = new Intent(getActivity(), showNeed.class);
                            intent.putExtra("donID", needsid.get(position));
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
                        needsid.clear();
                        id.clear();
                        title.clear();
                        date.clear();
                        encode.clear();
                        address.clear();
                        description.clear();
                        if(loginType.equals("1")){
                            l.getData(getActivity().getApplicationContext(), "1",needsid,id, title, date, encode,address, description,""+i,listView);
                        }else if(loginType.equals("2")){
                            l.getData2(getActivity().getApplicationContext(), "1",needsid,id, title, date, encode,address, description,""+i,listView);
                        }
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