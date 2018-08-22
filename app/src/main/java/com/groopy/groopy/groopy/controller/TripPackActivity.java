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


public class TripPackActivity extends AppCompatActivity implements View.OnClickListener
{
    // private TripPackageViewModel _tripPackage = null;
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

        View resetButton = findViewById(R.id._resetButton);
        resetButton.setOnClickListener(TripPackActivity.this);

        // Assign the component to a property in the binding class.
        _binding.setTripPackage1(tripPackageVM);
    }

    public void launchNewPackItemFragment()
    {
        NewPackItemFragment npif = new NewPackItemFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.newPackItemFragmentContainer, npif, "NewPackItemFragment");
        transaction.commit();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        _binding.setTripPackage(new TripPackage("Tenerife Tour"));
//    }

    @Override
    public void onClick(View v)
    {
//        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
//        tripPackageVM.setName("Reseted");
//        tripPackageVM.setDescription("No Description");
//        PackItem pi = new PackItem("Reseted Item");
//        List<PackItem> lpi = new ArrayList<>();
//        lpi.add(pi);
//        tripPackageVM.setPackItems(lpi);
        launchNewPackItemFragment();
    }

    public void notifyPackItemRemoved(int position)
    {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        tripPackageVM.removeItem(position);
    }

    public void notifyPackItemAdded(PackItem item, int position)
    {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        tripPackageVM.addItem(item, position);
    }

}
