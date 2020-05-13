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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.MapView;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;

import java.util.Map;
import java.util.Observable;


public class GeolocalisationControleur extends Observable implements LocationListener {
    private Activity activity;
    private AlertDialog dialogGPS;
    private LocationManager locationManager;

    public GeolocalisationControleur(final Activity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            askGps();
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                return;
            }
        } else {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                return;
            }
        }
    }

    public void loadPosition() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            askGps();
        } else {
            boolean permissionGranted = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (permissionGranted) {
                Location l = locationManager.getLastKnownLocation("gps");
                if (l != null) {
                    System.out.println("///////////////////////" + l.getLongitude());
                    System.out.println("///////////////////////" + l.getLatitude());
                    setChanged();
                    notifyObservers(l);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 100, this);
            } else {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    return;
                }
            }
        }
    }

    public void askGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Le GPS est désactiver :(");
        builder.setMessage("L'application a besoin du GPS pour vous localiser \n \n" +
                "Ps : La vérification peut prendre du temps, appuyez plusieurs fois si nécessaire");
        builder.setPositiveButton("Activer le GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(i);
            }
        }).setNegativeButton("Rafraichir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        return;
                }
                loadPosition();
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
        setChanged();
        notifyObservers(true);
    }

    @Override
    public void onProviderDisabled(String s) {
        setChanged();
        notifyObservers(false);
        askGps();
    }
}