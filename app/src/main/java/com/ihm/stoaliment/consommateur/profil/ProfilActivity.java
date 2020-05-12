package com.ihm.stoaliment.consommateur.profil;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.authentification.AuthentificationActivity;
import com.ihm.stoaliment.consommateur.BaseConsommateurActivity;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.model.Authentification;

public class ProfilActivity extends BaseConsommateurActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button retour = (Button) findViewById(R.id.retourALaPagePrecedente);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this, AccueilConsommateurActivity.class);
                startActivity(intent);
            }
        });

        TextView name = (TextView) findViewById(R.id.nom);
        name.setText(Authentification.consommateur.getNom());

        TextView firstname = (TextView) findViewById(R.id.prenom);
        firstname.setText(Authentification.consommateur.getNom());

        TextView adresse = (TextView) findViewById(R.id.adresse);
        adresse.setText(Authentification.consommateur.getAdresse() + " " + Authentification.consommateur.getVille() + " " + Authentification.consommateur.getCp());

        TextView tel = (TextView) findViewById(R.id.telephone);
        tel.setText("06 01 02 03 04");

        TextView mail = (TextView) findViewById(R.id.email);
        mail.setText(Authentification.consommateur.getNom() + "@gmail.com");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profil;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.action_user;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_deconnexion) {

            SharedPreferences mPrefs = Authentification.preferences;
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.remove(Authentification.GSON_LABEL);
            prefsEditor.apply();

            Intent intent = new Intent(this, AuthentificationActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
