package ru.pap.newsapi.screen.sourcedetail.article;


import android.net.Uri;
import android.view.View;

import com.squareup.picasso.Picasso;

import ru.pap.newsapi.R;
import ru.pap.newsapi.data.model.Article;
import ru.pap.newsapi.databinding.ItemArticleBinding;
import ru.pap.newsapi.screen.common.holder.ActionBaseHolder;
import ru.pap.newsapi.screen.common.holder.IItemAction;
import ru.pap.newsapi.utils.DateTimeUtils;

/**
 * Created by alex on 22.08.2017.
 */

public class ArticlesItemHolder extends ActionBaseHolder<Article> {

    private ItemArticleBinding mBinding;

    public ArticlesItemHolder(ItemArticleBinding binding, IItemAction action) {
        super(binding.getRoot(), action);
        mBinding = binding;
    }

    @Override
    public void bind(Article model) {
        mBinding.title.setText(model.getTitle());
        mBinding.body.setText(model.getDescription());
        mBinding.date.setText(DateTimeUtils.getTimeMark(model.getPublishedAt()));

        Picasso.with(getContext())
                .load(Uri.parse(model.getUrlToImage()))
                .resize(80, 80)
                //.centerCrop()
                .into(mBinding.media);
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAction().onItemAction(IItemAction.ON_CLICK, getAdapterPosition());
            }
        });
    }
}
