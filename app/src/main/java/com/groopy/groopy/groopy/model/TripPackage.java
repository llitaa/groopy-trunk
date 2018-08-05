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

        // TODO remove this after default list generation logic will be implemented
        _items = createDefaultItems();
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

    static private ArrayList<PackItem> createDefaultItems()
    {
        ArrayList<PackItem> res = new ArrayList<>();
        for (int i = 0; i < 20; ++i)
        {
            String countStr = Integer.toString(i);
            PackItem item = new PackItem("Item " + countStr);
            item.setDescription("Description of item " + countStr);
            res.add(item);
        }
        return res;
    }

    private String _name;
    private List<PackItem> _items;
    private String _description;
}
