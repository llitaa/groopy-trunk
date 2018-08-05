package com.groopy.groopy.groopy.controller;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.databinding.ActivityTripPackBinding;
import com.groopy.groopy.groopy.model.TripPackage;

public class TripPackActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Inflate layout
        ActivityTripPackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_pack);
        binding.setTripPackage(new TripPackage("Tenerife Tour"));
        binding.tripPackage.setTripPackage(new TripPackage("Tenerife Tour"));
        _binding = binding;
    }

    @Override
    protected void onResume() {
        super.onResume();
        _binding.setTripPackage(new TripPackage("Tenerife Tour"));
    }

    private ActivityTripPackBinding _binding;
}
