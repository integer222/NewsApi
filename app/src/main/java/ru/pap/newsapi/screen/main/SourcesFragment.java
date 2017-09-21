package ru.pap.newsapi.screen.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.pap.newsapi.data.model.Resource;
import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.databinding.FragmentMainBinding;
import ru.pap.newsapi.screen.common.BaseFragment;
import ru.pap.newsapi.screen.common.holder.IItemAction;
import ru.pap.newsapi.screen.sourcedetail.SourceDetailActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class SourcesFragment extends BaseFragment implements IItemAction {

    public static final String TAG = "SourcesFragment";

    private SourcesViewModel mSourcesViewModel;

    private FragmentMainBinding mBinding;
    private SourcesAdapter mAdapter;

    private SwipeRefreshLayout mRefresh;

    public SourcesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new SourcesAdapter(this);
        RecyclerView contentList = mBinding.content;
        contentList.setLayoutManager(new LinearLayoutManager(getContext()));
        contentList.setItemAnimator(new DefaultItemAnimator());
        contentList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        contentList.setAdapter(mAdapter);
        mRefresh = mBinding.refresh;
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh(true);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSourcesViewModel = ViewModelProviders
                .of(this, new SourcesViewModel.Factory(getContext()))
                .get(SourcesViewModel.class);
        subscribeUI();
        if (savedInstanceState == null) {
            showRefresh(true);
            doRefresh(false);
        }
    }

    private void showRefresh(final boolean show) {
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(show);
            }
        });
    }

    private void subscribeUI() {
        mSourcesViewModel.getData().observe(this, new Observer<Resource<List<Source>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Source>> resource) {
                setContentData(resource);
            }
        });
    }

    private void setContentData(Resource<List<Source>> resource) {
        if(!resource.isLoading()){
            showRefresh(false);
        }

        showMessage(resource);
        if (!resource.isLoaded()) {
            return;
        }

        mAdapter.setItems(resource.getData(), true);
    }

    private void doRefresh(boolean forceUpdate) {
        mSourcesViewModel.loadSources(forceUpdate);
    }

    private void doOpenDetailSource(Source source) {
        Intent intent = SourceDetailActivity.newIntent(getContext(), source.getId());
        startActivity(intent);
    }



    @Override
    public void onItemAction(int action, int position) {
        if (action == ON_CLICK) {
            doOpenDetailSource(mAdapter.getItem(position));
        }
    }
}
