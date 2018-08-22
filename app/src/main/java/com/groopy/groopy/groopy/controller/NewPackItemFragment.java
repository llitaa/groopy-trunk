package com.groopy.groopy.groopy.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.ui.ContextSearchUtils;

public class NewPackItemFragment extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pack_item, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button approveItemBtn = getActivity().findViewById(R.id._approveNewItem);
        approveItemBtn.setOnClickListener(NewPackItemFragment.this);
        Button rejectItemBtn = getActivity().findViewById(R.id._rejectNewItem);
        rejectItemBtn.setOnClickListener(NewPackItemFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        TripPackActivity activity = ContextSearchUtils.GetTripPackActivity(v);
        if (activity != null)
        {
            if (v.getId() == R.id._approveNewItem) {
                PackItem pi = new PackItem("New Item");
                activity.notifyPackItemAdded(pi);
            }
            activity.onNewPackItemAddingCompleted();
        }
    }
}
