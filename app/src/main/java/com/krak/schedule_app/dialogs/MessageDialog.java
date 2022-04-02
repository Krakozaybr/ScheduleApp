package com.krak.schedule_app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment {

    private String message = null;
    private String title = null;

    public MessageDialog(@NonNull String title, @NonNull String message) {
        this.title = title;
        this.message = message;
    }

    public MessageDialog(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (message != null){
            builder.setMessage(message);
        }
        return builder.setTitle(title).setPositiveButton("OK", null).create();
    }
}
