package com.krak.schedule_app.start;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.krak.schedule_app.R;
import com.krak.schedule_app.config.Config;
import com.krak.schedule_app.dialogs.MessageDialog;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.databinding.StartActivityBinding;
import com.krak.schedule_app.utils.NameValidator;

public class StartActivity extends AppCompatActivity {

    private StartActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StartActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addListeners();
    }

    private void addListeners(){
        // Нажал "Продолжить"
        binding.startNextBtn.setOnClickListener(view -> {
            String name = binding.startNameInput.getText().toString().trim();
            // Проверяем имя на валидность
            if (NameValidator.isValidUserName(name)) {
                startSchedule(name);
            } else {
                new MessageDialog(getResources().getString(R.string.incorrect_name))
                        .show(getSupportFragmentManager(), "custom");
            }
        });
        // Нажал "Пропустить"
        binding.mainSkipBtn.setOnClickListener(view -> {
            startSchedule(getResources().getString(R.string.standard_user_name));
        });
    }

    private void saveName(String name){
        SharedPreferences.Editor editor = getSharedPreferences(Config.PREFERENCES, MODE_PRIVATE).edit();
        editor.putString(Config.NAME_KEY, name);
        editor.apply();
    }

    private void startSchedule(String name){
        saveName(name);
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }
}