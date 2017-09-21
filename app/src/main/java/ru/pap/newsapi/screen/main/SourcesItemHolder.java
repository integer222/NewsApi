package ru.pap.newsapi.screen.main;

import android.view.View;

import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.databinding.ItemSourceBinding;
import ru.pap.newsapi.screen.common.holder.ActionBaseHolder;
import ru.pap.newsapi.screen.common.holder.IItemAction;

/**
 * Created by alex on 13.08.2017.
 */

public class SourcesItemHolder extends ActionBaseHolder<Source> {

    private ItemSourceBinding mBinding;

    public SourcesItemHolder(ItemSourceBinding binding, IItemAction action) {
        super(binding.getRoot(), action);
        mBinding = binding;
    }


    @Override
    public void bind(Source model) {
        mBinding.title.setText(model.getName());
        mBinding.body.setText(model.getDescription());
        mBinding.lang.setText(model.getLanguage());
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAction().onItemAction(IItemAction.ON_CLICK, getAdapterPosition());
            }
        });
    }
}
