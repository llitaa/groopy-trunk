package com.groopy.groopy.groopy.model;

//! Class that represents single trip package
class PackItem
{
    public PackItem(String name)
    {
        _name = name;
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
