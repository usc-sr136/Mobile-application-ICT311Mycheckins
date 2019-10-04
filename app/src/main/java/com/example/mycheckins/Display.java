package com.example.mycheckins;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.io.ByteArrayInputStream;


public class Display extends Fragment {
    View view;
    TextView title, place, details, location, date;
    Button btnDelete;
    ImageView imageDetail;
    Bitmap theImage;
    Context activity;
    int value = 0;

    public Display() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = container.getContext();

        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_display, container, false);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        imageDetail = (ImageView) view.findViewById(R.id.imageView1);
        title = (TextView) view.findViewById(R.id.txt_title1);
        place = (TextView) view.findViewById(R.id.txt_place1);
        details = (TextView) view.findViewById(R.id.txt_details1);
        location = (TextView) view.findViewById(R.id.txt_location1);
        date = (TextView) view.findViewById(R.id.txt_date1);

        Bundle bundle = getActivity().getIntent().getBundleExtra("data");

        value = bundle.getInt("Position");
        System.out.print(value);

        DatabaseHelper dbHandler = new DatabaseHelper(activity);
        Item list1 = dbHandler.getItem(value+1);
        if(list1!=null){
            title.setText(list1.getTitle());
            place.setText(list1.getPlace());
            details.setText(list1.getDetails());
            date.setText(list1.getDate());
            location.setText(list1.getLoc());
            ByteArrayInputStream imageStream = new ByteArrayInputStream(list1.getImage());
                theImage = BitmapFactory.decodeStream(imageStream);
            imageDetail.setImageBitmap(theImage);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
                Log.d("Delete Image: ", "Deleting.....");
                databaseHelper.deleteItem(new Item(value+1));

               // /after deleting data go to main page
                Intent i = new Intent(getActivity().getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

}
