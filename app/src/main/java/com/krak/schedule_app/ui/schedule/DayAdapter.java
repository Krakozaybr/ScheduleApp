package com.krak.schedule_app.ui.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.krak.schedule_app.R;
import com.krak.schedule_app.entities.Day;
import com.krak.schedule_app.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    private LayoutInflater inflater;
    private List<Day> days;
    private ArrayList<DayHolder> holders;

    public DayAdapter(Context context, List<Day> states) {
        this.days = states;
        this.inflater = LayoutInflater.from(context);
        this.holders = new ArrayList<>();
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.schedule_day, parent, false);
        return new DayHolder(view);
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        Day day = days.get(position);
        holder.title.setText(day.getName());
        LessonAdapter adapter = new LessonAdapter(holder.parent.getContext(), day.getLessons());
        holder.lessons.setAdapter(adapter);
        holder.lessons.addItemDecoration(new SpacesItemDecoration(0));
        holders.add(holder);
    }

    // Вызываем у всех холдеров сохранение
    public void save(){
        for (DayHolder holder : holders){
            holder.save();
        }
    }

    public List<Day> getDays() {
        return days;
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    public class DayHolder extends RecyclerView.ViewHolder {

        final RecyclerView lessons;
        final TextView title;
        final View parent;

        public DayHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.dayName);
            lessons = itemView.findViewById(R.id.lessons);
            parent = itemView;
        }

        public void save(){
            ((LessonAdapter)lessons.getAdapter()).save();
        }
    }
}
