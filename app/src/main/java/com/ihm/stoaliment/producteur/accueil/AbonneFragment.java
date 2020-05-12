package com.ihm.stoaliment.producteur.accueil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.ProduitListAdapter;
import com.ihm.stoaliment.controleur.ConsommateurControlleur;
import com.ihm.stoaliment.model.Authentification;
import com.ihm.stoaliment.model.Consommateur;
import com.ihm.stoaliment.producteur.abonneList.AbonneListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AbonneFragment extends Fragment implements Observer {

    private ConsommateurControlleur consommateurControlleur;
    private List<Consommateur> consommateurs = new ArrayList<>();
    private AbonneListAdapter abonnesListAdapter;
    private View root;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        consommateurControlleur = new ConsommateurControlleur(getActivity());
        consommateurControlleur.addObserver(this);
        consommateurControlleur.loadAbonnes(Authentification.producteur.getId());

        consommateurs = new ArrayList<>();
        abonnesListAdapter = new AbonneListAdapter(getActivity(), consommateurs);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_abonne, container, false);

        if(!consommateurs.isEmpty()){




            abonnesListAdapter = new AbonneListAdapter(getActivity(), consommateurs);
        }

        if(Authentification.producteur.getListeAbonnes().isEmpty()){

            root.findViewById(R.id.layout_load).setVisibility(View.GONE);
            root.findViewById(R.id.layout_abonnees).setVisibility(View.VISIBLE);
        }


            ListView listView = root.findViewById(R.id.listViewAbonnes);
        listView.setAdapter(abonnesListAdapter);
        listView.setOnItemClickListener(consommateurControlleur);
        return root;
    }

    @Override
    public void update(Observable o, Object arg) {

        consommateurs = (List<Consommateur>) arg;
        abonnesListAdapter.addAll(consommateurs);
        abonnesListAdapter.notifyDataSetChanged();
        root.findViewById(R.id.layout_load).setVisibility(View.GONE);
        root.findViewById(R.id.layout_abonnees).setVisibility(View.VISIBLE);
    }
}
