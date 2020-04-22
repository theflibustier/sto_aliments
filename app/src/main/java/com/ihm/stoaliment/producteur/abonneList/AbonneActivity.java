package com.ihm.stoaliment.producteur.abonneList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Abonne;

public class AbonneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonne);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Abonne abonne = (Abonne) bundle.getSerializable("ABONNE");

        ((TextView)findViewById(R.id.nomAbonne)).setText(abonne.getName());

        ((TextView)findViewById(R.id.codePostalAbonne)).setText(abonne.getCodePostal());

        ((TextView)findViewById(R.id.villeAbonne)).setText(abonne.getName());

        ((ImageView)findViewById(R.id.imgAbonne)).setImageResource(abonne.getImg());
    }
}
