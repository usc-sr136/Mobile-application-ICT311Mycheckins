package com.example.mycheckins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView getapp =(TextView) findViewById(R.id.applink);
        getapp.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
