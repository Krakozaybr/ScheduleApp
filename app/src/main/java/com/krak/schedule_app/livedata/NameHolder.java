package com.krak.schedule_app.livedata;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NameHolder extends ViewModel {

    private MutableLiveData<String> liveData = new MutableLiveData<>();

    public LiveData<String> getData(){
        return liveData;
    }

    public void changeName(String name){
        liveData.setValue(name);
    }
}
