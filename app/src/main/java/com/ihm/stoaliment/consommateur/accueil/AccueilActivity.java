package com.ihm.stoaliment.consommateur.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.produit.ProduitActivity;
import com.ihm.stoaliment.model.Produit;

import java.util.List;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        AccueilControlleur accueilControlleur = new AccueilControlleur();
        List<Produit> produits = accueilControlleur.chargeProduit();

        System.out.println(produits);

        ProduitListAdapter produitListAdapter = new ProduitListAdapter(this, produits);
        ListView listView = findViewById(R.id.listViewProduit);
        listView.setAdapter(produitListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Produit produit = (Produit) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ProduitActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUIT", produit);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
