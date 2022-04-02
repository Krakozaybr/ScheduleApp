package com.krak.schedule_app.app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.krak.schedule_app.entities.BreaksRow;
import com.krak.schedule_app.entities.BreaksSchedule;
import com.krak.schedule_app.entities.Day;
import com.krak.schedule_app.entities.Lesson;
import com.krak.schedule_app.entities.Note;

@Database(entities = {BreaksRow.class, BreaksSchedule.class, Day.class, Lesson.class, Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao notesDao();
    public abstract BreaksDao breaksDao();
    public abstract ScheduleDao scheduleDao();
}
