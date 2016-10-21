package nl.comsi.snsnotifier2;

import android.app.Activity;

import java.util.ArrayList;

public class NotificationRefresher {

    public interface INotificationRefresherCallback {
        void refreshNotifications(ArrayList<Notification> notifications);
    }

    public NotificationRefresher(Activity activity) {
        INotificationRefresherCallback notificationOverviewActivity = (INotificationRefresherCallback) activity;
        NotificationDbHelper dbHelper = new NotificationDbHelper(activity);
        notificationOverviewActivity.refreshNotifications(dbHelper.getAllNotifications());
    }
}
