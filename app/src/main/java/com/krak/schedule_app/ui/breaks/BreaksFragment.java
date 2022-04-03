package com.krak.schedule_app.ui.breaks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.krak.schedule_app.databinding.BreaksFragmentBinding;
import com.krak.schedule_app.entities.BreaksRow;
import com.krak.schedule_app.entities.BreaksSchedule;
import com.krak.schedule_app.livedata.BreaksHolder;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.utils.Saveable;
import com.krak.schedule_app.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BreaksFragment extends Fragment implements Saveable {

    private BreaksFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = BreaksFragmentBinding.inflate(getLayoutInflater());
        addListeners();
        initBreaks();
        MainActivity.saveable = this;
        binding.daysBreaks.addItemDecoration(new SpacesItemDecoration(80));
        return binding.getRoot();
    }

    private void initBreaks(){
        MainActivity activity =(MainActivity)getActivity();
        BreaksHolder holder = new ViewModelProvider(activity).get(BreaksHolder.class);
        holder.getData().observe(activity, breaksSchedules -> {
            if (binding != null && binding.daysBreaks != null) {
                BreaksAdapter adapter = new BreaksAdapter(activity, breaksSchedules);
                binding.daysBreaks.setAdapter(adapter);
            }
        });
    }

    private BreaksSchedule getInitialData(){
        int number = binding.daysBreaks.getAdapter().getItemCount();
        ArrayList<BreaksRow> rows = new ArrayList<>();
        rows.add(new BreaksRow(number, "уроки", "время", "перемена", 0));
        for (int i = 1; i < 10; i++) {
            rows.add(new BreaksRow(number, i + " урок", "", "", i));
        }
        return new BreaksSchedule(number, rows, "Расписание " + (number + 1));
    }

    private void addListeners(){
        binding.breaksPlusBtn.setOnClickListener(view -> {
            new ViewModelProvider(getActivity()).get(BreaksHolder.class).add(getInitialData());
        });
    }

    public void save(){
        BreaksAdapter adapter = (BreaksAdapter)binding.daysBreaks.getAdapter();
        adapter.save();
        new ViewModelProvider(getActivity()).get(BreaksHolder.class).updateData(adapter.getBreaks());
    }

    @Override
    public void onDestroyView() {
        save();
        binding = null;
        super.onDestroyView();
    }
}
