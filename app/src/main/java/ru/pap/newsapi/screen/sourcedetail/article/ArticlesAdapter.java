package ru.pap.newsapi.screen.sourcedetail.article;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.pap.newsapi.data.model.Article;
import ru.pap.newsapi.databinding.ItemArticleBinding;
import ru.pap.newsapi.screen.common.adapter.ActionBaseAdapter;
import ru.pap.newsapi.screen.common.holder.BaseHolder;
import ru.pap.newsapi.screen.common.holder.IItemAction;

/**
 * Created by alex on 22.08.2017.
 */

public class ArticlesAdapter extends ActionBaseAdapter<Article> {
    public ArticlesAdapter(IItemAction action) {
        super(action);
    }

    @Override
    public BaseHolder<Article> onCreateViewHolder(ViewGroup parent, int viewType, IItemAction action) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ArticlesItemHolder(ItemArticleBinding.inflate(inflater, parent, false), action);
    }

    @Override
    public void onBindViewHolder(BaseHolder<Article> holder, int position) {
        holder.bind(getItem(position));
    }
}
