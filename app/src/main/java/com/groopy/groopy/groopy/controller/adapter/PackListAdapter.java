package com.groopy.groopy.groopy.controller.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groopy.groopy.groopy.BR;
import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.databinding.PackItemViewBinding;
import com.groopy.groopy.groopy.model.PackItem;

import java.util.ArrayList;
import java.util.List;

public class PackListAdapter extends RecyclerView.Adapter<PackListAdapter.ViewHolder>
{
    private final LayoutInflater _inflater;
    private List<PackItem> _packItems = new ArrayList<>();

    public PackListAdapter(Context context)
    {
        this._inflater = LayoutInflater.from(context);
    }

    public void update(List<PackItem> list)
    {
        this._packItems.clear();
        this._packItems.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Generate same view vor each pack item
//        ViewDataBinding binding = PackItemViewBinding.inflate(_inflater, parent, false);
//        return new ViewHolder(binding.getRoot());

        TextView tw = (TextView) LayoutInflater.from(parent.getContext())
            .inflate(R.layout.pack_item_view, parent, false);

        // tw.setText("Lol Kek");
        ViewHolder vh = new ViewHolder(tw);
        return vh;
    }

    @Override
    public void onBindViewHolder(PackListAdapter.ViewHolder viewHolder, int position)
    {
        PackItem packItem = _packItems.get(position);
        // ViewDataBinding binding = viewHolder.getBinding();
        // binding.setPackItem(packItem);
        // binding.setVariable(BR.packItem, packItem);
        // binding.executePendingBindings();
        viewHolder._textView.setText(packItem.getName());
    }

    @Override
    public int getItemCount()
    {
        return _packItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView _textView;
        public ViewHolder(TextView itemView)
        {
            super(itemView);
            _textView = itemView;
        }
        //        public ViewHolder(View itemView)
//        {
//            super(itemView);
//        }

//        public ViewDataBinding getBinding()
//        {
//            return DataBindingUtil.getBinding(itemView);
//        }
    }

}