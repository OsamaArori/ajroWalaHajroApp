package com.example.ajrowalahajro;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notifications extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static notifications newInstance(String param1, String param2) {
        notifications fragment = new notifications();
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
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> encode = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args;
        args = getActivity().getIntent().getExtras();
        String loginType =(args.getString("loginType"));
        String userID =(args.getString("id"));
        name.clear();
        date.clear();
        encode.clear();
        description.clear();
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        listNotf l = new listNotf();
//        Toast.makeText(getActivity().getApplicationContext(), "spinner "+(i), Toast.LENGTH_SHORT).show();
        srl=v.findViewById(R.id.refresh);
        listView = v.findViewById(R.id.listnotifications);
        l.getData(getActivity().getApplicationContext(), userID,loginType, name, date, encode, description,listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                    Intent intent = new Intent(getActivity(), showNotf.class);
                    intent.putExtra("notfid", l.notfid.get(position));
                    intent.putExtra("id", l.fromid.get(position));
                    intent.putExtra("type", loginType);
                    intent.putExtra("encode", l.pic.get(position));
                    startActivity(intent);
            }});
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                name.clear();
                date.clear();
                encode.clear();
                description.clear();
                l.getData(getActivity().getApplicationContext(), userID,loginType, name, date, encode, description,listView);
                srl.setRefreshing(false);
            }
        });
        return v;
    }
}