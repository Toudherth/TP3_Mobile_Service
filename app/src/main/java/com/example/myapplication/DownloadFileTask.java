package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import androidx.fragment.app.FragmentActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadFileTask extends AsyncTask<URL, Integer, String> {

    private static final String TAG = "DownloadTextTask";

    @Override
    protected String doInBackground(URL... urls) {
        StringBuilder buffer = new StringBuilder();
        for(URL url: urls) {
            try {
                buffer.append(download(url));
            } catch (IOException e) {
                Log.e(TAG, "Erreur lors de l'accès à Internet", e);
            }
        }
        return buffer.toString();
    }

    private String download(URL url) throws IOException {
        Scanner scanner = null;
        try {
            URLConnection connection = url.openConnection();
            InputStream stream = connection.getInputStream();
            scanner = new Scanner(stream).useDelimiter("\0");
            return scanner.next();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
