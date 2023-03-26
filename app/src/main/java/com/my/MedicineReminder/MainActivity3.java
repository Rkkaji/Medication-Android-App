package com.my.MedicineReminder;

import static android.app.PendingIntent.getActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView t1,t2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        t1= findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("userTime", Context.MODE_PRIVATE);
        int hour = sharedPref.getInt("hour",0);
        int minute = sharedPref.getInt("minute",0);
        System.out.println(hour);
        t1.setText(hour+"");
        t2.setText(minute+"");

    }
}