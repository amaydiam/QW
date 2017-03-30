package com.qwash.washer.utils;

import android.content.Context;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.qwash.washer.Sample;

import java.util.List;

public class TextUtils {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.equals("null") || str.equals(""));
    }

    public static void errorValidation(Context context,List<ValidationError> errors){
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }else if (view instanceof AutoCompleteTextView) {
                ((AutoCompleteTextView) view).setError(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getBulan(Context context, int bulan){
        String[] list = Sample.listMontOfYear(context);
        return list[bulan-1];
    }

    public static String ReplaceFirstCaracters(String inputStr) {
        String substr = inputStr.substring(0, 1);
        if (substr.equalsIgnoreCase("0")) {
            return ReplaceFirstCaracters(inputStr.substring(1, inputStr.length()));
        } else {
            return inputStr;
        }
    }

}
