package com.groopy.groopy.groopy.model;

import java.util.ArrayList;
import java.util.List;

//! Class that represents single trip package
public class TripPackage
{
    private String _name;
    private List<PackItem> _items;
    private String _description;
    protected List<PackItem> _toPackItems;
    protected List<PackItem> _inBagItems;

    public TripPackage(String name)
    {
        _name = name;
        _items = new ArrayList<>();
        _description = null;

        // TODO remove this after default list generation logic will be implemented
        _items = createDefaultItems();
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

    public List<PackItem> getToPackItems() {
        return _toPackItems;
    }

    public void setItemPacked(PackItem item) {
        if (_toPackItems.contains(item))
        {
            _inBagItems.add(item);
            _toPackItems.remove(item);
        }
    }

    public void setItemUnPacked(PackItem item) {
        if (_inBagItems.contains(item))
        {
            _toPackItems.add(item);
            _inBagItems.remove(item);
        }
    }

    public List<PackItem> getInBagItems() {
        return _inBagItems;
    }
}
