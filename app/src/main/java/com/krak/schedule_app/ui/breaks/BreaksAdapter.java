package com.krak.schedule_app.ui.breaks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.krak.schedule_app.R;
import com.krak.schedule_app.entities.BreaksSchedule;
import com.krak.schedule_app.livedata.BreaksHolder;
import com.krak.schedule_app.main_activity.MainActivity;
import com.krak.schedule_app.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BreaksAdapter extends RecyclerView.Adapter<BreaksAdapter.BreaksSchedulesHolder> {

    private MainActivity context;
    private LayoutInflater inflater;
    private List<BreaksSchedule> breaks;
    private ArrayList<BreaksSchedulesHolder> holders;

    public BreaksAdapter(MainActivity context, List<BreaksSchedule> breaks) {
        this.context = context;
        this.breaks = breaks;
        this.inflater = LayoutInflater.from(context);
        this.holders = new ArrayList<>();
    }

    @Override
    public BreaksSchedulesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.breaks_schedule, parent, false);
        return new BreaksSchedulesHolder(view);
    }

    @Override
    public int getItemCount() {
        return breaks.size();
    }

    @Override
    public void onBindViewHolder(BreaksSchedulesHolder holder, int position) {
        BreaksSchedule schedule = breaks.get(position);
        holder.title.setText(schedule.getTitle());

        BreaksRowsAdapter adapter = new BreaksRowsAdapter(holder.parent.getContext(), schedule.getRows());
        holder.rows.setAdapter(adapter);
        holder.rows.addItemDecoration(new SpacesItemDecoration(0));
        holder.schedule = schedule;
        holders.add(holder);
        holder.addEventListener();
    }

    // Вызываем у всех холдеров сохранение
    public void save(){
        for (BreaksSchedulesHolder holder : holders){
            holder.save();
        }
    }

    public List<BreaksSchedule> getBreaks() {
        return breaks;
    }

    class BreaksSchedulesHolder extends RecyclerView.ViewHolder{

        private RecyclerView rows;
        private AppCompatEditText title;
        private ImageButton delete;
        private View parent;
        private BreaksSchedule schedule;

        public BreaksSchedulesHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.breaksScheduleTitle);
            this.rows = itemView.findViewById(R.id.daysBreaks);
            this.parent = itemView;
            this.delete = itemView.findViewById(R.id.deleteScheduleBtn);
        }

        public void save(){
            // Сохраняем все строки
            ((BreaksRowsAdapter)this.rows.getAdapter()).save();
            // Сохраняем заголовок
            schedule.setTitle(title.getText().toString());
        }

        private void addEventListener(){
            // Нажал на мусорку
            this.delete.setOnClickListener(view -> {
                new ViewModelProvider(context).get(BreaksHolder.class).delete(schedule);
            });
        }
    }
}
