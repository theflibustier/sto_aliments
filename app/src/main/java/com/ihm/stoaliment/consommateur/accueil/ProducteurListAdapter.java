package com.ihm.stoaliment.consommateur.accueil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.model.Producteur;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProducteurListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;
    private final List<Producteur> producteurs;

    public ProducteurListAdapter(Activity context, List<Producteur> producteurs){

        super(context, R.layout.listview_row_producteur, producteurs);

        this.context=context;
        this.producteurs = producteurs;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_producteur, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView tvProducteur = (TextView) rowView.findViewById(R.id.textViewProducteur);
        final ImageView image = (ImageView) rowView.findViewById(R.id.imageViewImageProducteur);

        tvProducteur.setText(producteurs.get(position).getNom());

        image.setImageBitmap(producteurs.get(position).getImage());

        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return producteurs.get(position);
    }
}
