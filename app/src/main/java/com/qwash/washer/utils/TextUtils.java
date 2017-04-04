package com.qwash.washer.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.qwash.washer.R;
import com.qwash.washer.Sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TextUtils {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.equals("null") || str.equals(""));
    }

    public static void errorValidation(Context context, List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof AutoCompleteTextView) {
                ((AutoCompleteTextView) view).setError(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getBulan(Context context, int bulan) {
        String[] list = Sample.listMontOfYear(context);
        return list[bulan - 1];
    }

    public static String ReplaceFirstCaracters(String inputStr) {
        String substr = inputStr.substring(0, 1);
        if (substr.equalsIgnoreCase("0")) {
            return ReplaceFirstCaracters(inputStr.substring(1, inputStr.length()));
        } else {
            return inputStr;
        }
    }


    public static Date getDate(String sdate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(sdate);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static String getTodayYestFromMilli(Activity activity, String sdate, long msgTimeMillis) {

        Calendar messageTime = Calendar.getInstance();
        messageTime.setTimeInMillis(msgTimeMillis);
        // get Currunt time
        Calendar now = Calendar.getInstance();

        if (now.get(Calendar.DATE) == messageTime.get(Calendar.DATE)
                &&
                ((now.get(Calendar.MONTH) == messageTime.get(Calendar.MONTH)))
                &&
                ((now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)))
                ) {

            return activity.getString(R.string.today);

        } else if (
                ((now.get(Calendar.DATE) - messageTime.get(Calendar.DATE)) == 1)
                        &&
                        ((now.get(Calendar.MONTH) == messageTime.get(Calendar.MONTH)))
                        &&
                        ((now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)))
                ) {
            return activity.getString(R.string.yesterday);
        } else {
            String d[] = sdate.split("-");
            return d[2] + " " + getBulan(activity, Integer.parseInt(d[1])) + " " + d[0];
        }
    }

    public static String DefaultDateFormat(String date) {
//yyyy-MM-dd HH:mm:ss
        return date.replace("T"," ").replace(".000Z","");
    }
}
