package nl.comsi.snsnotifier2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationOverview extends AppCompatActivity implements NotificationRefresher.INotificationRefresherCallback{
    public final static String TAG = "NotificationOverview";
    private NotificationReceiver notificationReceiver;

    public NotificationOverview()
    {
        notificationReceiver = new NotificationReceiver(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_overview);
        registerReceiver(notificationReceiver, new IntentFilter(SNSMessagingService.MESSAGE_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(notificationReceiver);
    }

    public void refreshNotifications() {
        new RefreshOperationTask(this).execute();
    }

    @Override
    public void refreshNotifications(ArrayList<Notification> notifications) {
        TableLayout tl = (TableLayout) findViewById(R.id.NotificationTable);

        // clear rows except first
        while(tl.getChildCount() > 1)
            tl.removeView(tl.getChildAt(tl.getChildCount() - 1));

        for(Notification n : notifications) {

            TableRow row = new TableRow(this);

            TextView textDateTime = new TextView(this);
            textDateTime.setText(n.getDatetime());

            TextView textType = new TextView(this);
            textType.setText(n.getType());

            TextView textProcessed = new TextView(this);
            textProcessed.setText(String.valueOf(n.getProcessed()));

            TextView textMessage = new TextView(this);
            textMessage.setText(n.getMessage());

            row.addView(textDateTime);
            row.addView(textType);
            row.addView(textProcessed);
            row.addView(textMessage);
            tl.addView(row);
        }
    }

    private class RefreshOperationTask extends AsyncTask<Void, Void, ArrayList<Notification>>
    {
        private NotificationOverview activity;

        public RefreshOperationTask(NotificationOverview overview)
        {
            this.activity = overview;
        }

        @Override
        protected ArrayList<Notification> doInBackground(Void... params) {
            NotificationDbHelper dbHelper = new NotificationDbHelper(this.activity);
            return dbHelper.getAllNotifications();
        }

        @Override
        protected void onPostExecute(ArrayList<Notification> notifications) {
            this.activity.refreshNotifications(notifications);
        }
    }
}