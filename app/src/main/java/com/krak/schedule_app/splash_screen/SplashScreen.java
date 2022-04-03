package com.krak.schedule_app.splash_screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.krak.schedule_app.databinding.SplashScreenActivityBinding;
import com.krak.schedule_app.main_activity.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private MutableLiveData<Boolean> data = new MutableLiveData<>();
    private SplashScreenActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashScreenActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        data.observe(this, aBoolean -> startActivity(new Intent(SplashScreen.this, MainActivity.class)));
        new Thread(() -> {
            try{
                Thread.sleep(2000);
                data.postValue(false);
            } catch (InterruptedException ignored){}
        }).start();
    }


}
