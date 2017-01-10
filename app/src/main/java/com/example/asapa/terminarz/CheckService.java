package com.example.asapa.terminarz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CheckService extends Service {
    final String LOG_TAG = "myLogs";
    ExecutorService es;


    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(5);

    }

    public void onDestroy() {
        super.onDestroy();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        int time = intent.getIntExtra("time", 1);
        String eventName=intent.getStringExtra("eventName");
        String eventTime=intent.getStringExtra("eventTime");
                MyRun mr = new MyRun(time, startId, eventName,eventTime);
        es.execute(mr);
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {

        int time;
        int startId;
        String eventName;
        String eventTime;

        public MyRun(int time, int startId, String eventName,String eventTime ) {
            this.time = time;
            this.startId = startId;
            this.eventName=eventName;
            this.eventTime=eventTime;
        }

        public void run() {

            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
               // Log.d(LOG_TAG, "MyRun#" + startId + " someRes = " + someRes.getClass() );
                android.support.v4.app.NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Upcoming event:"+eventName)
                                .setContentText(eventTime);
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(getApplicationContext(), LoginActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(AllTasks.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(startId, mBuilder.build());


            } catch (NullPointerException e) {
                Log.d(LOG_TAG, "MyRun#" + startId + " error notification");
            }
            stop();
        }

        void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelf(" + startId + ")");
            stopSelf(startId);
        }
    }
}
