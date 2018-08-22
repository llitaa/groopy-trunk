package com.groopy.groopy.groopy.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.groopy.groopy.groopy.model.TripPackage;
import com.groopy.groopy.groopy.databinding.TripPackageViewBinding;

public class TripPackageView extends LinearLayout {
    public TripPackageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        _binding = DataBindingUtil.getBinding(this);
    }

    private TripPackageViewBinding _binding;
    private TripPackage _tripPackage;
}
