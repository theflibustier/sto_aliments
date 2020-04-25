
package com.ihm.stoaliment.consommateur.autour;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

//import com.example.map.R;
import com.ihm.stoaliment.R;

public class AutourActivity extends AppCompatActivity {
    private MapView map;
    private double lat;
    private double lng;
    IMapController mapController;
    ItemizedOverlayWithFocus<OverlayItem> mMyLocationOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=new Intent(AutourActivity.this,GeolocalisationActivity.class);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2

        //load/initialize the osmdroid configuration, this can be done
        Configuration.getInstance().load(   getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()) );
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);   //render

        /**
         * Zoomable
         */
        map.setBuiltInZoomControls(true);

        /**
         * permet de zommer avec 2 doigt
         */
        map.setMultiTouchControls(true);


        mapController = map.getController();
        mapController.setZoom(18.0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            lat =data.getExtras().getDouble("latitude");
            lng =data.getExtras().getDouble("longitude");
            System.out.println(lat);
            System.out.println(lng);

            GeoPoint startPoint = new GeoPoint(lat, lng);
            mapController.setCenter(startPoint);

            //create a new item to draw on the map
            //your items
            ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
            OverlayItem home = new OverlayItem("Salade / Tomate / Oignon", "Siège social", new GeoPoint(43.132988,5.993595));
            Drawable m = home.getMarker(0);

            items.add(home); // Lat/Lon decimal degrees
            items.add(new OverlayItem("Jean-Luc l'agriculteur", "bah chez Jean-luc", new GeoPoint(43.131459,5.994371))); // Lat/Lon decimal degrees

            //the Place icons on the map with a click listener
            ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this, items,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        @Override
                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                            //do something
                            return true;
                        }
                        @Override
                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                            return false;
                        }
                    });


            mOverlay.setFocusItemsOnTap(true);
            map.getOverlays().add(mOverlay);
        }
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
}
