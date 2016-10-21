package nl.comsi.snsnotifier2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class NotificationContract {
    private NotificationContract() {}

    public static class NotificationEntry implements BaseColumns {
        public static final String TABLE_NAME = "notifications";
        public static final String COLUMN_NAME_DATETIME_RECEIVED = "datetime_received";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_PROCESSED = "processed";
        public static final String COLUMN_NAME_MESSAGE = "message";
    }
}