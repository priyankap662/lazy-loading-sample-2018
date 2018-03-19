package com.sample.assignment.bindings;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.sample.assignment.common.RecyclerListAdapter;

import java.util.List;

/**
 * Contains {@link BindingAdapter}s for the list.
 */
public class ListBindingAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static void setRecycletAdapterList(RecyclerView listView, List items) {
        RecyclerListAdapter adapter = (RecyclerListAdapter) listView.getAdapter();
        if (adapter != null && items != null) {
            adapter.setList(items);
        }
    }
}
