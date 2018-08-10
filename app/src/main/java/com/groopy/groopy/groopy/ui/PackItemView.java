package com.groopy.groopy.groopy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.model.PackItem;

public class PackItemView extends LinearLayout
{
    public PackItemView(Context context)
    {
        this(context, null);
    }

    public PackItemView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        inflate(getContext(), R.layout.pack_item_view, this);
    }

    public void setSourceData(PackItem packItem)
    {
        TextView nameView = findViewById(R.id._itemName);
        nameView.setText(packItem.getName());
        TextView decriptionView = findViewById(R.id._itemDescription);
        decriptionView.setText(packItem.getDescription());
    }

    public View getRemoveItemView()
    {
        return findViewById(R.id._removeButton);
    }
}