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
 * Use the {@link admin_needs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_needs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_needs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_needs.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_needs newInstance(String param1, String param2) {
        admin_needs fragment = new admin_needs();
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
    ArrayList<String> needid = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> encode = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ListClass l = new ListClass();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        needid.clear();
        id.clear();
        title.clear();
        date.clear();
        encode.clear();
        address.clear();
        description.clear();
        View v = inflater.inflate(R.layout.fragment_admin_needs, container, false);
        srl=v.findViewById(R.id.refresh);
        listView = v.findViewById(R.id.adminlistneeds);
        l.getDataNeeds(getActivity().getApplicationContext(),needid,id,  title, date, encode,address, description,listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id1){
                Intent intent = new Intent(getActivity(), adminShowNeed.class);
                intent.putExtra("needid", needid.get(position));
                intent.putExtra("id", id.get(position));
                intent.putExtra("desc", description.get(position));
                intent.putExtra("picdon", encode.get(position));
                intent.putExtra("date", date.get(position));
                intent.putExtra("address", address.get(position));
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), id.get(position)+" clicked "+(position), Toast.LENGTH_SHORT).show();
            }});
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                needid.clear();
                id.clear();
                title.clear();
                date.clear();
                encode.clear();
                address.clear();
                description.clear();
                l.getDataNeeds(getActivity().getApplicationContext(),needid,id,  title, date, encode,address, description,listView);
                srl.setRefreshing(false);
            }
        });
        return v;
    }
}