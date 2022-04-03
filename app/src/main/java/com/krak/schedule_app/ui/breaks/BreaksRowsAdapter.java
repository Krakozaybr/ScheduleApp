package com.krak.schedule_app.ui.breaks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.krak.schedule_app.R;
import com.krak.schedule_app.entities.BreaksRow;

import java.util.ArrayList;
import java.util.List;

public class BreaksRowsAdapter extends RecyclerView.Adapter<BreaksRowsAdapter.RowHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<BreaksRow> rows;
    private ArrayList<RowHolder> holders;

    public BreaksRowsAdapter(Context context, List<BreaksRow> rows) {
        this.context = context;
        this.rows = rows;
        this.inflater = LayoutInflater.from(context);
        this.holders = new ArrayList<>();
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.breaks_day_row, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, int position) {
        BreaksRow row = rows.get(position);
        holder.col1.setText(row.getCol1());
        holder.col2.setText(row.getCol2());
        holder.col3.setText(row.getCol3());
        holders.add(holder);
        holder.breaksRow = row;
    }

    // Вызываем у всех холдеров сохранение
    public void save(){
        for (RowHolder holder : holders){
            holder.save();
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    class RowHolder extends RecyclerView.ViewHolder{

        private EditText col1, col2, col3;
        private BreaksRow breaksRow;

        public RowHolder(View itemView) {
            super(itemView);
            this.col1 = itemView.findViewById(R.id.breaksCol1);
            this.col2 = itemView.findViewById(R.id.breaksCol2);
            this.col3 = itemView.findViewById(R.id.breaksCol3);
        }

        public void save(){
            breaksRow.setCol1(col1.getText().toString());
            breaksRow.setCol2(col2.getText().toString());
            breaksRow.setCol3(col3.getText().toString());
        }
    }
}
