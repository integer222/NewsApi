package ru.pap.newsapi.screen.sourcedetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.pap.newsapi.data.model.Resource;
import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.databinding.SourceDetailFragmentBinding;
import ru.pap.newsapi.screen.common.BaseActivity;
import ru.pap.newsapi.screen.common.BaseFragment;
import ru.pap.newsapi.ui.pager.NewsFragmentStatePagerAdapter;
import ru.pap.newsapi.ui.pager.NewsFragmentStatePagerAdapter.PageData;

/**
 * Created by alex on 13.08.2017.
 */

public class SourceDetailFragment extends BaseFragment {

    public static final String EXTRA_SOURCE_ID = "EXTRA_SOURCE_ID";

    private SourceDetailFragmentBinding mBinding;
    private SourceDetailViewModel mDetailViewModel;
    private NewsFragmentStatePagerAdapter mPagerAdapter;

    public static SourceDetailFragment newInstance(Bundle args) {
        if (args == null || !args.containsKey(EXTRA_SOURCE_ID)) {
            throw new UnsupportedOperationException("source id == null");
        }
        SourceDetailFragment fragment = new SourceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = SourceDetailFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BaseActivity baseActivity = getBaseActivity();
        if (baseActivity != null) {
            baseActivity.setSupportActionBar(mBinding.toolbar);
            baseActivity.setHomeNecessarily(true);
        }
        mPagerAdapter = new NewsFragmentStatePagerAdapter(getChildFragmentManager());
        mBinding.pager.setAdapter(mPagerAdapter);
        mBinding.tabs.setupWithViewPager(mBinding.pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDetailViewModel = ViewModelProviders
                .of(this, new SourceDetailViewModel.Factory(getContext()))
                .get(SourceDetailViewModel.class);
        subscribeUI();
        if (savedInstanceState == null) {
            mDetailViewModel.loadSource(getArguments().getString(EXTRA_SOURCE_ID));
        }
    }

    private void subscribeUI() {
        mDetailViewModel.getSource().observe(this, new Observer<Resource<Source>>() {
            @Override
            public void onChanged(@Nullable Resource<Source> sourceResource) {
                setDetailSource(sourceResource);
            }
        });
    }

    private void setDetailSource(Resource<Source> resource) {
        if (!resource.isLoading()) {
            //hide progress
        }

        showMessage(resource);
        if (!resource.isLoaded()) {
            return;
        }

        Source source = resource.getData();
        mBinding.collapsingToolbarLayout.setTitle(source.getName());
        initTabs(source);
    }

    private void initTabs(Source source){
        List<PageData> pageDataList = new ArrayList<>();
        PageData pageData;
        for (String sortBy : source.getSortBysAvailable()){
            pageData = new PageData(sortBy, source.getId(), sortBy);
            pageDataList.add(pageData);
        }
        mPagerAdapter.setPagesData(pageDataList);
    }
}
