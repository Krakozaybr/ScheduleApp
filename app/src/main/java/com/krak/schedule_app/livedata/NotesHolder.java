package com.krak.schedule_app.livedata;

import android.app.Application;

import com.krak.schedule_app.app.App;
import com.krak.schedule_app.app.database.AppDatabase;
import com.krak.schedule_app.app.database.BreaksDao;
import com.krak.schedule_app.app.database.NoteDao;
import com.krak.schedule_app.entities.Note;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotesHolder extends ListHolder<Note>{

    public NotesHolder(Application application) {
        super(application);
    }

    public void loadData(AtomicInteger threadsWorked){
        new Thread(){
            @Override
            public void run() {
                super.run();
                App app = getApplication();
                AppDatabase db = app.getDatabase();
                NoteDao dao = db.notesDao();
                data.postValue(dao.getAll());
                threadsWorked.incrementAndGet();
            }
        }.start();
    }

    @Override
    public void uploadData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                App app = getApplication();
                AppDatabase db = app.getDatabase();
                NoteDao dao = db.notesDao();
                if (data.getValue() != null)
                    dao.insertAll(data.getValue());
            }
        }.start();
    }

    public void delete(Note note){
        if (data.getValue().contains(note)){
            List<Note> noteList = data.getValue();
            noteList.remove(note);
            data.setValue(noteList);
            new Thread(() -> {
                App app = getApplication();
                AppDatabase db = app.getDatabase();
                NoteDao dao = db.notesDao();
                dao.delete(note);
            }).start();
        }
    }
}
