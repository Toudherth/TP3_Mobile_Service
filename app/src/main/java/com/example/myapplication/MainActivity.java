package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private FrameLayout container1, container2;

    private AfficheFragment afficheFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container1= findViewById(R.id.fragment_container);
        //container2= findViewById(R.id.fragment_container2);

        afficheFragment = new AfficheFragment();

        FragmentManager fragmentManager= getSupportFragmentManager(); // Obtenir une référence au gestionnaire de fragments
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();// Commencer la transaction de fragments

        SaisieFragment saisieFragment = new SaisieFragment();
        fragmentTransaction.add(R.id.fragment_container, saisieFragment); // Ajouter le fragment au gabarit spécifié dans le fichier de layout de l'activité
        fragmentTransaction.commit(); // Terminer la transaction

        // Check if network connection is available
        myClickHandler(findViewById(android.R.id.content));

    }

    public void myClickHandler(View view) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            System.out.println("je suis connecter ");
            Toast.makeText(this, "  connection etablit.", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("je ne suis pas connecter ");

            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }


}