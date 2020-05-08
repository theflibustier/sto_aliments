package com.ihm.stoaliment.consommateur.accueil;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.ihm.stoaliment.R;


public class FilterActivity extends AppCompatActivity {
    private SeekBar simpleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_filtrer);

        // recupère l'element seekbar
        simpleSeekBar = (SeekBar)findViewById(R.id.distanceSeekBar);

        // avoir la value on set un listener dessus
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //initialise à 0
            int progressChangedValue = 0;

            //au changement on recupère la nouvelle valeur
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            //toast : montre la valeur
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(FilterActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
