package com.krak.schedule_app.main_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.krak.schedule_app.R;
import com.krak.schedule_app.config.Config;
import com.krak.schedule_app.databinding.ActivityMainBinding;
import com.krak.schedule_app.livedata.BreaksHolder;
import com.krak.schedule_app.livedata.DaysHolder;
import com.krak.schedule_app.livedata.ListHolder;
import com.krak.schedule_app.livedata.NameHolder;
import com.krak.schedule_app.livedata.NotesHolder;
import com.krak.schedule_app.start.StartActivity;
import com.krak.schedule_app.utils.Saveable;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Saveable saveable;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNav();
        addObservers();
        checkName();
        loadData();
    }

    private void addObservers(){
        NameHolder nameHolder = new ViewModelProvider(this).get(NameHolder.class);

        nameHolder.getData().observe(this, s -> {
            TextView name = binding.navView.getHeaderView(0).findViewById(R.id.navName);
            name.setText(s + ", ");
        });
    }

    private void initNav(){
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_schedule, R.id.nav_breaks, R.id.nav_notes, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void checkName(){
        SharedPreferences preferences = this.getSharedPreferences(Config.PREFERENCES, MODE_PRIVATE);
        if (preferences.contains(Config.NAME_KEY)){
            NameHolder nameHolder = new ViewModelProvider(this).get(NameHolder.class);
            nameHolder.changeName(preferences.getString(Config.NAME_KEY, ""));
        } else {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadData(){
        ViewModelProvider provider = new ViewModelProvider(this);
        ListHolder[] holders = {
                provider.get(BreaksHolder.class),
                provider.get(NotesHolder.class),
                provider.get(DaysHolder.class)
        };
        for (ListHolder holder : holders){
            List list = (List)holder.getData().getValue();
            if (list == null || list.isEmpty()){
                holder.loadData();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (saveable != null) {
            saveable.save();
        }
        ViewModelProvider provider = new ViewModelProvider(this);
        ListHolder[] holders = {
                provider.get(BreaksHolder.class),
                provider.get(NotesHolder.class),
                provider.get(DaysHolder.class)
        };
        for (ListHolder holder : holders){
            holder.uploadData();
        }
    }
}
