package ru.pap.newsapi.screen.sourcedetail.article;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.pap.newsapi.data.model.Article;
import ru.pap.newsapi.data.model.Resource;
import ru.pap.newsapi.databinding.ContentListBinding;
import ru.pap.newsapi.screen.common.BaseFragment;
import ru.pap.newsapi.screen.common.holder.IItemAction;

/**
 * Created by alex on 22.08.2017.
 */

public class ArticlesFragment extends BaseFragment implements IItemAction {

    public static final String EXTRA_SOURCE_ID = "EXTRA_SOURCE_ID";
    public static final String EXTRA_SORT_BY = "EXTRA_SORT_BY";

    private ContentListBinding mBinding;
    private ArticlesAdapter mArticlesAdapter;

    private ArticlesViewModel mArticlesViewModel;

    private String mSourceId;
    private String mSortBy;

    public static ArticlesFragment newInstance(String sourceId, String sortBy) {
        ArticlesFragment articlesFragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_SOURCE_ID, sourceId);
        args.putString(EXTRA_SORT_BY, sortBy);
        articlesFragment.setArguments(args);
        return articlesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = ContentListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.content.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.content.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mBinding.content.setItemAnimator(new DefaultItemAnimator());

        mArticlesAdapter = new ArticlesAdapter(this);
        mBinding.content.setAdapter(mArticlesAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mArticlesViewModel = ViewModelProviders
                .of(this, new ArticlesViewModel.Factory(getContext()))
                .get(ArticlesViewModel.class);
        subscribeUI();
        if (savedInstanceState == null) {
            mSourceId = getArguments().getString(EXTRA_SOURCE_ID);
            mSortBy = getArguments().getString(EXTRA_SORT_BY);
            mArticlesViewModel.loadArticles(mSourceId, mSortBy, false);
        }
    }

    private void subscribeUI() {
        mArticlesViewModel.getArticles().observe(this, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Article>> listResource) {
                setArticles(listResource);
            }
        });
    }

    private void setArticles(Resource<List<Article>> resource) {
        showMessage(resource);
        if (!resource.isLoaded()) {
            return;
        }
        mArticlesAdapter.setItems(resource.getData(), true);
    }

    @Override
    public void onItemAction(int action, int position) {
        if (action == ON_CLICK) {
            Article article = mArticlesAdapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            startActivity(intent);
        }
    }
}
