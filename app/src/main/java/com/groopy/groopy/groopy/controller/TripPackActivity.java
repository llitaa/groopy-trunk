package com.groopy.groopy.groopy.controller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.databinding.ActivityTripPackBinding;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.ui.TripPackageView;
import com.groopy.groopy.groopy.viewModel.*;


public class TripPackActivity extends AuthenticationAwareActivity {
    // private static final String TAG = "TripPackActivity";

    private ActivityTripPackBinding _binding;

    public String getTag() {
        return "TripPackActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        // Create view model
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        tripPackageVM.setName("Kiew Trip");
        // Inflate layout
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_trip_pack);
        // Assign the component to a property in the binding class.
        _binding.setTripPackage1(tripPackageVM);
        _binding.setShowInBagItems1(false);
        _binding.setPackItemClickListener1(new TripPackageView.PackItemClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionSignOut:
                signOut();
                return true;
            case R.id.optionAccountSettings:
                Intent intent = new Intent(TripPackActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onUserSignedOut() {
        Log.d(getTag(), "authenticationState: user is not defined, navigating back to login screen.");
        Intent intent = new Intent(TripPackActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onNewPackItemRequested() {
        launchNewPackItemFragment();
    }

    public void onNewPackItemAddingCompleted() {
        closeNewPackItemFragment();
    }

    private void launchNewPackItemFragment() {
        NewPackItemFragment npif = new NewPackItemFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.newPackItemFragmentContainer, npif, "NewPackItemFragment");
        transaction.commit();
    }

    private void closeNewPackItemFragment() {
        FragmentManager manager = getFragmentManager();
        NewPackItemFragment frg = (NewPackItemFragment) manager.findFragmentById(R.id.newPackItemFragmentContainer);
        if (frg != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(frg);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getTag(), "on resumed");
        checkAuthenticationState();
    }

    public void notifyPackItemRemoved(int position) {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        tripPackageVM.removeToPackItem(position);
    }

    public void notifyPackItemAdded(PackItem item) {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        // TODO (LYT) add adapter to view model and avoid this call
        // List<PackItem> items = tripPackageVM.getPackItems();
        // int size = tripPackageVM.getPackItems().size();

        int size = tripPackageVM.getToPackItems().size();
        tripPackageVM.addToPackItem(item, size);
    }

    public void notifyItemPacked(int position) {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        PackItem item = tripPackageVM.getToPackItems().get(position);
        tripPackageVM.removeToPackItem(position);
        tripPackageVM.addInBagItem(item, tripPackageVM.getInBagItems().size());
    }

    public void notifyItemUnpacked(int position) {
        TripPackageViewModel tripPackageVM = ViewModelProviders.of(this).get(TripPackageViewModel.class);
        PackItem item = tripPackageVM.getInBagItems().get(position);
        tripPackageVM.removeInBagItem(position);
        tripPackageVM.addToPackItem(item, tripPackageVM.getToPackItems().size());
    }
}
