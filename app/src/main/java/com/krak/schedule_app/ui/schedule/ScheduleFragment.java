package com.krak.schedule_app.ui.schedule;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.krak.schedule_app.R;
import com.krak.schedule_app.databinding.ScheduleFragmentBinding;
import com.krak.schedule_app.entities.Day;
import com.krak.schedule_app.entities.Lesson;
import com.krak.schedule_app.livedata.DaysHolder;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.utils.Saveable;
import com.krak.schedule_app.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment implements Saveable {

    private ScheduleFragmentBinding binding;
    private static final String TAG = "SCHEDULE_ACTIVITY";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ScheduleFragmentBinding.inflate(getLayoutInflater());
        initDays();
        MainActivity.saveable = this;
        addEventListeners();
        return binding.getRoot();
    }

    private void addEventListeners() {
        binding.clearAllScheduleBtn.setOnClickListener(view -> {
            new ViewModelProvider(getActivity()).get(DaysHolder.class).updateData(getInitialData());
        });
    }


    // Стандартный шаблон, если ничего нет
    private ArrayList<Day> getInitialData(){
        String[] dayNames = {
                getString(R.string.monday),
                getString(R.string.tuesday),
                getString(R.string.wednesday),
                getString(R.string.thursday),
                getString(R.string.friday),
                getString(R.string.saturday),
                getString(R.string.sunday)
        };
        ArrayList<Day> result = new ArrayList<>();
        for (int j = 0; j < dayNames.length; j++){
            String dayName = dayNames[j];
            ArrayList<Lesson> lessons = new ArrayList<>(7);
            for (int i = 0; i < 9; i++) {
                lessons.add(new Lesson(j, i + 1, ""));
            }
            Day day = new Day(j, dayName, lessons);
            result.add(day);
        }
        return result;
    }

    // Грузим данные
    private void initDays(){
        LiveData<List<Day>> liveData = new ViewModelProvider(getActivity()).get(DaysHolder.class).getData();
        List<Day> dayList = liveData.getValue();
        DayAdapter adapter;
        if (dayList == null || dayList.size() == 0) {
            // если в бд пусто, берем стандартный шаблон
            adapter = new DayAdapter((MainActivity)getActivity(), getInitialData());
        } else {
            adapter = new DayAdapter((MainActivity)getActivity(), dayList);
        }
        binding.days.setAdapter(adapter);
        liveData.observe(getActivity(), days -> {
            if (binding != null && binding.days != null) {
                binding.days.setAdapter(new DayAdapter((MainActivity)getActivity(), days));
            }
        });
    }

    public void save(){
        DayAdapter adapter = (DayAdapter) binding.days.getAdapter();
        adapter.save();
        new ViewModelProvider(getActivity()).get(DaysHolder.class).updateData(adapter.getDays());
    }

    @Override
    public String toString() {
        return "ScheduleFragment{";
    }

    @Override
    public void onDestroyView() {
        save();
        binding = null;
        super.onDestroyView();
    }
}