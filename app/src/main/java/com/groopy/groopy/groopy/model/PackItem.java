package com.groopy.groopy.groopy.model;

//! Class that represents single trip package
public class PackItem
{
    public PackItem(String name)
    {
        this(name, null);
    }

    public PackItem(String name, String description)
    {
        _name = name;
        _description = description;
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

    private String _name;
    private String _description;
}
