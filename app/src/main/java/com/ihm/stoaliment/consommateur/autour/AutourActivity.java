
package com.ihm.stoaliment.consommateur.autour;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.BaseConsommateurActivity;
import com.ihm.stoaliment.controleur.GeolocalisationControleur;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.model.Producteur;

public class AutourActivity extends BaseConsommateurActivity implements Observer {
    private MapView map;
    private double lat;
    private double lng;
    IMapController mapController;
    ItemizedOverlayWithFocus<OverlayItem> mMyLocationOverlay;

    private ProducteurControleur producteurControleur;
    private GeolocalisationControleur geolocalisationControleur;
    private OverlayItem curPosition;
    List<OverlayItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<OverlayItem>();

        //load/initialize the osmdroid configuration, this can be done
        Configuration.getInstance().load(   getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()) );
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        //setContentView(R.layout.activity_map);


        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);   //render

        mapController = map.getController();
        mapController.setZoom(18.0);
        /**
         * Zoomable
         */
        map.setBuiltInZoomControls(true);

        /**
         * permet de zommer avec 2 doigt
         */
        map.setMultiTouchControls(true);
        producteurControleur = new ProducteurControleur(this);
        producteurControleur.addObserver(this);
        producteurControleur.loadProducteurs();

        geolocalisationControleur = new GeolocalisationControleur(this);
        geolocalisationControleur.addObserver(this);
        geolocalisationControleur.loadPosition();


        OverlayItem home = new OverlayItem("Salade / Tomate / Oignon", "Siège social", new GeoPoint(43.132988,5.993595));
        Drawable m = home.getMarker(2);

        items.add(home); // Lat/Lon decimal degrees
        items.add(new OverlayItem("Jean-Luc l'agriculteur", "bah chez Jean-luc", new GeoPoint(43.131459,5.994371))); // Lat/Lon decimal degrees

        //the Place icons on the map with a click listener
        mMyLocationOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this, items, producteurControleur);


        mMyLocationOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mMyLocationOverlay);



    }

    @Override
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public int getBottomNavigationMenuItemId() {
        return R.id.action_autour;
    }


    private Drawable resize(Resources r, Drawable image, int newSize) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, newSize, newSize, false);
        BitmapDrawable drawableBmp = new BitmapDrawable(r, bitmapResized);
        return drawableBmp;
    }
    @Override
    public void update(Observable o , Object arg){
        if(arg instanceof Location){
            Location result  = (Location) arg;
            lng = result.getLongitude();
            lat = result.getLatitude();
            GeoPoint startPoint = new GeoPoint(lat, lng);
            mapController.setCenter(startPoint);
            if(curPosition == null){
                curPosition = new OverlayItem("Vous etes ici ", "votre position", startPoint);
                items.add(curPosition);
            }
            else{
                items.set(items.indexOf(curPosition), new OverlayItem("Vous etes ici ", "votre position", startPoint) );
            }

        } else if( o instanceof ProducteurControleur){

            if(arg != null && mMyLocationOverlay != null){
                if(arg instanceof Producteur){
                    Producteur producteur = (Producteur) arg;
                    mMyLocationOverlay.addItem(new OverlayItem(producteur.getId(), producteur.getNom(), producteur.getVille(),new GeoPoint(producteur.getLocation().getLatitude(), producteur.getLocation().getLongitude())));
                }else{
                    List<Producteur> producteurs = (List<Producteur>) arg;
                    for(Producteur producteur : producteurs){
                        mMyLocationOverlay.addItem(new OverlayItem(producteur.getId(), producteur.getNom(), producteur.getVille(),new GeoPoint(producteur.getLocation().getLatitude(), producteur.getLocation().getLongitude())));
                        System.out.println(producteur.getNom());
                        System.out.println(producteur.getLocation());
                    }
                }
            }

        }
    }
}
