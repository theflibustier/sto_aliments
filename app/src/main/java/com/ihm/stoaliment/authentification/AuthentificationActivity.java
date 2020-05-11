package com.ihm.stoaliment.authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.authentification.inscription.InscriptionActivity;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.controleur.AuthentificationControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.producteur.accueil.AccueilProducteurActivity;

import java.util.Observable;
import java.util.Observer;

public class AuthentificationActivity extends AppCompatActivity implements Observer{

    private AuthentificationControleur authentificationControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        authentificationControleur = new AuthentificationControleur(this);
        authentificationControleur.addObserver(this);

        findViewById(R.id.btn_valider).setOnClickListener(authentificationControleur);

        TextInputEditText et = (TextInputEditText) findViewById(R.id.edit_text_identifiant);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        et.requestFocus();
        et.setOnKeyListener(authentificationControleur);



    }


    @Override
    public void update(Observable o, Object arg) {

        if(arg == null){

            TextInputLayout til = (TextInputLayout) findViewById(R.id.layout_id);
            til.setError("Identifiant incorrect...");
        }
        else{

            if(((CheckBox) findViewById(R.id.check_box_souvenir)).isChecked()){

                authentificationControleur.saveAuthentification();
            }

            System.out.println(Authentification.userType);
            if (Authentification.userType.equals(Authentification.CONSOMMATEUR_TYPE)) {
                System.out.println(Authentification.consommateur.getNom());
                Intent intent = new Intent(this, AccueilConsommateurActivity.class);
                startActivity(intent);
                finish();
            } else {
                System.out.println(Authentification.producteur.getNom());
                Intent intent = new Intent(this, AccueilProducteurActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authentification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_inscription) {

            Intent intent = new Intent(this, InscriptionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
