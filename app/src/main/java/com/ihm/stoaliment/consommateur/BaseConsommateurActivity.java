package com.ihm.stoaliment.consommateur;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ihm.stoaliment.R;
import com.ihm.stoaliment.consommateur.accueil.AccueilConsommateurActivity;
import com.ihm.stoaliment.consommateur.autour.AutourActivity;

public abstract class BaseConsommateurActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    private static Class currentActivity = AccueilConsommateurActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public  void updateNavigationBarState() {
        int actionId = getBottomNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

        navigationView.postDelayed(new Runnable() {
            @Override
            public void run() {

                int id = menuItem.getItemId();
                Class activity = null;

                if (id == R.id.action_home)
                    activity = AccueilConsommateurActivity.class;

                // Autour de moi
                else if (id == R.id.action_autour)
                    activity = AutourActivity.class;

                // Favoris
                else if (id == R.id.action_fav)
                    // TODO
                    ;

                // Profil
                else if (id == R.id.action_user)
                    // TODO
                    ;

//                if (activity != null &&  (currentActivity == null ||  !currentActivity.getSimpleName().equals(activity.getSimpleName())) ) {
                if (activity != null) {
                    Intent intent = new Intent(BaseConsommateurActivity.this.getBaseContext(), activity);
                    BaseConsommateurActivity.this.startActivity(intent);
                    currentActivity = activity;
                }
            }
        }, 300);

        return true;
    }

    void selectBottomNavigationBarItem(int itemId) {

        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    public abstract int getLayoutId();
    public abstract int getBottomNavigationMenuItemId();

}
