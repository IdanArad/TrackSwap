package com.example.trackswap;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.trackswap.model.Firestore;
import com.example.trackswap.model.Track;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    NavController navController;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);

        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView,navController);
    }

    int fragmentMenuId = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        if (fragmentMenuId != 0){
            menu.removeItem(fragmentMenuId);
        }
        fragmentMenuId = 0;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
        if (item.getItemId() == android.R.id.home){
            navController.popBackStack();
        } else if(item.getTitle().equals("logout")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Are you sure you want to log out?");

            builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent
                            = new Intent(MainActivity.this,
                            LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();



        } else {
            fragmentMenuId = item.getItemId();
            return NavigationUI.onNavDestinationSelected(item,navController);
        }
        return super.onOptionsItemSelected(item);
    }
}