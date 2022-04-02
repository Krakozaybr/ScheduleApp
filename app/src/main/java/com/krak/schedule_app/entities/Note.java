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

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey
    @NonNull
    private String title;
    private String text;

    public Note(){}

    public Note(@NonNull String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String toJSON(){
        JSONObject result = new JSONObject();
        try{
            result.put("title", title);
            result.put("text", text);
        } catch (JSONException e){
            Log.e("JSON", "JSONException while converting Note obj to JSON");
        }
        return result.toString();
    }

    public static Note parse(String json){
        try {
            return parse(new JSONObject(json));
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj Note to json");
            return null;
        }
    }

    public static Note parse(JSONObject json){
        Note result = new Note();
        try {
            result.title = json.getString("title");
            result.text = json.getString("text");
        } catch (JSONException e){
            Log.e("JSON", "JSONException while parsing obj Note to json");
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(getTitle(), note.getTitle()) &&
                Objects.equals(getText(), note.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getText());
    }
}
