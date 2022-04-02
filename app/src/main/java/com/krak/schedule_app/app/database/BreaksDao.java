package com.krak.schedule_app.app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.krak.schedule_app.entities.BreaksRow;
import com.krak.schedule_app.entities.BreaksSchedule;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class BreaksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertBreak(BreaksSchedule schedule);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertBreaksSchedules(List<BreaksSchedule> breaksSchedules);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertBreaksRows(List<BreaksRow> rows);

    @Delete
    public abstract void deleteBreaksSchedule(BreaksSchedule breaksSchedule);

    @Delete
    public abstract void deleteBreaksRows(List<BreaksRow> rows);

    @Query("SELECT * FROM breaks_schedule")
    public abstract List<BreaksSchedule> getEmptyBreaksSchedules();

    @Query("SELECT * FROM breaks_rows WHERE breaks_schedule_number == :number")
    public abstract List<BreaksRow> getBreaksRowsWithBreaksScheduleNumber(int number);

    @Transaction
    public List<BreaksSchedule> getBreaksSchedules(){
        List<BreaksSchedule> breaksSchedules = getEmptyBreaksSchedules();
        for (BreaksSchedule breaksSchedule : breaksSchedules){
            breaksSchedule.setRows(getBreaksRowsWithBreaksScheduleNumber(breaksSchedule.getNumber()));
        }
        return breaksSchedules;
    }

    @Transaction
    public void insertBreaks(List<BreaksSchedule> breaksSchedules){
        insertBreaksSchedules(breaksSchedules);
        ArrayList<BreaksRow> rows = new ArrayList<>();
        for (BreaksSchedule schedule : breaksSchedules){
            rows.addAll(schedule.getRows());
        }
        insertBreaksRows(rows);
    }

    @Transaction
    public void deleteBreaks(BreaksSchedule schedule){
        deleteBreaksSchedule(schedule);
        deleteBreaksRows(schedule.getRows());
    }
}
