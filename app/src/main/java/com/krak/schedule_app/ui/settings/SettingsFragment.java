package com.krak.schedule_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.krak.schedule_app.R;
import com.krak.schedule_app.databinding.SettingsFragmentBinding;
import com.krak.schedule_app.dialogs.NameInputDialog;
import com.krak.schedule_app.main_activity.MainActivity;

public class SettingsFragment extends Fragment {

    private SettingsFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater, container, false);
        addEventListeners();
        MainActivity.saveable = null;
        return binding.getRoot();
    }

    private void addEventListeners(){
        binding.changeNameBtn.setOnClickListener(view -> {
            new NameInputDialog(getResources().getString(R.string.change_name), getActivity())
                    .show(getActivity().getSupportFragmentManager(), "custom");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}