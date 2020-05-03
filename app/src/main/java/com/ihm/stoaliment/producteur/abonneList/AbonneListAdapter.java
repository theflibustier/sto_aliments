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

    private final Activity context;

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

        TextView infosAbonne =  rowView.findViewById(R.id.RowInfosAbonne);
        //ImageView image = rowView.findViewById(R.id.RowImageAbonne);

        //this code sets the values of the objects to values from the arrays
        Consommateur currentConsommateur = consommateurs.get(position);
        infosAbonne.setText(currentConsommateur.getNom());
        //image.setImageResource(consommateurs.get(position).getImg());
        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return consommateurs.get(position);
    }

}
