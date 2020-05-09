package com.ihm.stoaliment.producteur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Authentification;

public class CoordonneesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordonnees2);
        ((EditText) findViewById(R.id.edit_text_identifiant_modif)).setText("*******");
        ((TextInputEditText) findViewById(R.id.edit_text_nom_modif)).setText(Authentification.producteur.getNom());
        ((TextInputEditText) findViewById(R.id.edit_text_adresse_modif)).setText(Authentification.producteur.getAdresse());
        ((TextInputEditText) findViewById(R.id.edit_text_ville_modif)).setText(Authentification.producteur.getVille());
        ((TextInputEditText) findViewById(R.id.edit_text_cp_modif)).setText(Authentification.producteur.getCp());
    }
}
