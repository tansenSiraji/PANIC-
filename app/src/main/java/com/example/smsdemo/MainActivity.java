package com.example.smsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Button btn_Send;
//    EditText number;
//    EditText message;


    String number = "+8801732984529", message = "HELP";

    int i;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            i++;
            Handler handler= new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    i=0;
                }
            };
            if(i==1){
                //panicNow.setText("NOT AN EMERGENCY");
                handler.postDelayed(runnable,200);
            }
            else if(i==2){
                if(!number.equals("")){

                    String num = number;
                    String sms = message;
                    if(permission(num, sms)){
                        Toast.makeText(MainActivity.this, "Message Delivered", Toast.LENGTH_SHORT).show();
                    }
                }
                //panicNow.setText("NEED HELP");
            }

            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //btn_Send = findViewById(R.id.btn_send);
        //number = findViewById(R.id.number);
        //message = findViewById(R.id.Message);


       /* btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!number.getText().toString().equals("")){

                    String num = number.getText().toString();
                    String sms = message.getText().toString();
                    if(permission(num, sms)){
                        Toast.makeText(MainActivity.this, "Message Delivered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/

    //}

    private boolean permission(String num, String sms) {
        int permission_sms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int permission_state = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if(permission_sms == PackageManager.PERMISSION_GRANTED){

            if(permission_state == PackageManager.PERMISSION_GRANTED){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num, null, sms, null, null);
                return true;

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 100);

            }
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        }
        return false;
    }
}
