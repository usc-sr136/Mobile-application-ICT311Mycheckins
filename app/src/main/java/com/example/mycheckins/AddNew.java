package com.example.mycheckins;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class AddNew extends Fragment implements  DatePickerDialog.OnDateSetListener {


    //Variables
    Bitmap yourImage = null;
    private Button btn_date, showMap, share, camera;
    private TextView txt_loc;
    private EditText txt_title, txt_detail, txt_place;
    ImageView image = null;
    Context c;
    String title,place,detail,date,loc;
    Double lat = 0.0, lon = 0.0;
    Location gps_loc = null, network_loc = null, final_loc = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new, container, false);
        c = container.getContext();

        txt_loc = view.findViewById(R.id.txt_location);
        btn_date = view.findViewById(R.id.btn_date);
        showMap = view.findViewById(R.id.btn_showMap);
        camera = view.findViewById(R.id.btn_cam);
        share = view.findViewById(R.id.btn_share);
        image = view.findViewById(R.id.img1);
        txt_title = view.findViewById(R.id.txt_title);
        txt_place = view.findViewById(R.id.txt_place);
        txt_detail = view.findViewById(R.id.txt_details);


        //Location
        getLocation();
        if (final_loc != null) {
            lat = final_loc.getLatitude();
            lon = final_loc.getLongitude();
        }
        txt_loc.setText("Lat: "+lat.toString() + "\nLon: " + lon.toString());


        //current date
        btn_date.setText(getDate());
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();
            }
        });

        //camera
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cam();
            }
        });

        //share data
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();

            }
        });

        //Map
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("Lat", lat);
                bundle.putDouble("Lon", lon);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        saveData();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap yourImage = (Bitmap) data.getExtras().get("data");
        image.setImageBitmap(yourImage);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String date = dayOfMonth + "/" + month + "/" + year;
        this.btn_date.setText(date);
    }


    //functions

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(c,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public String getDate() {
        Date d = Calendar.getInstance().getTime();
        System.out.println("Current time => " + d);

        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        String formattedDate = df.format(d);
        return formattedDate;
    }

    public void cam() {


        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, 0);
    }

    public void saveData() {
        data();
        // convert bitmap to byte
        try{
            byte imageInByte[] = null;
            yourImage = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            yourImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            imageInByte = stream.toByteArray();
            Log.e("outputbeforeconversion", imageInByte.toString());
            // Inserting Items
            Log.d("Insert: ", "Inserting ..");
            Item p = new Item();

            p.setTitle(title);
            p.setPlace(place);
            p.setDetails(detail);
            p.setDate(date);
            p.setLoc(loc);
            p.setImage(imageInByte);

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            databaseHelper.addItems(p);
            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(getActivity(), "Data not Inserted", Toast.LENGTH_SHORT).show();
        }

    }
    public void getLocation() {
        LocationManager locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

        }else{
            requestPermission();
        }
        try {
            gps_loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (gps_loc != null) {
            final_loc = gps_loc;
        } else if (network_loc != null) {
            final_loc = network_loc;
        }
    }


    public void share() {
        data();
        String subject = "Title: "+title +
                "\nPlace:"+ place +
                "\nDetail: "+ detail +
                "\nDate: "+ date +
                "\nLocation: "+ loc;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, subject);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    void data(){
        title = txt_title.getText().toString();
        place = txt_place.getText().toString();
        loc = txt_loc.getText().toString();
        detail = txt_detail.getText().toString();
        date = btn_date.getText().toString();
    }

    void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission Needed")
                    .setMessage("Sorry")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}