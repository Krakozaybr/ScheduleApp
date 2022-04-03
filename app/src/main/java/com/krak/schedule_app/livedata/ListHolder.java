package com.krak.schedule_app.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Обеспечивает взаимодействие Activity с AppDatabase
 * Модель.
 */

public abstract class ListHolder<V> extends AndroidViewModel {

    MutableLiveData<List<V>> data = new MutableLiveData<>();

    public ListHolder(Application application) {
        super(application);
    }

    public LiveData<List<V>> getData(){
        return data;
    }

    public synchronized void updateData(List<V> newData){
        data.setValue(newData);
    }

    public abstract void loadData();

    public abstract void uploadData();
}
