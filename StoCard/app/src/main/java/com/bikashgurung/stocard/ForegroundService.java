package com.bikashgurung.stocard;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.DestinationModel;
import com.bikashgurung.stocard.DB.PassingDataModel;
import com.bikashgurung.stocard.DB.StoreModel;
import com.bikashgurung.stocard.Retrofit.ApiInterface;
import com.bikashgurung.stocard.Retrofit.Distance.DistanceResponse;
import com.bikashgurung.stocard.Retrofit.Distance.Element;
import com.bikashgurung.stocard.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;
    private GpsTracker gpsTracker;

    Double branch_longitude, branch_latitude, user_longitude, user_latitude;
    String distance, destination_address;
    List<PassingDataModel> passModel = new ArrayList<>();

    String full_address = null;
    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();


    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Alert")
                .setContentText(input)
                .setSmallIcon(R.drawable.stocard_icon)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();
        startForegroundService();
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void startForegroundService() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                getUserLocation();
                getBranchLocation();
                //Toast.makeText(ForegroundService.this, "Tracking",Toast.LENGTH_SHORT).show();
            }
        }, delay);
    }

    private void getUserLocation() {
        gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            user_latitude = gpsTracker.getLatitude();
            user_longitude = gpsTracker.getLongitude();

            //latitudeTextView.setText(String.valueOf(user_latitude));
            //longitudeTextView.setText(String.valueOf(user_longitude));

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void getBranchLocation() {
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<StoreModel> storeModel = db.storeDao().getAll();

        if (storeModel.isEmpty()) {

            Toast.makeText(this, "Cannot find ", Toast.LENGTH_SHORT).show();

        } else {
            for (int i = 0; i < storeModel.size(); i++) {
                branch_latitude = Double.valueOf(storeModel.get(i).store_latitude);
                branch_longitude = Double.valueOf(storeModel.get(i).store_longitute);

                System.out.println(branch_latitude);
                System.out.println(branch_longitude);

                calculateDistance();
            }
        }
    }

    private void calculateDistance() {

        Map<String, String> mapQuery = new HashMap<>();
        mapQuery.put("origins", user_latitude + "," + user_longitude);
        mapQuery.put("destinations", branch_latitude + "," + branch_longitude);

        mapQuery.put("key", "AIzaSyAJXVZvxdsuKE_pnjVW-75c2ki076skvk0");
        ApiInterface client = RetrofitClient.getInstance().getApi();

        Call<DistanceResponse> call = client.getDistanceInfo(mapQuery);
        call.enqueue(new Callback<DistanceResponse>() {
            @Override
            public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                if (response.body() != null &&
                        response.body().getRows() != null &&
                        response.body().getRows().size() > 0 &&
                        response.body().getRows().get(0) != null &&
                        response.body().getRows().get(0).getElements() != null &&
                        response.body().getRows().get(0).getElements().size() > 0 &&
                        response.body().getRows().get(0).getElements().get(0) != null &&
                        response.body().getRows().get(0).getElements().get(0).getDistance() != null &&
                        response.body().getRows().get(0).getElements().get(0).getDuration() != null) {

                    Element element = response.body().getRows().get(0).getElements().get(0);

                    distance = String.valueOf(element.getDistance().getValue());
                    destination_address = String.valueOf(response.body().getDestinationAddresses());

                    if (passModel == null) {
                        passModel.add(new PassingDataModel(distance, destination_address));
                    }else{
                        passModel.clear();
                        passModel.add(new PassingDataModel(distance, destination_address));
                    }

                }
                sendNotification();
            }

            @Override
            public void onFailure(Call<DistanceResponse> call, Throwable t) {

            }
        });
    }

    private void sendNotification() {
        int compare = 0;

        ArrayList<Integer> resultList = new ArrayList();

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<DestinationModel> fullAddressList = db.destinationDao().getAll();

        for (int counter = 0; counter < passModel.size(); counter++) {

            compare = Integer.parseInt(passModel.get(counter).getDistance());
            full_address = passModel.get(counter).getDestination_address();

            resultList.add(compare);

        }

        for (int j=0; j<resultList.size(); j++){
            if (resultList.get(j) <= 500){
               // notifyUsers();
                /*Intent intent = new Intent(this, MyBroadcastReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/

                System.out.println(resultList.get(j));

                /*for (int A=0; A<fullAddressList.size(); A++){
                    String DbFullAddress = fullAddressList.get(A).getFull_address();
                    String DbBankName = fullAddressList.get(A).getBankName().trim();
                    String DbBranchName = fullAddressList.get(A).branchName;

                    if (full_address.equals(DbFullAddress)){
                        System.out.println(DbBankName + " is near you");
                    }
                }*/

                notifyUser();
            }
        }
    }

    private void notifyUser() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(myBroadcastReceiver, filter);
    }



}
