package com.krak.schedule_app.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.krak.schedule_app.R;
import com.krak.schedule_app.databinding.SettingsFragmentBinding;
import com.krak.schedule_app.dialogs.MessageDialog;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.start.StartActivity;

public class SettingsFragment extends Fragment {

    private SettingsFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater, container, false);
        addEventListeners();
        MainActivity.saveable = null;
        return binding.getRoot();
    }

    private void addEventListeners(){
        // Нажал на кнопку "Изменить имя" -> переходим в StartActivity
        binding.changeNameBtn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), StartActivity.class));
        });
        // Нажал на кнопку "Связь с разработчиком"
        binding.feedbackBtn.setOnClickListener(view -> {
            new MessageDialog(getResources().getString(R.string.feedback), getString(R.string.credit))
                    .show(getActivity().getSupportFragmentManager(), "custom");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}