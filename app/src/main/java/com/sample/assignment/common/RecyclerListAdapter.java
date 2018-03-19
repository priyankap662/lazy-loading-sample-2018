package com.sample.assignment.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class RecyclerListAdapter<Data, Binding extends ViewDataBinding, ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Data> list;
    private OnListClick<Data> onClickListener;

    private RecyclerListAdapter(Context context, List<Data> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public RecyclerListAdapter(Context context, List<Data> list, OnListClick<Data> onClickListener) {
        this(context, list);
        this.onClickListener = onClickListener;
    }

    public void setList(List<Data> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @LayoutRes
    protected abstract int layoutId();

    protected abstract void bindData(Binding binding, Data data);

    private Data getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressWarnings("unchecked")
    private ViewHolder viewHolder(View view) {
        return (ViewHolder) new RecyclerViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Binding binding = DataBindingUtil.inflate(layoutInflater, layoutId(), parent, false);
        return viewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Binding binding = DataBindingUtil.findBinding(holder.itemView);
        bindData(binding, getItem(position));

        if (onClickListener != null) {
            binding.getRoot().setOnClickListener(v -> onClickListener.onListItemClick(list.get(position)));
        }
    }
}
