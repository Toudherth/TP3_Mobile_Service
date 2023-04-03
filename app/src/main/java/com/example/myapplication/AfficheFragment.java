package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AfficheFragment extends Fragment {



    private TextView nom1 ;
    private TextView prenom1;
    private TextView dateNaissance1;
    private TextView numerotel1;
    private TextView mail1;
    private TextView centre1;
    private Button buttonaffichefichier, buttonafficheannuler;

    @Override
    public void onResume() {
        super.onResume();

        // Démarrer le service ici
        Intent intent = new Intent(getActivity(), HelloService.class);
        getActivity().startService(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_affiche, container, false);

        nom1 = view.findViewById(R.id.nom);
        prenom1 = view.findViewById(R.id.prenom);
        dateNaissance1 = view.findViewById(R.id.date);
        numerotel1 = view.findViewById(R.id.tel);
        mail1 = view.findViewById(R.id.mail);
        centre1 = view.findViewById(R.id.interet);
        buttonaffichefichier= view.findViewById(R.id.buttonaffichefichier);
        buttonafficheannuler= view.findViewById(R.id.buttonafficheretour);

        // Récupérer le Bundle passé du Fragment 1
        Bundle dataBundle = getArguments();

        if (dataBundle != null) {
            String nom = dataBundle.getString("nom");
            String prenom = dataBundle.getString("prenom");
            String dateNaissance = dataBundle.getString("dateNaissance");
            String numeroTel = dataBundle.getString("numeroTel");
            String adresseMail = dataBundle.getString("adresseMail");
            boolean sport = dataBundle.getBoolean("sport");
            boolean musique = dataBundle.getBoolean("musique");
            boolean lecture = dataBundle.getBoolean("lecture");

            // Remplir les vues avec les données récupérées du Bundle
            nom1.setText("Nom : "+nom);
            prenom1.setText("Prenom : "+prenom);
            dateNaissance1.setText("Date naissance :"+dateNaissance);
            numerotel1.setText("Numero tel : "+numeroTel);
            mail1.setText("Email : "+adresseMail);
            String centre ="";
            if(sport){ centre+="Sport, "; }
            if(musique){ centre+="Musique, "; }
            if(lecture){ centre+="Lecture, "; }
            if(centre.length() > 0){
                centre= centre.substring(0, centre.length() - 2);
            }
            centre1.setText("Centre d'interet : "+centre);
        }
        buttonaffichefichier.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {


                String nom= nom1.getText().toString();
                String prenom= prenom1.getText().toString();
                String date= dateNaissance1.getText().toString();
                String tel= numerotel1.getText().toString();
                String mail= mail1.getText().toString();
                String centre= centre1.getText().toString();

                // ici pour stocker le text dans un fichier
                String FileName="formulaire";
                String string=nom+" "+prenom+" "+date+" "+tel+" "+mail+" "+centre;

                try {
                    FileOutputStream fos= getActivity().openFileOutput(FileName, getContext().MODE_PRIVATE);
                    fos.write(string.getBytes());

                    fos.close();
                    // récupérer l'emplacement du fichier créé
                    File file = getActivity().getFileStreamPath(FileName);
                    System.out.println("---------- file le chemin :   "+file);

                    // lire le contenu du fichier
                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));

                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line);
                    }

                    Log.d("TAG", "Contenu du fichier : " + content.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity().getApplicationContext(), "Le fichier est stocké", Toast.LENGTH_SHORT).show();

            }
        });

        //Button retour
        buttonafficheannuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a supprimera le fragment Affichage de la pile arrière et restaurera le fragment Saisie.
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return  view;

    }


}