package com.dsantano.proyectodam.datepicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DialogDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static IDatePickerListener i;

    public static DialogDatePickerFragment newInstance(Activity activity) {
        i = (IDatePickerListener) activity;
        return new DialogDatePickerFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        i.onDateSelected(year, month, day);
    }
}