package com.my.MedicineReminder;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity2<val> extends AppCompatActivity {
    Button timeButton;
    Button test;

    SharedPreferences sharedPref;
    int hour;
    int minute;
    boolean notify = false;
    private Calendar calendar;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        timeButton = findViewById(R.id.timeSelect);
        sharedPref = getSharedPreferences("userTime", Context.MODE_PRIVATE);
        final Button button2 = findViewById(R.id.button3);
        createNotificationChanel();
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println("hello World");
                Intent myIntent2 = new Intent(MainActivity2.this, MainActivity3.class);
                MainActivity2.this.startActivity(myIntent2);
                setNotification();

                }
        });


    }

    private void setNotification(){
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,Notification.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,  PendingIntent.FLAG_IMMUTABLE);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, sharedPref.getInt("hour",0));
        calendar.set(Calendar.MINUTE, sharedPref.getInt("minute",0));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Alarm set Success", Toast.LENGTH_SHORT).show();
    }


    public void popTime(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                hour = selectHour;
                minute = selectMinute;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d", sharedPref.getInt("hour",0), sharedPref.getInt("minute",0)));
                editor.putInt("hour",hour);
                editor.putInt("minute",minute);
                editor.commit();

            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute,false);
        timePickerDialog.show();

    }

    private  void createNotificationChanel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "MedicineReminderChannel";
            String description = "Reminder to take meds";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Med Remind",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}