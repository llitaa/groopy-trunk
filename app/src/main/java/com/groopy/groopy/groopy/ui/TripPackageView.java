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
import com.groopy.groopy.groopy.adapter.PackListAdapter;
import com.groopy.groopy.groopy.controller.TripPackActivity;
import com.groopy.groopy.groopy.model.TripPackage;
import com.groopy.groopy.groopy.databinding.TripPackageViewBinding;

import java.util.ArrayList;
import java.util.List;

public class TripPackageView extends LinearLayout implements View.OnClickListener {
    TripPackageViewBinding _binding;

    public TripPackageView(Context context) {
        super(context);
    }

    public TripPackageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        _binding = DataBindingUtil.getBinding(this);
        subscribeToAddItemRequest();
        subscribeShowInBagModeChanges();
    }

    private void subscribeToAddItemRequest() {
        ImageButton addItemBtn = findViewById(R.id._addPackItemBtn);
        addItemBtn.setOnClickListener(TripPackageView.this);
    }

    private void subscribeShowInBagModeChanges() {
        Button showInBagItemsBtn = findViewById(R.id._showInBagItemsBtn);
        showInBagItemsBtn.setOnClickListener(TripPackageView.this);
        Button showNotPackedItemsBtn = findViewById(R.id._showNotPackedItemsBtn);
        showNotPackedItemsBtn.setOnClickListener(TripPackageView.this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id._addPackItemBtn) {
            TripPackActivity activity = ContextSearchUtils.GetTripPackActivity(v);
            activity.onNewPackItemRequested();
        } else if (viewId == R.id._showInBagItemsBtn) {
            _binding.setShowInBagItems(true);
        } else if (viewId == R.id._showNotPackedItemsBtn) {
            _binding.setShowInBagItems(false);
        }
    }

    public static class PackItemClickListener implements PackListAdapter.OnItemClickListener {

        @Override
        public void onPackItemClicked(PackItemView item, int position) {
            RecyclerView rv = ((RecyclerView) item.getParent());
            if (rv != null) {
                PackListAdapter adapter = (PackListAdapter) rv.getAdapter();
                TripPackActivity activity = ContextSearchUtils.GetTripPackActivity(item);
                if (adapter != null && activity != null) {
                    int viewId = rv.getId();
                    if (viewId == R.id._toPackItems) {
                        adapter.removeItem(position);
                        activity.notifyItemPacked(position);
                    } else if (viewId == R.id._inBagItems) {
                        adapter.removeItem(position);
                        activity.notifyItemUnpacked(position);
                    }
                }
                // activity.notifyPackItemClicked(rv, position);
            }
        }
    }
}
