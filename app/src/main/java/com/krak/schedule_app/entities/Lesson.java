package com.krak.schedule_app.entities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "lessons")
public class Lesson {
    @NonNull
    @PrimaryKey
    private String position;
    @ColumnInfo(name = "day_number")
    private int dayNumber;
    private int number;
    private String text;

    public Lesson(int dayNumber, int number, String text) {
        this.dayNumber = dayNumber;
        this.number = number;
        this.text = text;
        this.position = dayNumber + "_" + number;
    }

    public Lesson() {
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toJSON(){
        JSONObject result = new JSONObject();
        try{
            result.put("number", number);
            result.put("text", text);
        } catch (JSONException e){
            Log.e("JSON", "JSONException while converting obj Lesson to json");
        }
        return result.toString();
    }

    public static Lesson parse(String json){
        try {
            return parse(new JSONObject(json));
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj Day to json");
            return null;
        }
    }

    public static Lesson parse(JSONObject json){
        Lesson result = new Lesson();
        try {
            result.number = json.getInt("number");
            result.text = json.getString("text");
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj Lesson to json");
        }
        return result;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }
}
