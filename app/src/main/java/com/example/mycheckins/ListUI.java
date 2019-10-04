package com.example.mycheckins;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class ListUI extends Fragment {
    DatabaseHelper db;
    View view;
    DemoAdapter adapter;
    public ListUI() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_ui, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ListView list = view.findViewById(R.id.list);
        adapter =new DemoAdapter(getActivity());

        list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Activity", 2);
                bundle.putInt("Position", position);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.new1)
        {
            Intent intent = new Intent(getActivity().getApplicationContext(), Main2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Activity", 1);
            bundle.putInt("Position", -1);
            intent.putExtra("data", bundle);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.help)
        {
            Intent intent = new Intent(getActivity().getApplicationContext(), Help.class);
            startActivity(intent);
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
