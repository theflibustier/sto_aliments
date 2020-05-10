package com.ihm.stoaliment.consommateur.accueil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.model.Producteur;

import java.util.List;

public class AbonnementListAdapter extends ArrayAdapter {

    private final Activity context;

    private final List<Producteur> producteurs;

    public AbonnementListAdapter(Activity context, List<Producteur> producteurs){

        super(context, R.layout.listview_row_abonne, producteurs);

        this.context=context;
        this.producteurs = producteurs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row_abonne, null,true);

        TextView infosAbonne =  rowView.findViewById(R.id.RowInfosAbonne);
        //ImageView image = rowView.findViewById(R.id.RowImageAbonne);

        //this code sets the values of the objects to values from the arrays
        Producteur currentConsommateur = producteurs.get(position);
        infosAbonne.setText(currentConsommateur.getNom());
        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return producteurs.get(position);
    }

}