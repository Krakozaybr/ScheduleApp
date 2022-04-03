package com.krak.schedule_app.livedata;

import android.app.Application;

import com.krak.schedule_app.app.App;
import com.krak.schedule_app.app.database.AppDatabase;
import com.krak.schedule_app.app.database.BreaksDao;
import com.krak.schedule_app.app.database.NoteDao;
import com.krak.schedule_app.app.database.ScheduleDao;
import com.krak.schedule_app.entities.BreaksSchedule;
import com.krak.schedule_app.entities.Note;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BreaksHolder extends ListHolder<BreaksSchedule> {

    public BreaksHolder(Application application) {
        super(application);
    }

    public void loadData(AtomicInteger threadsWorked){
        new Thread(){
            @Override
            public void run() {
                super.run();
                App app = getApplication();
                AppDatabase db = app.getDatabase();
                BreaksDao dao = db.breaksDao();
                data.postValue(dao.getBreaksSchedules());
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
                BreaksDao dao = db.breaksDao();
                if (data.getValue() != null)
                    dao.insertBreaks(data.getValue());
            }
        }.start();
    }

    public void delete(BreaksSchedule breaksSchedule){
        if (data.getValue().contains(breaksSchedule)){
            List<BreaksSchedule> breaksSchedules = data.getValue();
            breaksSchedules.remove(breaksSchedule);
            data.setValue(breaksSchedules);
            new Thread(() -> {
                App app = getApplication();
                AppDatabase db = app.getDatabase();
                BreaksDao dao = db.breaksDao();
                dao.deleteBreaks(breaksSchedule);
            }).start();
        }
    }

    public void add(BreaksSchedule breaksSchedule){
        List<BreaksSchedule> breaksSchedules = data.getValue();
        breaksSchedules.add(breaksSchedule);
        data.setValue(breaksSchedules);
        new Thread(() -> {
            App app = getApplication();
            AppDatabase db = app.getDatabase();
            BreaksDao dao = db.breaksDao();
            dao.insertBreak(breaksSchedule);
            dao.insertBreaksRows(breaksSchedule.getRows());
        }).start();
    }
}
