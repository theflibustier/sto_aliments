package com.ihm.stoaliment.consommateur.accueil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.produit.ProduitActivity;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;
import com.ihm.stoaliment.model.ProduitList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AccueilControlleur implements AdapterView.OnItemClickListener {

    private Activity activity;

    public AccueilControlleur(Activity activity, ProduitList produitList){

        this.activity = activity;
        produitList.loadProduit();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Produit produit = (Produit) parent.getItemAtPosition(position);

        Intent intent = new Intent(activity.getApplicationContext(), ProduitActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUIT", produit);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
