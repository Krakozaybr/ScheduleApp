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
import java.util.Objects;

// Таблица расписания звонков
@Entity(tableName = "breaks_schedule")
public class BreaksSchedule {

    @PrimaryKey
    private int number;
    @Ignore
    private List<BreaksRow> rows;
    private String title;

    public BreaksSchedule(int number, List<BreaksRow> rows, String title) {
        this.number = number;
        this.rows = rows;
        this.title = title;
    }

    public BreaksSchedule(){}

    public String toJSON(){
        JSONObject result = new JSONObject();
        try {
            result.put("title", title);
            JSONArray array = new JSONArray();
            for (BreaksRow row : rows){
                array.put(row.toJSON());
            }
            result.put("rows", array);
        } catch (JSONException e){
            Log.e("JSON", "JSONException while converting BreaksSchedule obj to json");
        }
        return result.toString();
    }

    public static BreaksSchedule parse(String json){
        try {
            return parse(new JSONObject(json));
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj BreaksSchedule to json");
            return null;
        }
    }

    public static BreaksSchedule parse(JSONObject json){
        BreaksSchedule result = new BreaksSchedule();
        try {
            result.title = json.getString("title");
            List<BreaksRow> rows = new ArrayList<>();
            JSONArray array = json.getJSONArray("rows");
            for (int i = 0; i < array.length(); i++) {
                rows.add(BreaksRow.parse(array.getString(i)));
            }
            result.rows = rows;
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj BreaksSchedule to json");
        }
        return result;
    }

    public List<BreaksRow> getRows() {
        return rows;
    }

    public void setRows(List<BreaksRow> rows) {
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreaksSchedule that = (BreaksSchedule) o;
        return getNumber() == that.getNumber() &&
                Objects.equals(getRows(), that.getRows()) &&
                Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getRows(), getTitle());
    }
}
