package com.ihm.stoaliment.producteur.abonneList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.AbonneList;

public class AbonneControlleur implements AdapterView.OnItemClickListener {

    private Activity activity;

    public AbonneControlleur(Activity activity, AbonneList abonneList){

        this.activity = activity;
        abonneList.loadAbonnes();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Consommateur consommateur = (Consommateur) parent.getItemAtPosition(position);

        Intent intent = new Intent(activity.getApplicationContext(), AbonneActivity.class);

        System.out.println(intent);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ABONNE", consommateur);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}