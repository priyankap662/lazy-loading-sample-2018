package com.sample.assignment.destination;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.sample.assignment.R;
import com.sample.assignment.common.SwipeRefreshActivity;
import com.sample.assignment.databinding.ActivityDestinationBinding;
import com.sample.assignment.models.Destination;
import com.sample.assignment.models.Rows;
import com.sample.assignment.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DestinationActivity extends SwipeRefreshActivity<DestinationsViewModel, ActivityDestinationBinding> {

    private DestinationsViewModel viewModel;

    @Override
    protected int layoutId() {
        return R.layout.activity_destination;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseDestinationsListView();

        viewModel = obtainViewModel(this);
        viewModel.getDestination().observe(this, this::onDestination);
        viewModel.fetchDestinationData(false);
    }

    private void onDestination(Destination destination) {
        binding.setListItems(
                (List<Rows>) CollectionUtils.filter(destination.getRows(),
                        info -> !TextUtils.isEmpty(info.getTitle()) && !TextUtils.isEmpty(info.getDescription())));
        updatePageTitle(destination.getTitle());
        completeRefresh();
    }

    @Override
    public void onError(Throwable throwable) {
        completeRefresh();
    }

    @Override
    protected void refreshCall(boolean... refresh) {
        viewModel.fetchDestinationData(refresh);
    }

    private void initialiseDestinationsListView() {
        RecyclerView recyclerList = binding.listView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerList.addItemDecoration(dividerItemDecoration);

        recyclerList.setHasFixedSize(true);

        DestinationsAdapter adapter = new DestinationsAdapter(this, new ArrayList<>(), null);
        recyclerList.setAdapter(adapter);
    }
}
