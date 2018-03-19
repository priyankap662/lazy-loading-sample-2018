package com.sample.assignment.destination;


import android.content.Context;

import com.sample.assignment.R;
import com.sample.assignment.common.OnListClick;
import com.sample.assignment.common.RecyclerListAdapter;
import com.sample.assignment.common.RecyclerViewHolder;
import com.sample.assignment.databinding.DestinationListItemBinding;
import com.sample.assignment.models.Rows;
import com.sample.assignment.utils.RandomColor;

import java.util.List;


public class DestinationsAdapter extends RecyclerListAdapter<Rows, DestinationListItemBinding, RecyclerViewHolder> {

    private RandomColor randomColor;

    DestinationsAdapter(Context context, List<Rows> list, OnListClick<Rows> onClickListener) {
        super(context, list, onClickListener);
        randomColor = new RandomColor();
    }

    @Override
    protected int layoutId() {
        return R.layout.destination_list_item;
    }

    @Override
    protected void bindData(DestinationListItemBinding binding, Rows row) {
        binding.image.setImageDrawable(randomColor.getRandomLazyLoadingColor());
        binding.setItem(row);
    }
}