package com.ihm.stoaliment.controleur;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;

import java.util.Observable;


public class GeolocalisationControleur extends Observable implements LocationListener {
    private Activity activity;
    private AlertDialog dialogGPS;
    private LocationManager locationManager;

    public GeolocalisationControleur(final Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if(!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){
            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

    }

    public void loadPosition(){

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        Location l = locationManager.getLastKnownLocation("gps");
        if(l != null){
            System.out.println("///////////////////////"+l.getLongitude());
            System.out.println("///////////////////////"+l.getLatitude());
            setChanged();
            notifyObservers(l);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 100,this);

    }

    public void askGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Le GPS est désactiver :(");
        builder.setMessage("L'application a besoin du GPS pour vous localiser");
        builder.setPositiveButton("Activer le GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("Non merci", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "La géolocalisation est obligatoire pour le bon fonctionnement de l'application", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, AccueilConsommateurActivity.class);
                activity.startActivity(intent);
            }
        });
        dialogGPS = builder.create();
        dialogGPS.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        setChanged();
        notifyObservers(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        loadPosition();
    }

    @Override
    public void onProviderDisabled(String s) {
        askGps();
    }
}