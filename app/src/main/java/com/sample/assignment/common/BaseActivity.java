package com.sample.assignment.common;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.sample.assignment.R;


public abstract class BaseActivity<ViewModel extends AndroidViewModel, Binding extends ViewDataBinding>
        extends AppCompatActivity implements DataView {

    protected View rootView;
    protected FrameLayout frameLayout;
    protected Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindRoot();
        observeData();
    }

    protected void bindRoot() {
        binding = DataBindingUtil.setContentView(this, R.layout.container_base);

        rootView = findViewById(R.id.page_container);
        frameLayout = findById(rootView, R.id.frame_layout);

        if (layoutId() != 0) {
            binding = DataBindingUtil.inflate(getLayoutInflater(), layoutId(), frameLayout, true);
        }
    }

    protected void observeData() {
        ((BaseViewModel) obtainViewModel(this)).isLoading.observe(this, this::onLoadingChanged);
        ((BaseViewModel) obtainViewModel(this)).errorData.observe(this, this::onError);
    }

    public ViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(getViewModelClass());
    }

    protected void updatePageTitle(String pageTitle) {
        setText(rootView, R.id.toolbar_title, pageTitle);
        hideShow(rootView, R.id.logo, R.id.toolbar_title);
    }

    private void onLoadingChanged(boolean isLoading) {
        loading(isLoading);
    }

    protected abstract int layoutId();
}
