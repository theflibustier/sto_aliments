package com.ihm.stoaliment.controleur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ihm.stoaliment.consommateur.producteur.DetailProducteurActivity;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Producteur;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import static com.google.firebase.firestore.FieldValue.arrayUnion;

public class ProducteurControleur extends Observable implements AdapterView.OnItemClickListener, ItemizedIconOverlay.OnItemGestureListener<OverlayItem>, OnSuccessListener<Location> {


    private Activity activity;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "DATABASE";

    private int nb_producteur_load;
    private int nb_producteur_to_load;

    public ProducteurControleur(Activity activity){

        this.activity = activity;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Producteur producteur = (Producteur) parent.getItemAtPosition(position);

        Intent intent = new Intent(activity.getApplicationContext(), DetailProducteurActivity.class);
        intent.putExtra("PRODUCTEUR", producteur.getId());
        activity.startActivity(intent);
    }

    public void loadProducteurs(){

        db.collection("producteur").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG,document.getId() + " => " + document.getData());

                        final Producteur producteur = document.toObject(Producteur.class);
                        producteur.setId(document.getId());

                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference islandRef = firebaseStorage.getReference().child(producteur.getImageUrl());

                        File localFile = null;
                        try {
                            localFile = File.createTempFile("images", "jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final File finalLocalFile = localFile;
                        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());

                                producteur.setImage(bitmap);
                                setChanged();
                                notifyObservers(producteur);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }


    public void loadProducteursSortedByNearest(final GeoPoint geoPoint){

        final List<Producteur> producteurs = new ArrayList<>();

        db.collection("producteur").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG,document.getId() + " => " + document.getData());

                        final Producteur producteur = document.toObject(Producteur.class);
                        producteur.setId(document.getId());
                        producteurs.add(producteur);
                    }

                    nb_producteur_to_load = producteurs.size();
                    nb_producteur_load = 0;

                    for(final Producteur producteur : producteurs){

                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference islandRef = firebaseStorage.getReference().child(producteur.getImageUrl());

                        File localFile = null;
                        try {
                            localFile = File.createTempFile("images", "jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final File finalLocalFile = localFile;
                        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());

                                producteur.setImage(bitmap);

                                nb_producteur_load++;
                                if(nb_producteur_load == nb_producteur_to_load){

                                    producteurs.sort(new Comparator<Producteur>() {
                                        @Override
                                        public int compare(Producteur o1, Producteur o2) {

                                            double disO1 = Math.abs(o1.getLocation().getLatitude() - geoPoint.getLatitude()) + Math.abs(o1.getLocation().getLongitude() - geoPoint.getLongitude());
                                            double disO2 = Math.abs(o2.getLocation().getLatitude() - geoPoint.getLatitude()) + Math.abs(o2.getLocation().getLongitude() - geoPoint.getLongitude());

                                            if(disO1 < disO2) return -1;
                                            else if (disO1 > disO2) return 1;
                                            return 0;
                                        }
                                    });

                                    setChanged();
                                    notifyObservers(producteurs);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }




    public void loadProducteur(String id) {

        db.collection("producteur").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        final Producteur producteur = document.toObject(Producteur.class);
                        producteur.setId(document.getId());

                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference islandRef = firebaseStorage.getReference().child(producteur.getImageUrl());

                        File localFile = null;
                        try {
                            localFile = File.createTempFile("images", "jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        final File finalLocalFile = localFile;
                        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());

                                producteur.setImage(bitmap);
                                setChanged();
                                notifyObservers(producteur);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "No such document");
                }

            }
        });
    }

    @Override
    public boolean onItemSingleTapUp(int index, OverlayItem item) {

        System.out.println(item.getUid());
        return false;
    }

    @Override
    public boolean onItemLongPress(int index, OverlayItem item) {
        return false;
    }

    @Override
    public void onSuccess(Location location) {
        loadProducteursSortedByNearest(new GeoPoint(location.getLatitude(), location.getLongitude()));
    }
}
