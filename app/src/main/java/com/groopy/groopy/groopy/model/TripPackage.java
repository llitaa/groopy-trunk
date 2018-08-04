package com.groopy.groopy.groopy.model;

import java.util.ArrayList;
import java.util.List;

//! Class that represents single trip package
public class TripPackage
{
    public TripPackage(String name)
    {
        _name = name;
        _items = new ArrayList<>();
        _description = null;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public List<PackItem> getItems()
    {
        return _items;
    }

    public void setItems(List<PackItem> items)
    {
        _items = items;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setDescription(String description)
    {
        _description = description;
    }

    private String _name;
    private List<PackItem> _items;
    private String _description;
}
