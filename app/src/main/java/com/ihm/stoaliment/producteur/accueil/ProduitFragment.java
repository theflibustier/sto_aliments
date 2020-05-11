package com.ihm.stoaliment.producteur.accueil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.ProduitListAdapter;
import com.ihm.stoaliment.controleur.ProducteurControleur;
import com.ihm.stoaliment.controleur.ProduitControleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Produit;
import com.ihm.stoaliment.producteur.produit.AjoutProduitActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ProduitFragment extends Fragment implements Observer {

    private List<Produit> produits;
    private ProduitListAdapter produitListAdapter;
    private View root;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ProduitControleur produitControleur = new ProduitControleur(getActivity());
        produitControleur.addObserver(this);
        produitControleur.loadProduits(String.valueOf(Authentification.authentification.getRef()));

        produits = new ArrayList<>();
        produitListAdapter = new ProduitListAdapter(getActivity(), produits);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_produit, container, false);

        if(!produits.isEmpty()){

            root.findViewById(R.id.layout_load).setVisibility(View.GONE);
            root.findViewById(R.id.layout_produit).setVisibility(View.VISIBLE);

            produitListAdapter = new ProduitListAdapter(getActivity(), produits);
        }

        ListView listView = root.findViewById(R.id.listViewProduitProducteur);
        listView.setAdapter(produitListAdapter);

        root.findViewById(R.id.floatingActionButton_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AjoutProduitActivity.class);
                getContext().startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void update(Observable o, Object arg) {

        Produit produit = (Produit) arg;
        produits.add(produit);
        produitListAdapter.notifyDataSetChanged();
        root.findViewById(R.id.layout_load).setVisibility(View.GONE);
        root.findViewById(R.id.layout_produit).setVisibility(View.VISIBLE);
    }
}
