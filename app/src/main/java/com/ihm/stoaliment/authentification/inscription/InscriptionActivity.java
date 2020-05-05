package com.ihm.stoaliment.authentification.inscription;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.InscriptionControlleur;

import java.util.Observable;
import java.util.Observer;

public class InscriptionActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        InscriptionControlleur inscriptionControlleur = new InscriptionControlleur(this);
        inscriptionControlleur.addObserver(this);

        findViewById(R.id.btn_valider_inscription).setOnClickListener(inscriptionControlleur);
        findViewById(R.id.button_coordonne).setOnClickListener(inscriptionControlleur);
        ( (CheckBox) findViewById(R.id.check_box_producteur)).setOnCheckedChangeListener(inscriptionControlleur);
    }

    @Override
    public void update(Observable o, Object arg) {

        String res = (String) arg;
        if(res.isEmpty()){

            finish();
        }
        else{

            Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        }
    }
}
