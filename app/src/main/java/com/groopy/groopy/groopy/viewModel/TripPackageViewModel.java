package com.groopy.groopy.groopy.viewModel;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.model.TripPackage;
import com.groopy.groopy.groopy.BR;

import java.util.List;

public class TripPackageViewModel extends ViewModel implements Observable
{
    protected String name;
    protected String description;
    protected List<PackItem> packItems;

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
        this.packItems = this.tripPackage.getItems();
        notifyChange();
    }

    public TripPackageViewModel(String name)
    {
        this.tripPackage = new TripPackage(name);
        this.name = tripPackage.getName();
        this.description = tripPackage.getDescription();
        this.packItems = tripPackage.getItems();
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
    public List<PackItem> getPackItems()
    {
        return this.packItems;
    }

    public void setPackItems(List<PackItem> items)
    {
        this.packItems = items;
        this.tripPackage.setItems(this.packItems);
        notifyChange();
    }

    public void addItem(PackItem item, int position)
    {
        this.packItems.add(position, item);
        this.tripPackage.setItems(this.packItems);
        // notifyChange();
    }

    public void removeItem(int position)
    {
        this.packItems.remove(position);
        this.tripPackage.setItems(this.packItems);
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