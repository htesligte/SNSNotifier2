package nl.comsi.snsnotifier2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import nl.comsi.snsnotifier2.NotificationContract;

public class NotificationDbHelper extends SQLiteOpenHelper {
    public final static String TAG = "NotificationDbHelper";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NotificationContract.NotificationEntry.TABLE_NAME + " (" +
                    NotificationContract.NotificationEntry._ID + " INTEGER PRIMARY KEY, " +
                    NotificationContract.NotificationEntry.COLUMN_NAME_DATETIME_RECEIVED + " DATETIME, " +
                    NotificationContract.NotificationEntry.COLUMN_NAME_TYPE + " TEXT," +
                    NotificationContract.NotificationEntry.COLUMN_NAME_PROCESSED + " INTEGER," +
                    NotificationContract.NotificationEntry.COLUMN_NAME_MESSAGE + " TEXT )"
            ;

    private final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NotificationContract.NotificationEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "snsnotifier.db";

    public NotificationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<Notification> getAllNotifications()
    {
        ArrayList<Notification> notifications = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                NotificationContract.NotificationEntry._ID,
                NotificationContract.NotificationEntry.COLUMN_NAME_DATETIME_RECEIVED,
                NotificationContract.NotificationEntry.COLUMN_NAME_TYPE,
                NotificationContract.NotificationEntry.COLUMN_NAME_PROCESSED,
                NotificationContract.NotificationEntry.COLUMN_NAME_MESSAGE,
        };

        String sortOrder = NotificationContract.NotificationEntry._ID + " DESC";

        Cursor c = db.query(
                NotificationContract.NotificationEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        try {
            while(c.moveToNext())
            {
                Notification n = new Notification();
                n.setID(c.getInt(c.getColumnIndex(NotificationContract.NotificationEntry._ID)));
                n.setDatetime(c.getString(c.getColumnIndex(NotificationContract.NotificationEntry.COLUMN_NAME_DATETIME_RECEIVED)));
                n.setType(c.getString(c.getColumnIndex(NotificationContract.NotificationEntry.COLUMN_NAME_TYPE)));
                n.setProcessed(c.getInt(c.getColumnIndex(NotificationContract.NotificationEntry.COLUMN_NAME_PROCESSED)));
                n.setMessage(c.getString(c.getColumnIndex(NotificationContract.NotificationEntry.COLUMN_NAME_MESSAGE)));
                notifications.add(n);
            }
        } catch( Exception e ) {
            Log.e(TAG, e.getMessage());
        } finally {
            c.close();
        }
        return notifications;
    }
}
