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
import com.ihm.stoaliment.model.Produit;

import java.util.List;

public class ProduitListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;
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
        TextView tvProduit = (TextView) rowView.findViewById(R.id.textViewProduit);
        final ImageView image = (ImageView) rowView.findViewById(R.id.imageViewImageProduit);
        TextView tvPrix = (TextView) rowView.findViewById(R.id.prixProduitRow);
        TextView tvQuantite = (TextView) rowView.findViewById(R.id.quantiteProduitRow);

        String prix = String.valueOf(produits.get(position).getPrix());
        String quantite = String.valueOf(produits.get(position).getQuantite());

        tvProduit.setText(produits.get(position).getLabel());
        tvPrix.setText(prix);
        tvQuantite.setText(quantite);
        image.setImageBitmap(produits.get(position).getImage());

        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return produits.get(position);
    }
}
