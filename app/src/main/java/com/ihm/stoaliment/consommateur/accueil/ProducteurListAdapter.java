package com.ihm.stoaliment.consommateur.accueil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.DoubleValue;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.GeolocalisationControleur;
import com.ihm.stoaliment.model.Producteur;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ProducteurListAdapter extends ArrayAdapter implements Observer {

    //to reference the Activity
    private final Activity context;
    private final List<Producteur> producteurs;
    GeolocalisationControleur geolocalisationControleur;
    private Location curPosition;

    public ProducteurListAdapter(Activity context, List<Producteur> producteurs){

        super(context, R.layout.listview_row_producteur, producteurs);

        this.context=context;
        this.producteurs = producteurs;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_producteur, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView tvProducteur = (TextView) rowView.findViewById(R.id.textViewProducteur);
        TextView tvProducteurDistance = (TextView) rowView.findViewById(R.id.textViewProducteurDistance);
        final ImageView image = (ImageView) rowView.findViewById(R.id.imageViewImageProducteur);

        tvProducteur.setText(producteurs.get(position).getNom() + " - "+producteurs.get(position).getCp() + " - "+ producteurs.get(position).getVille());

        GeolocalisationControleur geolocalisationControleur = new GeolocalisationControleur(context);
        geolocalisationControleur.addObserver(this);
        geolocalisationControleur.loadPosition();
        Double distance = (FilterActivity.distance(curPosition.getLatitude(), producteurs.get(position).getLocation().getLatitude(), curPosition.getLongitude(), producteurs.get(position).getLocation().getLongitude(), 0, 0) * 0.001);
        String distanceKm = Double.toString(Math.floor(distance * 100)/100);
        tvProducteurDistance.setText("A " + distanceKm + " km de chez vous !");

        image.setImageBitmap(producteurs.get(position).getImage());
        return rowView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return producteurs.get(position);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof Location) {
            curPosition = (Location) o;
        }
    }
}
