package com.groopy.groopy.groopy.model;

import java.util.ArrayList;
import java.util.List;

//! Class that represents single trip package
public class TripPackage
{
    private String _name;
    private String _description;
    private List<PackItem> _toPackItems;
    private List<PackItem> _inBagItems;

    public TripPackage(String name)
    {
        _name = name;
        _description = null;

        // TODO remove this after default list generation logic will be implemented
        _toPackItems = createDefaultItems();
        _inBagItems = new ArrayList<>();
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
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

    public List<PackItem> getToPackItems() {
        return _toPackItems;
    }

    public void addInBagItem(PackItem item) {
        _inBagItems.add(item);
    }

    public void addToPackItem(PackItem item) {
        _toPackItems.add(item);
    }

    public void removeInBagItem(int pos) {
        if (pos < _inBagItems.size())
            _inBagItems.remove(pos);
    }

    public void removeToPackItem(int pos) {
        if (pos < _toPackItems.size())
            _toPackItems.remove(pos);
    }

    public List<PackItem> getInBagItems() {
        return _inBagItems;
    }


}
