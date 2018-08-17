package com.groopy.groopy.groopy.controller;

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
    private TripPackageViewModel _tripPackage = null;
    private ActivityTripPackBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Inflate layout
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_pack);
//        _tripPackage = new TripPackage("Tenerife Trip");
//        binding.setTripPackage1(_tripPackage);

        View resetButton = findViewById(R.id._resetButton);
        resetButton.setOnClickListener(TripPackActivity.this);

        // Obtain the ViewModel component.
//        TripPackage
        _tripPackage = new TripPackageViewModel("Kiew Trip");

        // Inflate view and obtain an instance of the binding class.
        // ActivityTripPackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_pack);

        // Assign the component to a property in the binding class.
        _binding.setTripPackage1(_tripPackage);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        _binding.setTripPackage(new TripPackage("Tenerife Tour"));
//    }

    @Override
    public void onClick(View v)
    {
        _tripPackage.setName("Reseted");
        _tripPackage.setDescription("No Description");
        PackItem pi = new PackItem("Reseted Item");
        List<PackItem> lpi = new ArrayList<>();
        lpi.add(pi);
        _tripPackage.setPackItems(lpi);
    }

}
