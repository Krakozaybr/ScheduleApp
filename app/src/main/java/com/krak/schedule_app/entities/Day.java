package com.krak.schedule_app.entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
// Таблица одного дня в расписании
@Entity(tableName = "days")
public class Day {
    @PrimaryKey
    private int dayNumber;
    private String name;
    @Ignore
    private List<Lesson> lessons;

    public Day(){}

    public Day(int dayNumber, String name, List<Lesson> lessons) {
        this.dayNumber = dayNumber;
        this.name = name;
        this.lessons = lessons;
    }

    private String toJSON(){
        JSONObject result = new JSONObject();
        try{
            result.put("name", name);
            JSONArray array = new JSONArray();
            for (Lesson lesson : lessons){
                array.put(lesson.toJSON());
            }
            result.put("lessons", array);
        } catch (JSONException e){
            Log.e("JSON", "JSONException while converting obj Day to json");
        }
        return result.toString();
    }

    public static Day parse(String json){
        try {
            return parse(new JSONObject(json));
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj Day to json");
            return null;
        }
    }

    public static Day parse(JSONObject json){
        Day result = new Day();
        try {
            String name = json.getString("name");
            JSONArray array = json.getJSONArray("lessons");
            ArrayList<Lesson> lessons = new ArrayList<>(array.length());
            for (int i = 0; i < array.length(); i++) {
                lessons.add(Lesson.parse(array.getString(i)));
            }
            result.name = name;
            result.lessons = lessons;
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj Day to json");
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }
}
