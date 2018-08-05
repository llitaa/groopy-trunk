package com.groopy.groopy.groopy.controller.binder;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.groopy.groopy.groopy.controller.adapter.PackListAdapter;
import com.groopy.groopy.groopy.model.PackItem;
import com.groopy.groopy.groopy.model.TripPackage;

import java.util.List;

public class TripPackageViewBinder
{
//    @BindingAdapter("tripPackageList")
//    public static void setTripPackageList(RecyclerView view, List<PackItem> list){
//        if(list == null) {
//            return;
//        }
//        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
//        if(layoutManager == null) {
//            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        }
//        PackListAdapter adapter = (PackListAdapter) view.getAdapter();
//        if(adapter == null) {
//            adapter = new PackListAdapter(view.getContext());
//            view.setAdapter(adapter);
//        }
//        adapter.update(list);
//    }

    @BindingAdapter("tripPackageList")
    public static void setTripPackageList(RecyclerView view, TripPackage tripPackage)
    {
        if (tripPackage == null)
        {
            return;
        }
        List<PackItem> list = tripPackage.getItems();
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
            adapter = new PackListAdapter(view.getContext());
            view.setAdapter(adapter);
        }
        adapter.update(list);
    }

}
