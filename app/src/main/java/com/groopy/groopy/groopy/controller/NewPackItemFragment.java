package com.groopy.groopy.groopy.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.ui.ContextSearchUtils;

public class NewPackItemFragment extends Fragment implements View.OnClickListener
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_pack_item, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ImageButton approveItemBtn = getActivity().findViewById(R.id._approveNewItem);
        approveItemBtn.setOnClickListener(NewPackItemFragment.this);
        ImageButton rejectItemBtn = getActivity().findViewById(R.id._rejectNewItem);
        rejectItemBtn.setOnClickListener(NewPackItemFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v)
    {
        TripPackActivity activity = ContextSearchUtils.GetTripPackActivity(v);
        if (activity != null)
        {
            if (v.getId() == R.id._approveNewItem)
            {
                String name = ((EditText) getActivity().findViewById(R.id._title)).getText().toString();
                String desc = ((EditText) getActivity().findViewById(R.id._description)).getText().toString();
                PackItem pi = new PackItem(name, desc);
                activity.notifyPackItemAdded(pi);
            }
            activity.onNewPackItemAddingCompleted();
        }
    }
}
