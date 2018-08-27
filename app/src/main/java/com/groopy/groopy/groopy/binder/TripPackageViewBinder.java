package com.groopy.groopy.groopy.binder;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.groopy.groopy.groopy.adapter.PackListAdapter;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.model.TripPackage;

import java.util.List;

public class TripPackageViewBinder
{
    @BindingAdapter({"packItemsList", "itemClickListener"})
    public static void setTripPackageList(RecyclerView view, List<PackItem> list, PackListAdapter.OnItemClickListener itemClickListener)
    {
        if (list == null)
        {
            return;
        }
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null)
        {
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        PackListAdapter adapter = (PackListAdapter) view.getAdapter();
        if (adapter == null)
        {
            adapter = new PackListAdapter(view.getContext(), itemClickListener);
            view.setAdapter(adapter);
        }
        adapter.update(list);
        view.setItemAnimator(new DefaultItemAnimator());
    }

}
