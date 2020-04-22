package com.ihm.stoaliment.consommateur.accueil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ihm.stoaliment.consommateur.produit.ProduitActivity;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.ProducteurModel;

public class AccueilControlleur implements AdapterView.OnItemClickListener {


    private Activity activity;

    public AccueilControlleur(Activity activity, ProducteurModel producteurModel){

        this.activity = activity;
        producteurModel.loadProducteur();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Producteur producteur = (Producteur) parent.getItemAtPosition(position);

        Intent intent = new Intent(activity.getApplicationContext(), ProduitActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("PRODUIT", producteur);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
