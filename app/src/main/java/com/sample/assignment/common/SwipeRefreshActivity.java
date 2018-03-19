package com.sample.assignment.common;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sample.assignment.R;
import com.sample.assignment.databinding.ContainerSwipeRefreshBinding;

public abstract class SwipeRefreshActivity<ViewModel extends AndroidViewModel, Binding extends ViewDataBinding>
        extends BaseActivity<ViewModel, Binding> {

    private ContainerSwipeRefreshBinding baseBinding;
    private SwipeRefreshLayout refreshLayout;

    protected abstract void refreshCall(boolean... refresh);

    @Override
    protected void bindRoot() {
        baseBinding = DataBindingUtil.setContentView(this, R.layout.container_swipe_refresh);
        rootView = findViewById(R.id.page_container);
        frameLayout = findViewById(R.id.frame_layout);

        if (layoutId() > 0) {
            binding = DataBindingUtil.inflate(getLayoutInflater(), layoutId(), frameLayout, true);
        }
        configureSwipeRefresh();
    }

    @Override
    public void loading(boolean show) {
        baseBinding.setLoading(show && !isRefreshing());
        if (!show) {
            completeRefresh();
        }
    }

    protected void completeRefresh() {
        if (refreshLayout != null) {
            refreshLayout.setVisibility(View.VISIBLE);
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
        }
    }

    private void configureSwipeRefresh() {
        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(() -> refreshCall(true));

        if (layoutId() == 0) {
            refreshLayout.setVisibility(View.VISIBLE);
        }

        refreshEnable(true);
    }

    protected void refreshEnable(boolean isEnable) {
        if (refreshLayout != null) {
            refreshLayout.setEnabled(isEnable);
        }
    }

    public boolean isRefreshing() {
        return refreshLayout != null && refreshLayout.isRefreshing();
    }
}
