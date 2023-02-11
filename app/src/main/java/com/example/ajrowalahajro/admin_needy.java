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
 * Use the {@link admin_needy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_needy extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_needy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_needy.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_needy newInstance(String param1, String param2) {
        admin_needy fragment = new admin_needy();
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
    adminListUsers l = new adminListUsers();
    URL u=new URL();
    SwipeRefreshLayout srl;
    ListView listView;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> pic = new ArrayList<String>();
    String url=u.getNeedy;
            //"http://192.168.1.7/projectDB/getNeedy.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        name.clear();
        pic.clear();
        l.id.clear();
        View v = inflater.inflate(R.layout.fragment_admin_needy, container, false);
        //adminListUsers l = new adminListUsers();
        srl=v.findViewById(R.id.refresh);
        listView = v.findViewById(R.id.listneedy);
        l.getData(getActivity().getApplicationContext(), name, pic,listView,url);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
               Intent intent = new Intent(getActivity(), adminUsersProfile.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("id", l.id.get(position));
                    intent.putExtra("pic", pic.get(position));
                    startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(), "clicked "+l.id.get(position), Toast.LENGTH_SHORT).show();

//                }else if(loginType.equals("2")){
//                    Toast.makeText(getActivity().getApplicationContext(), "clicked "+(position), Toast.LENGTH_SHORT).show();
//                }
            }});
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                name.clear();
                pic.clear();
                l.id.clear();
                l.getData(getActivity().getApplicationContext(), name, pic,listView,url);
                srl.setRefreshing(false);
            }});
        return v;
    }
}