package nl.comsi.snsnotifier2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SNSMessagingService extends FirebaseMessagingService {
    private final static String TAG = "SNSMessagingService";
    public static final String MESSAGE_RECEIVED = "MESSAGE_RECEIVED";

    private NotificationDbHelper dbHelper;
    public SNSMessagingService() {
        Log.d(TAG, "Service started");
        dbHelper = new NotificationDbHelper(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Message received" );
        Log.i(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Data: " + remoteMessage.getData());
        if( remoteMessage.getNotification() != null )
            Log.i(TAG, "Notification body: " + remoteMessage.getNotification().getBody());
        else
            Log.d(TAG, "No notification body received");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

        values.put(NotificationContract.NotificationEntry.COLUMN_NAME_DATETIME_RECEIVED, sdf.format(new Date()));
        values.put(NotificationContract.NotificationEntry.COLUMN_NAME_TYPE, "bounce");
        values.put(NotificationContract.NotificationEntry.COLUMN_NAME_MESSAGE, remoteMessage.getData().toString());
        values.put(NotificationContract.NotificationEntry.COLUMN_NAME_PROCESSED, 0);

        long rowId = db.insert(NotificationContract.NotificationEntry.TABLE_NAME, null, values);
        Log.i(TAG, "Inserted id " + String.valueOf(rowId));

        Intent intent = new Intent(MESSAGE_RECEIVED);
        sendBroadcast(intent);
    }
}
