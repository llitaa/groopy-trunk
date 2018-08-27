package com.groopy.groopy.groopy.viewModel;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.model.TripPackage;
import com.groopy.groopy.groopy.BR;

import java.util.List;

public class TripPackageViewModel extends ViewModel implements Observable
{
    protected String name;
    protected String description;
    protected List<PackItem> toPackItems;
    protected List<PackItem> inBagItems;

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    // Trip package model
    private TripPackage tripPackage;

    public TripPackageViewModel()
    {
        this("");
    }

    public TripPackageViewModel(TripPackage tripPackage)
    {
        this.tripPackage = tripPackage;
        this.name = this.tripPackage.getName();
        this.description = this.tripPackage.getDescription();

        this.toPackItems = tripPackage.getToPackItems();
        this.inBagItems = tripPackage.getInBagItems();
        notifyChange();
    }

    public TripPackageViewModel(String name)
    {
        this.tripPackage = new TripPackage(name);
        this.name = tripPackage.getName();
        this.description = tripPackage.getDescription();

        this.toPackItems = this.tripPackage.getToPackItems();
        this.inBagItems = this.tripPackage.getInBagItems();
        notifyChange();
    }

    @Bindable
    public String getName()
    {
        return this.name;
    }

    public void setName(String nm)
    {
        this.name = nm;
        this.tripPackage.setName(this.name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
        tripPackage.setDescription(description);
    }

    @Bindable
    public List<PackItem> getToPackItems()
    {
        return this.toPackItems;
    }

    public void addToPackItem(PackItem item, int position)
    {
        this.toPackItems.add(position, item);
        // this.tripPackage.addToPackItem(item);
        notifyPropertyChanged(BR.toPackItems);
        // notifyChange();
    }

    public void removeToPackItem(int position)
    {
        this.toPackItems.remove(position);
        // this.tripPackage.removeToPackItem(position);
        // notifyChange();
    }

    @Bindable
    public List<PackItem> getInBagItems()
    {
        return this.inBagItems;
    }

    public void addInBagItem(PackItem item, int position)
    {
        this.inBagItems.add(position, item);
        // this.tripPackage.addInBagItem(item);
        notifyPropertyChanged(BR.inBagItems);
        // notifyChange();
    }

    public void removeInBagItem(int position)
    {
        this.inBagItems.remove(position);
        // this.tripPackage.removeInBagItem(position);
        // notifyChange();
    }

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback)
    {
        this.callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback)
    {
        this.callbacks.remove(callback);
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    private void notifyChange()
    {
        this.callbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     * */
    private void notifyPropertyChanged(int fieldId)
    {
        this.callbacks.notifyCallbacks(this, fieldId, null);
    }
}