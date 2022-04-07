package com.krak.schedule_app.main_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
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
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    // Чтобы даже если пользователь ввел данные и сразу закрыл приложение,
    // мы сохранили их. С заботой о UserExperience
    public static Saveable saveable;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static final String GOTO_FRAGMENT = "GOTO_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNav();
        addObservers();
        checkName();
        loadData();
        checkIntents();
    }

    private void checkIntents() {
        if (getIntent().hasExtra(GOTO_FRAGMENT)){
            binding.navView.setCheckedItem(getIntent().getIntExtra(GOTO_FRAGMENT, R.id.nav_schedule));
        }
    }


    private void addObservers(){
        // Если меняется имя, то мы меняем его в заголовке NavigationView
        NameHolder nameHolder = new ViewModelProvider(this).get(NameHolder.class);
        nameHolder.getData().observe(this, s -> {
            TextView name = binding.navView.getHeaderView(0).findViewById(R.id.navName);
            name.setText(s + ", ");
        });
    }

    // Инициализируем NavigationView
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

    // Если имени нет в преференсах, то посылаем пользователя
    // в StartActivity, где спрашиваем имя у него
    public void checkName(){
        SharedPreferences preferences = this.getSharedPreferences(Config.PREFERENCES, MODE_PRIVATE);
        if (preferences.contains(Config.NAME_KEY)){
            NameHolder nameHolder = new ViewModelProvider(this).get(NameHolder.class);
            nameHolder.changeName(preferences.getString(Config.NAME_KEY, ""));
        } else {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        }
    }

    // Нужно для NavigationView
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Загружаем данные из бд в наши модели - ListHolder-ы
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

    // Синхронизируем бд и ListHolder-ы на всякий случай.
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
