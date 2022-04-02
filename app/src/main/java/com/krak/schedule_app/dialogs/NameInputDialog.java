package com.krak.schedule_app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.krak.schedule_app.R;
import com.krak.schedule_app.config.Config;
import com.krak.schedule_app.livedata.NameHolder;
import com.krak.schedule_app.utils.NameValidator;

public class NameInputDialog extends DialogFragment {

    private String title, text;
    private FragmentActivity context;

    public NameInputDialog(String title, String text, FragmentActivity context) {
        this.context = context;
        this.title = title;
        this.text = text;
    }

    public NameInputDialog(String title, FragmentActivity context) {
        this.context = context;
        this.title = title;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (text != null){
            builder.setMessage(text);
        }

        SharedPreferences preferences = context.getSharedPreferences(Config.PREFERENCES, Context.MODE_PRIVATE);
        String currentName = preferences.getString(Config.NAME_KEY, "");
        AppCompatEditText input = (AppCompatEditText) LayoutInflater.from(context).inflate(R.layout.input_name_edittext, null);
        input.setText(currentName);
        return builder
                .setTitle(title)
                .setView(input)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String name = input.getText().toString();
                    if (NameValidator.isValidUserName(name)) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Config.NAME_KEY, name);
                        editor.apply();
                        NameHolder nameHolder = new ViewModelProvider(context).get(NameHolder.class);
                        nameHolder.changeName(name);
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.incorrect_name), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
    }
}
