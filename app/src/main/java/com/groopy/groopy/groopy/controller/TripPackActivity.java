package com.groopy.groopy.groopy.controller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.databinding.ActivityTripPackBinding;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.model.TripPackage;
import com.groopy.groopy.groopy.ui.TripPackageView;
import com.groopy.groopy.groopy.viewModel.*;
import java.util.ArrayList;
import java.util.List;


public class TripPackActivity extends AppCompatActivity
{
    private ActivityTripPackBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Create view model
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        tripPackageVM.setName("Kiew Trip");
        // Inflate layout
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_pack);
        // Assign the component to a property in the binding class.
        _binding.setTripPackage1(tripPackageVM);
    }

    public void onNewPackItemRequested()
    {
        launchNewPackItemFragment();
    }

    public void onNewPackItemAddingCompleted()
    {
        closeNewPackItemFragment();
    }

    private void launchNewPackItemFragment()
    {
        NewPackItemFragment npif = new NewPackItemFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.newPackItemFragmentContainer, npif, "NewPackItemFragment");
        transaction.commit();
    }

    private void closeNewPackItemFragment()
    {
        FragmentManager manager = getFragmentManager();
        NewPackItemFragment frg = (NewPackItemFragment) manager.findFragmentById(R.id.newPackItemFragmentContainer);
        if (frg != null)
        {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(frg);
            transaction.commit();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        _binding.setTripPackage(new TripPackage("Tenerife Tour"));
//    }

    public void notifyPackItemRemoved(int position)
    {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        tripPackageVM.removeItem(position);
    }

    public void notifyPackItemAdded(PackItem item)
    {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        // TODO (LYT) add adapter to view model and avoid this call
        List<PackItem> items = tripPackageVM.getPackItems();
        // int size = tripPackageVM.getPackItems().size();
        items.add(item);
        tripPackageVM.setPackItems(items);
    }
}
