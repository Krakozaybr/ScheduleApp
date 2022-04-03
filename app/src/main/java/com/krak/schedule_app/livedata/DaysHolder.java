package com.krak.schedule_app.livedata;

import android.app.Application;

import com.krak.schedule_app.app.App;
import com.krak.schedule_app.app.database.AppDatabase;
import com.krak.schedule_app.app.database.ScheduleDao;
import com.krak.schedule_app.entities.Day;

import java.util.concurrent.atomic.AtomicInteger;

public class DaysHolder extends ListHolder<Day>{

    public DaysHolder(Application application) {
        super(application);
    }

    public void loadData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                App app = getApplication();
                AppDatabase db = app.getDatabase();
                ScheduleDao dao = db.scheduleDao();
                data.postValue(dao.getDays());
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
                ScheduleDao dao = db.scheduleDao();
                if (data.getValue() != null)
                    dao.insertDaysAndLessons(data.getValue());
            }
        }.start();
    }
}
