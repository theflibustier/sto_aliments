package com.ihm.stoaliment.producteur.accueil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Producteur;

import java.util.Observable;
import java.util.Observer;

public class HomeFragment extends Fragment implements Observer {

    private Producteur producteur;
    private View root;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ProducteurControleur producteurControleur = new ProducteurControleur(getActivity());
        producteurControleur.addObserver(this);
        producteurControleur.loadProducteur(Authentification.authentification.getRef());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);

        if(producteur != null){

            root.findViewById(R.id.layout_load).setVisibility(View.GONE);
            root.findViewById(R.id.layout_home).setVisibility(View.VISIBLE);
            ( (ImageView) root.findViewById(R.id.imageViewImageProfilProducteur)).setImageBitmap(producteur.getImage());
            ( (TextView) root.findViewById(R.id.textViewNomProfilProducteur)).setText(producteur.getNom());
        }
        return root;
    }

    @Override
    public void update(Observable o, Object arg) {

        producteur = (Producteur) arg;
        ( (ImageView) getActivity().findViewById(R.id.imageViewImageProfilProducteur)).setImageBitmap(producteur.getImage());
        ( (TextView) getActivity().findViewById(R.id.textViewNomProfilProducteur)).setText(producteur.getNom());
        root.findViewById(R.id.layout_load).setVisibility(View.GONE);
        root.findViewById(R.id.layout_home).setVisibility(View.VISIBLE);
    }
}
