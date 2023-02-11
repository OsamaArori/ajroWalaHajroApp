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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class history extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment history.
     */
    // TODO: Rename and change types and number of parameters
    public static history newInstance(String param1, String param2) {
        history fragment = new history();
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
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> encode = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ListClassHistory l = new ListClassHistory();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        String id = getArguments().getString("id");
//        String type = getArguments().getString("type");
        Bundle args;
        args = getActivity().getIntent().getExtras();
        String type =(args.getString("loginType"));
        String id =(args.getString("id"));
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        Toast.makeText(getActivity().getApplicationContext(),id+" ugjhgu",Toast.LENGTH_SHORT).show();
        srl=v.findViewById(R.id.refresh);
        listView = v.findViewById(R.id.listhistory);
        l.getDataHistory(getActivity().getApplicationContext(), id,type, address ,title, encode,date, listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
//                Intent intent = new Intent(getActivity(), show.class);
//                intent.putExtra("title", title.get(position));
//                intent.putExtra("info", info.get(position));
//                intent.putExtra("pic", encode.get(position));
//                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), "history"+position, Toast.LENGTH_SHORT).show();

            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                address.clear();
                title.clear();
                encode.clear();
                date.clear();
                l.getDataHistory(getActivity().getApplicationContext(), id,type, address, title, encode,date, listView);
                srl.setRefreshing(false);
            }
        });
//        Button close=v.findViewById(R.id.close);
//        history hh=new history();
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                getActivity().supportFragmentManager
////                        .beginTransaction()
////                        .remove(this)
////                        .commit()
////                getActivity().supportFragmentManager.popBackStack();
//               // getActivity().getFragmentManager().beginTransaction().remove(history.this).commit();
//                profile nextFrag= new profile();
//                getActivity().getSupportFragmentManager().beginTransaction().remove(history.this)
//                        .commit();
//
//               // container.removeView(view);
//            }
//        });
        return v;
    }
}