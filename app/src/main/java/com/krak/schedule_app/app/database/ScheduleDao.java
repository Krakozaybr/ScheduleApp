package com.krak.schedule_app.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.krak.schedule_app.entities.Day;
import com.krak.schedule_app.entities.Lesson;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertDays(List<Day> days);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertLessons(List<Lesson> lessons);

    @Query("SELECT * FROM lessons WHERE day_number == :number")
    public abstract List<Lesson> getLessonsOfDayWithNumber(int number);

    @Query("SELECT * FROM days")
    public abstract List<Day> getEmptyDays();

    @Transaction
    public List<Day> getDays(){
        List<Day> days = getEmptyDays();
        for (Day day : days){
            day.setLessons(getLessonsOfDayWithNumber(day.getDayNumber()));
        }
        return days;
    }

    @Transaction
    public void insertDaysAndLessons(List<Day> days){
        insertDays(days);
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Day day : days){
            lessons.addAll(day.getLessons());
        }
        insertLessons(lessons);
    }
}
