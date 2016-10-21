package nl.comsi.snsnotifier2;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class SNSInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "SNSInstanceIDService";
    public SNSInstanceIDService() {
        Log.d(TAG, "Service started");
        Log.d(TAG, FirebaseInstanceId.getInstance().getToken() );
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken );
    }
}