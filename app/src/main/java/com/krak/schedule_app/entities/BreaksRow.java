package com.krak.schedule_app.entities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

@Entity(tableName = "breaks_rows")
public class BreaksRow {
    @ColumnInfo(name = "breaks_schedule_number")
    private int breaksScheduleNumber;

    @NonNull
    @PrimaryKey
    private String position;

    private String col1, col2, col3;

    public BreaksRow(int breaksScheduleNumber, String col1, String col2, String col3, int position) {
        this.breaksScheduleNumber = breaksScheduleNumber;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.position = breaksScheduleNumber + "_" + position;
    }

    public BreaksRow(){}

    public String toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("col1", col1);
            jsonObject.put("col2", col2);
            jsonObject.put("col3", col3);
        } catch (JSONException e){
            Log.e("JSON", "JSONException while converting BreaksRow obj to JSON");
        }
        return jsonObject.toString();
    }

    public static BreaksRow parse(String json){
        try {
            return parse(new JSONObject(json));
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj BreaksRow to json");
            return null;
        }
    }

    public static BreaksRow parse(JSONObject json){
        BreaksRow result = new BreaksRow();
        try {
            result.col1 = json.getString("col1");
            result.col2 = json.getString("col2");
            result.col3 = json.getString("col3");
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj BreaksRow to json");
        }
        return result;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public int getBreaksScheduleNumber() {
        return breaksScheduleNumber;
    }

    public void setBreaksScheduleNumber(int breaksScheduleNumber) {
        this.breaksScheduleNumber = breaksScheduleNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreaksRow breaksRow = (BreaksRow) o;
        return getBreaksScheduleNumber() == breaksRow.getBreaksScheduleNumber() &&
                Objects.equals(getCol1(), breaksRow.getCol1()) &&
                Objects.equals(getCol2(), breaksRow.getCol2()) &&
                Objects.equals(getCol3(), breaksRow.getCol3());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBreaksScheduleNumber(), getCol1(), getCol2(), getCol3());
    }
}
