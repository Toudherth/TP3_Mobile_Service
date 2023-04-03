package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaDrm;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.health.ServiceHealthStats;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class HelloService extends Service {
    public static final String TAG = "PremierService";
    private ExecutorService executorService;

    private IBinder mBiner = new MyBinder();
    private Handler mHandler;

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        System.out.println("je suis ici ------------------------");
        // Call startPretendRunTask() here to start the task

        DownloadFileTask downloadTextTask = new DownloadFileTask() {
            @Override
            protected void onPostExecute(String data) {

                try {
                    JSONObject json = new JSONObject(data);
                    String fruit = json.getString("fruit");
                    String size = json.getString("size");
                    String color = json.getString("color");
                    System.out.println("*********************"  +fruit + " " + size + " " + color);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        try {
            URL url1 = new URL("https://filesamples.com/samples/code/json/sample1.json");
            downloadTextTask.execute(url1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("je suis ici ------------------------");
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyBinder extends Binder {
        HelloService getHelloService() {
            startPretendRunTask();
            return HelloService.this;
        }
    }

    public void startPretendRunTask() {
        final Runnable rennable = new Runnable() {
            @Override
            public void run() {

                DownloadFileTask downloadTextTask = new DownloadFileTask() {
                    @Override
                    protected void onPostExecute(String data) {

                        try {
                            JSONObject json = new JSONObject(data);
                            String fruit = json.getString("fruit");
                            String size = json.getString("size");
                            String color = json.getString("color");
                            System.out.println(fruit + " " + size + " " + color);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                try {
                    URL url1 = new URL("https://filesamples.com/samples/code/json/sample1.json");
                    downloadTextTask.execute(url1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        };

        executorService.submit(rennable);
    }
        }