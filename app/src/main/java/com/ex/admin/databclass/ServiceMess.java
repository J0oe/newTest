package com.ex.admin.databclass;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceMess extends Service {
    private static final int NOTIFY_ID = 101;
    MediaPlayer sound;
    MediaPlayer sound2;

    Integer date;

    @Override
    public void onCreate() {
        date = 0;
        super.onCreate();
        sound = MediaPlayer.create(this, R.raw.soundmess);
        sound2 = MediaPlayer.create(this, R.raw.alarm);


        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ChatOnline")
                .setContentText("processing");

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFY_ID, notification);
        notification.flags = notification.FLAG_NO_CLEAR;

        startForeground(NOTIFY_ID, notification);


        DatabaseReference mDataPost = FirebaseDatabase.getInstance().getReference("Post2/idmax");
        mDataPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sound.start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference mDataAlarm = FirebaseDatabase.getInstance().getReference("Alarm");
        mDataAlarm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (date != 0) {
                    sound2.start();
                }
                date++;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }


}
