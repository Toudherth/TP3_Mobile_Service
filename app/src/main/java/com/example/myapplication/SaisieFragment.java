package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SaisieFragment extends Fragment {

    private static final String DEBUG_TAG = "HttpExample";


    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextDateNaissance;
    private EditText editTextNumeroTelephone;
    private EditText editTextAdresseMail;
    private CheckBox checkBoxSport;
    private CheckBox checkBoxMusique;
    private CheckBox checkBoxLecture;
    private TextView textView2;

    private Button buttonTelecharger;
    private Button buttonfrag3;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_saisie, container, false);

        // Get the context from the fragment
        Context context = getContext();

        OkHttpClient client = new OkHttpClient();

        textView2= rootView.findViewById(R.id.textView2);


        editTextNom = rootView.findViewById(R.id.editTextNom);
        editTextPrenom = rootView.findViewById(R.id.editTextPrenom);
        editTextDateNaissance = rootView.findViewById(R.id.editTextDateNaissance);
        editTextNumeroTelephone =rootView.findViewById(R.id.editTextNumeroTelephone);
        editTextAdresseMail = rootView.findViewById(R.id.editTextAdresseMail);
        checkBoxSport =rootView.findViewById(R.id.checkBoxSport);
        checkBoxMusique = rootView.findViewById(R.id.checkBoxMusique);
        checkBoxLecture = rootView.findViewById(R.id.checkBoxLecture);

        buttonTelecharger = rootView.findViewById(R.id.buttonTelecharger);

        // Récupérer le bouton de soumission
        Button buttonSoumettre = rootView.findViewById(R.id.buttonSoumettre);
        buttonSoumettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();

                dataBundle.putString("nom", editTextNom.getText().toString());
                dataBundle.putString("prenom", editTextPrenom.getText().toString());
                dataBundle.putString("dateNaissance", editTextDateNaissance.getText().toString());
                dataBundle.putString("numeroTel", editTextNumeroTelephone.getText().toString());
                dataBundle.putString("adresseMail", editTextAdresseMail.getText().toString());
                dataBundle.putBoolean("sport", checkBoxSport.isChecked());
                dataBundle.putBoolean("musique", checkBoxMusique.isChecked());
                dataBundle.putBoolean("lecture", checkBoxLecture.isChecked());

               //Puch le bandle vers l'autre fragment
                Fragment affichageFragmment= new AfficheFragment();
                affichageFragmment.setArguments(dataBundle);
                // Lancer l'activité fragmentAffichage
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, affichageFragmment).commit();
            }
        });

        buttonTelecharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadFileTask downloadTextTask = new DownloadFileTask(){
                    @Override
                    protected void onPostExecute(String data) {

                       // textView2.setText(data);
                        try {
                            JSONObject json = new JSONObject(data);
                            String fruit = json.getString("fruit");
                            String size = json.getString("size");
                            String color = json.getString("color");
                            textView2.setText(fruit+" "+size+" "+color);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                textView2.setText("Chargement en cours...");
                try {
                    URL url1 = new URL("https://filesamples.com/samples/code/json/sample1.json");
                    downloadTextTask.execute(url1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }





            }
        });

        return  rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nom", editTextNom.getText().toString());
        outState.putString("prenom", editTextPrenom.getText().toString());
        outState.putString("dateNaissance", editTextDateNaissance.getText().toString());
        outState.putString("numeroTel", editTextNumeroTelephone.getText().toString());
        outState.putString("adresseMail", editTextAdresseMail.getText().toString());
        outState.putBoolean("sport", checkBoxSport.isChecked());
        outState.putBoolean("musique", checkBoxMusique.isChecked());
        outState.putBoolean("lecture", checkBoxLecture.isChecked());
    }
    

}

