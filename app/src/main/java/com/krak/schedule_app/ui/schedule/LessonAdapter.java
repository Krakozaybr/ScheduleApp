package com.krak.schedule_app.ui.schedule;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.krak.schedule_app.R;
import com.krak.schedule_app.entities.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder> {

    private List<Lesson> lessons;
    private LayoutInflater inflater;
    private ArrayList<LessonHolder> holders;

    public LessonAdapter(Context context, List<Lesson> lessons) {
        this.lessons = lessons;
        this.inflater = LayoutInflater.from(context);
        this.holders = new ArrayList<>();
    }

    @Override
    public LessonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.schedule_lesson, parent, false);
        return new LessonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.number.setText(lesson.getNumber() + "");
        holder.input.setText(lesson.getText());
        holders.add(holder);
        holder.lesson = lesson;
    }

    // Вызываем у всех холдеров сохранение
    public void save(){
        for (LessonHolder holder : holders){
            holder.save();
        }
    }

    // Очищаем все места ввода
    public void clear(){
        for (LessonHolder holder : holders){
            holder.clear();
        }
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    class LessonHolder extends RecyclerView.ViewHolder{

        private AppCompatEditText input;
        private TextView number;
        private Lesson lesson;

        public LessonHolder(@NonNull View itemView) {
            super(itemView);
            this.input = itemView.findViewById(R.id.lessonInput);
            this.number = itemView.findViewById(R.id.number);
        }

        public void clear(){
            this.input.setText("");
        }

        public void save(){
            lesson.setText(input.getText().toString());
        }
    }
}
