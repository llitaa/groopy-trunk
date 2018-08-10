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
import com.groopy.groopy.groopy.ui.PackItemView;

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

    public void update(List<PackItem> list) {
        this._packItems.clear();
        this._packItems.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        PackItemView itemView = new PackItemView(parent.getContext());
        // Customize view size
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PackListAdapter.ViewHolder viewHolder, int position)
    {
        PackItem packItem = _packItems.get(position);
        viewHolder.initContext(packItem, position);
    }

    public void removeItem(int position)
    {
        _packItems.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, PackItem item)
    {
        _packItems.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount()
    {
        return _packItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public PackItemView _packItemView;
        private View _removeItemView;

        public ViewHolder(View view)
        {
            super(view);
            _packItemView = (PackItemView) view;
        }

        public void initListeners()
        {
            _removeItemView = _packItemView.getRemoveItemView();
            if(_removeItemView != null)
            {
                _removeItemView.setOnClickListener(ViewHolder.this);
            }
        }

        @Override
        public void onClick(View v)
        {
            if (v == _removeItemView)
            {
                removeItem(ViewHolder.this.getAdapterPosition());
            }
        }

        public void initContext(PackItem packItem, int position)
        {
            _packItemView.setSourceData(packItem);
            initListeners();
        }
    }

}