package com.groopy.groopy.groopy.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.controller.TripPackActivity;
import com.groopy.groopy.groopy.model.TripPackage;
import com.groopy.groopy.groopy.databinding.TripPackageViewBinding;

public class TripPackageView extends LinearLayout
{
    public TripPackageView(Context context)
    {
        super(context);
    }

    public TripPackageView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        subscribeToAddItemRequest();
    }

    private void subscribeToAddItemRequest()
    {
        ImageButton addItemBtn = findViewById(R.id._addPackItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (v.getId() == R.id._addPackItemBtn)
                {
                    TripPackActivity activity = ContextSearchUtils.GetTripPackActivity(v);
                    activity.onNewPackItemRequested();
                }
            }
        });
    }

}
