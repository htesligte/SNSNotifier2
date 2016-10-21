package nl.comsi.snsnotifier2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    protected NotificationOverview notificationOverviewActivity;

    public NotificationReceiver(NotificationOverview notificationOverviewActivity) {
        this.notificationOverviewActivity = notificationOverviewActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.notificationOverviewActivity.refreshNotifications();
    }
}
