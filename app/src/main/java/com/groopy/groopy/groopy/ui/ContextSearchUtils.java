package com.groopy.groopy.groopy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import com.groopy.groopy.groopy.controller.TripPackActivity;

public class ContextSearchUtils {
    public static TripPackActivity GetTripPackActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (TripPackActivity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
