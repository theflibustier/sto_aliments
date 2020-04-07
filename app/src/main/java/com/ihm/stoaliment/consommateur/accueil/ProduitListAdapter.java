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
import com.ihm.stoaliment.model.Producteur;
import com.ihm.stoaliment.model.Produit;

import java.util.List;

public class ProduitListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final List<Produit> produits;

    public ProduitListAdapter(Activity context, List<Produit> produits){

        super(context, R.layout.listview_row_produit, produits);

        this.context=context;
        this.produits = produits;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_produit, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView tvProducteur = (TextView) rowView.findViewById(R.id.textViewProducteur);
        TextView tvLabel = (TextView) rowView.findViewById(R.id.textViewLabel);
        ImageView image = (ImageView) rowView.findViewById(R.id.imageViewImage);

        //this code sets the values of the objects to values from the arrays
        Producteur currentProducteur = produits.get(position).getProducteur();
        tvProducteur.setText(currentProducteur.getName() + " - " + currentProducteur.codePostal + " - " + currentProducteur.getVille());
        tvLabel.setText(produits.get(position).getLabel());
        image.setImageResource(produits.get(position).getImage());

        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return produits.get(position);
    }


}
