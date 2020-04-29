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
import com.ihm.stoaliment.model.Consommateur;

import java.util.List;

public class AbonneListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final List<Consommateur> consommateurs;

    public AbonneListAdapter(Activity context, List<Consommateur> consommateurs){

        super(context, R.layout.listview_row_abonne, consommateurs);

        this.context=context;
        this.consommateurs = consommateurs;
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
        Consommateur currentConsommateur = consommateurs.get(position);
        //tvInfosAbonne.setText(currentConsommateur.getNom() + " - " + currentConsommateur.getCodePostal() + " - " + currentConsommateur.getVille());
        //image.setImageResource(consommateurs.get(position).getImg());

        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return consommateurs.get(position);
    }

}
