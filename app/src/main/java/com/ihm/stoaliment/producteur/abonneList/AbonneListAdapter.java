package com.ihm.stoaliment.producteur.abonneList;

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
import com.ihm.stoaliment.model.Abonne;
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.List;

public class AbonneListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final List<Abonne> abonnes;

    public AbonneListAdapter(Activity context, List<Abonne> abonnes){

        super(context, R.layout.listview_row_abonne, abonnes);

        this.context=context;
        this.abonnes = abonnes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row_abonne, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView tvInfosAbonne =  rowView.findViewById(R.id.RowInfosAbonne);
        ImageView image = rowView.findViewById(R.id.RowImageAbonne);

        //this code sets the values of the objects to values from the arrays
        Abonne currentAbonne = abonnes.get(position);
        tvInfosAbonne.setText(currentAbonne.getName() + " - " + currentAbonne.getCodePostal() + " - " + currentAbonne.getVille());
        image.setImageResource(abonnes.get(position).getImg());

        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return abonnes.get(position);
    }

}
