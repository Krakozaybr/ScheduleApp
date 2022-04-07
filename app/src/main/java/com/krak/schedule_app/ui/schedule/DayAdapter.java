package com.krak.schedule_app.ui.schedule;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.krak.schedule_app.R;
import com.krak.schedule_app.entities.Day;
import com.krak.schedule_app.livedata.DaysHolder;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    private LayoutInflater inflater;
    private List<Day> days;
    private ArrayList<DayHolder> holders;
    private MainActivity context;

    public DayAdapter(MainActivity context, List<Day> states) {
        this.days = states;
        this.inflater = LayoutInflater.from(context);
        this.holders = new ArrayList<>();
        this.context = context;
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
        holder.day = day;
        holder.addListeners();
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
        final ImageButton clearBtn;
        private Day day;
        private boolean areLessonsShowed = false;

        public DayHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.dayName);
            lessons = itemView.findViewById(R.id.lessons);
            parent = itemView;
            clearBtn = itemView.findViewById(R.id.clearBtn);
            lessons.setVisibility(View.GONE);
        }

        private void toggleLessonsShowed(){
            if (areLessonsShowed){
                lessons.animate()
                        .translationY(lessons.getHeight())
                        .alpha(0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                lessons.setVisibility(View.GONE);
                            }
                        });
            } else{
                lessons.animate()
                        .translationYBy(-lessons.getHeight()).translationY(0).setDuration(100)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                lessons.setVisibility(View.VISIBLE);
                            }
                        });
            }
            areLessonsShowed = !areLessonsShowed;
        }

        public void addListeners(){
            clearBtn.setOnClickListener(view -> {
                clear();
            });
            // Нажал на заголовок. Надо показать или скрыть lessons с анимацией
            this.title.setOnClickListener(view -> {
                toggleLessonsShowed();
            });
        }

        public void clear(){
            day.clear();
            lessons.setAdapter(new LessonAdapter(context, day.getLessons()));
        }

        public void save(){
            ((LessonAdapter)lessons.getAdapter()).save();
        }
    }
}
