package com.example.mycheckins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getBundleExtra("data");
        int value = 0, p;
        value = bundle.getInt("Activity");

        p = bundle.getInt("Position");
        loadFragment(new AddNew());
        if (value == 1){
            loadFragment(new AddNew());
        }
        else if (value == 2)
        {
            Display frag = new Display();
            frag.setArguments(bundle);
            loadFragment(frag);
        }

    }

    public  void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentplace, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
